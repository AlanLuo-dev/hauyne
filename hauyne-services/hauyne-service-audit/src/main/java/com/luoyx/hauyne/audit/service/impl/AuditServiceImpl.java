package com.luoyx.hauyne.audit.service.impl;

import com.luoyx.hauyne.admin.api.sys.audit.RoleAuditDTO;
import com.luoyx.hauyne.audit.entity.UserSnapshot;
import com.luoyx.hauyne.audit.enums.AuditEnum;
import com.luoyx.hauyne.audit.query.AuditQuery;
import com.luoyx.hauyne.audit.response.AuditChangeVO;
import com.luoyx.hauyne.audit.response.FieldChangeVO;
import com.luoyx.hauyne.audit.service.AuditService;
import com.luoyx.hauyne.audit.support.AuditFieldNameRegistry;
import com.luoyx.hauyne.usersnapshot.service.UserSnapshotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.javers.core.Changes;
import org.javers.core.Javers;
import org.javers.core.commit.CommitMetadata;
import org.javers.core.diff.Change;
import org.javers.core.diff.changetype.NewObject;
import org.javers.core.diff.changetype.ObjectRemoved;
import org.javers.core.diff.changetype.ValueChange;
import org.javers.core.diff.changetype.container.ListChange;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.repository.jql.JqlQuery;
import org.javers.repository.jql.QueryBuilder;
import org.javers.shadow.Shadow;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 审计日志 Service实现类
 *
 * @author Alan.Luo
 */
@Slf4j
@Service("auditService")
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final Javers javers;
    private final UserSnapshotService<UserSnapshot> userSnapshotService;

    /**
     * 审计（新增、修改）
     *
     * @param authorId 审计人
     * @param auditDTO 审计DTO
     */
    @Override
    public <U> void audit(Long authorId, U auditDTO) {
        javers.commit(String.valueOf(authorId), auditDTO);
    }

    /**
     * 审计（删除）
     *
     * @param authorId 审计人 用户id
     * @param auditDTO 审计DTO
     */
    @Override
    public <U> void shadowDelete(Long authorId, U auditDTO) {
        javers.commitShallowDelete(String.valueOf(authorId), auditDTO);
    }

    /**
     * 创建JQL查询条件
     *
     * @param query 查询条件
     * @return JQL查询对象
     */
    private JqlQuery createJqlQuery(AuditQuery query) {
        return QueryBuilder
                .byInstanceId(
                        query.getLocalId(),
                        AuditEnum.TYPE_NAME_CLAZZ_MAP.get(query.getTypeName())
                )
                .build();
    }

    /**
     * 查询快照
     *
     * @param query 查询条件
     * @return 快照列表
     */
    @Override
    public String findSnapshots(AuditQuery query) {
        List<CdoSnapshot> snapshots = javers.findSnapshots(createJqlQuery(query));
        return javers.getJsonConverter().toJson(snapshots);
    }

    /**
     * 查询影子
     *
     * @param query 查询条件
     * @return 影子列表
     */
    @Override
    public String findShadows(AuditQuery query) {
        List<Shadow<RoleAuditDTO>> shadows = javers.findShadows(createJqlQuery(query));
        return javers.getJsonConverter().toJson(shadows);
    }

    /**
     * 查询变更日志
     *
     * @param query 查询条件
     * @return 变更日志列表
     */
    @Override
    public String findChanges(AuditQuery query) {
        Changes changes = javers.findChanges(createJqlQuery(query));
        return javers.getJsonConverter().toJson(changes);
    }

    /**
     * 查询分组后的变更日志
     *
     * @param query 查询条件
     * @return 变更日志列表
     */
    @Override
    public List<AuditChangeVO> groupChangesByCommit(AuditQuery query) {
        List<Change> changes = javers.findChanges(createJqlQuery(query));

        Map<String, AuditChangeVO> grouped = new LinkedHashMap<>();
        Set<String> deletedCommitIds = new HashSet<>();

        for (Change change : changes) {
            Optional<CommitMetadata> optional = change.getCommitMetadata();
            if (optional.isPresent()) {
                CommitMetadata meta = optional.get();
                String commitId = meta.getId().value();

                // 初始化每个提交组
                AuditChangeVO changeDTO = grouped.computeIfAbsent(commitId, id ->
                        AuditChangeVO.builder()
                                .author(meta.getAuthor())
                                .commitDate(meta.getCommitDate())
                                .version(new BigDecimal(id)) // 可改为 meta.getId().value()
                                .changes(new ArrayList<>())
                                .changeType("MODIFY") // 默认是修改
                                .build()
                );


                if (change instanceof ObjectRemoved) {
                    changeDTO.setChangeType("DELETE");
                    changeDTO.setChanges(Collections.emptyList()); // 清空字段变更
                    deletedCommitIds.add(commitId);
                    continue;
                }

                // 若当前 commit 是删除型，不再处理其他字段变更
                if (deletedCommitIds.contains(commitId)) {
                    continue;
                }

                if (change instanceof NewObject) {
                    changeDTO.setChangeType("CREATE");
                }

                // 字段变更
                else if (change instanceof ValueChange) {
                    ValueChange valueChange = (ValueChange) change;

                    FieldChangeVO field = FieldChangeVO.builder()
                            .property(valueChange.getPropertyName())
                            .label(
                                    AuditFieldNameRegistry.resolve(query.getTypeName(), valueChange.getPropertyName())
                            )
                            .oldValue(valueChange.getLeft())
                            .newValue(valueChange.getRight())
                            .build();
                    changeDTO.getChanges().add(field);
                }

                // 集合变更
                else if (change instanceof ListChange) {
                    ListChange listChange = (ListChange) change;

                    FieldChangeVO field = FieldChangeVO.builder()
                            .property(listChange.getPropertyName())
                            .label(
                                    AuditFieldNameRegistry.resolve(query.getTypeName(), listChange.getPropertyName())
                            )
                            .oldValue(listChange.getLeft())
                            .newValue(listChange.getRight())
                            .build();
                    changeDTO.getChanges().add(field);
                }
            }

        }

        List<AuditChangeVO> auditChangeList = new ArrayList<>(grouped.values());
        Set<Long> userIds = auditChangeList.stream()
                .map(item -> Long.parseLong(item.getAuthor()))
                .collect(Collectors.toSet());
        if (CollectionUtils.isNotEmpty(userIds)) {
            Map<Long, String> userMap = userSnapshotService.listByIds(userIds).stream()
                    .collect(Collectors.toMap(UserSnapshot::getId, UserSnapshot::getRealName));
            for (AuditChangeVO auditChangeVO : auditChangeList) {
                auditChangeVO.setAuthor(userMap.get(Long.parseLong(auditChangeVO.getAuthor())));
            }
        }

        return auditChangeList;
    }
}
