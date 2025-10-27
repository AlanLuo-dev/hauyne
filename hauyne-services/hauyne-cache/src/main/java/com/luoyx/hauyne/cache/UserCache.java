package com.luoyx.hauyne.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luoyx.hauyne.admin.api.sys.dto.UserFullNameDTO;
import com.luoyx.hauyne.cache.client.UserProfileClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.luoyx.hauyne.cache.constant.RedisConstant.USER_FULL_NAME_KEY;

@Slf4j
@RequiredArgsConstructor
public class UserCache {

    private final RedisTemplate<String, Object> anotherRedisTemplate;
    private final UserProfileClient userProfileClient;

    /**
     * 按用户id查询单个用户全名
     *
     * @param userId 用户id
     * @return 用户全名
     */
    public String getUserFullName(Long userId) {
        Set<Long> userIds = new HashSet<>(1);
        userIds.add(userId);
        Map<Long, String> userFullNameMap = getUserFullName(userIds);

        return userFullNameMap.get(userId);
    }

    /**
     * 按用户id集合 获取各个用户的全名
     *
     * @param userIds 用户id集合
     * @return
     */
    public Map<Long, String> getUserFullName(Set<Long> userIds) {
        Map<Long, String> cachedUserFullNamesMap = new HashMap<>();

        // 用户id集合 判空
        if (CollectionUtils.isEmpty(userIds)) {
            return cachedUserFullNamesMap;
        }
        log.info("从Redis缓存中读取 用户全名");
        HashOperations<String, String, String> hashOperations = anotherRedisTemplate.opsForHash();

        // 缓存不存在，从数据库查询，并异步写入到缓存
        if (Boolean.FALSE.equals(anotherRedisTemplate.hasKey(USER_FULL_NAME_KEY))) {
            log.info("从数据库查询用户全名");
            return findFullNamesMapAndFlushCache(userIds);
        }

        List<String> hashKeyList = userIds.stream()
                .map(Object::toString)
                .collect(Collectors.toList());

        // 从Redis缓存读取
        List<String> hashValueList = hashOperations.multiGet(USER_FULL_NAME_KEY, hashKeyList);
        if (CollectionUtils.isNotEmpty(hashValueList)) {
            for (int i = 0; i < hashKeyList.size(); i++) {
                String fullName = hashValueList.get(i);
                if (StringUtils.isNotBlank(fullName)) {
                    cachedUserFullNamesMap.put(Long.parseLong(hashKeyList.get(i)), fullName);
                }
            }
        }

        // 未缓存的用户id，执行一次远程查询，更新到Redis
        Collection<Long> uncachedUserIds = CollectionUtils.removeAll(userIds, cachedUserFullNamesMap.keySet());
        if (CollectionUtils.isNotEmpty(uncachedUserIds)) {
            // 去数据库查询，并更新到缓存
            log.info("存在未缓存的用户全名，用户id为 -> {}", StringUtils.join(uncachedUserIds));
            Map<Long, String> uncachedUserFullnameMap = findFullNamesMapAndFlushCache(new HashSet<>(uncachedUserIds));
            cachedUserFullNamesMap.putAll(uncachedUserFullnameMap);
        }

        return cachedUserFullNamesMap;
    }

    /**
     * 按用户id集合 从数据库查询用户全名
     *
     * @param userIds 用户id集合
     * @return 用户全名Map key:用户id，value:用户全名
     */
    public Map<Long, String> findFullNamesMapAndFlushCache(Set<Long> userIds) {

        // 从service-admin服务查询用户的全名
        List<UserFullNameDTO> userFullNameList = userProfileClient.findFullNamesByUserIds(userIds);
        if (CollectionUtils.isNotEmpty(userFullNameList)) {

            // 异步写入Redis缓存
            this.asyncFlushCache(userFullNameList);

            return userFullNameList.stream()
                    .collect(
                            Collectors.toMap(UserFullNameDTO::getUserId, UserFullNameDTO::getFullName)
                    );
        }

        return new HashMap<>();
    }

    /**
     * 异步刷新缓存
     *
     * @param userFullNameList
     */
    public void asyncFlushCache(List<UserFullNameDTO> userFullNameList) {
        CompletableFuture.runAsync(() -> {
            log.info("开始异步刷新缓存");
            Map<String, String> cacheUserFullNameMap = userFullNameList
                    .stream()
                    .collect(Collectors.toMap(item -> item.getUserId().toString(), UserFullNameDTO::getFullName));

            HashOperations<String, String, String> hashOperations = anotherRedisTemplate.opsForHash();
            hashOperations.putAll(USER_FULL_NAME_KEY, cacheUserFullNameMap);
            try {
                String userFullNameStr = new ObjectMapper().writeValueAsString(cacheUserFullNameMap);
                log.info("已成功将 -> {}更新至 -> {} Hash缓存", userFullNameStr, USER_FULL_NAME_KEY);
            } catch (JsonProcessingException e) {
                log.error("序列化错误{}{}", e, e.getMessage());
            }
            log.info("结束异步刷新缓存");
        });
    }

}

