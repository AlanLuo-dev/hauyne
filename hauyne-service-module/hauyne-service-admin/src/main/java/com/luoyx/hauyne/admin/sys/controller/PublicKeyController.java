package com.luoyx.hauyne.admin.sys.controller;

import com.luoyx.hauyne.admin.api.sys.enums.PrivateKeyRedisKeyEnum;
import com.luoyx.hauyne.admin.sys.response.ModifyPasswordPreGeneratedKeyVO;
import com.luoyx.hauyne.framework.utils.rsa.RSAUtil;
import com.luoyx.hauyne.validation.constraint.EnumCheck;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.PrintWriter;
import java.security.interfaces.RSAKey;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * 公钥
 *
 * @author 1564469545@qq.com
 * @date 2023/4/2 12:51
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
public class PublicKeyController {

    private final RedisTemplate<String, String> redisTemplate;

    /**
     * 为修改密码生成RSA公私钥
     *
     * @return
     * @throws Exception
     */
    @Operation(summary = "生成RSA公私钥对")
    @PostMapping(value = "/public-key/{business}")
    public ModifyPasswordPreGeneratedKeyVO getPublickKey(@Parameter(name = "业务Key")
                                                         @NotBlank(message = "业务Key不能为空")
                                                         @EnumCheck(
                                                                 message = "业务Key不合法",
                                                                 enumClazz = PrivateKeyRedisKeyEnum.class,
                                                                 getterMethod = "getBusiness"
                                                         )
                                                         @PathVariable("business") String business) throws Exception {

        // 生成RSA公私钥对
        Map<String, RSAKey> rsaKeysMap = RSAUtil.initKey();

        // 私钥保存在redis中
        String redisKey = UUID.randomUUID().toString();
        PrivateKeyRedisKeyEnum privateKeyRedisKeyEnum = PrivateKeyRedisKeyEnum.map.get(business);
        redisTemplate
                .opsForValue()
                .set(privateKeyRedisKeyEnum.getKeyPrefix() + redisKey,
                        RSAUtil.getBase64PrivateKeyStr(rsaKeysMap),
                        privateKeyRedisKeyEnum.getTimeout(),
                        privateKeyRedisKeyEnum.getTimeUnit()
                );

        return new ModifyPasswordPreGeneratedKeyVO(RSAUtil.getBase64PublicKeyStr(rsaKeysMap), redisKey);
    }

    @RequestMapping(value = "/needPricer")
    public void pushRight(HttpServletResponse response) {
        response.setContentType(MediaType.TEXT_EVENT_STREAM_VALUE);
        response.setCharacterEncoding("utf-8");
        Random r = new Random();
        try {
            PrintWriter pw = response.getWriter();
            int i = 0;
            while (i < 10) {
                if (pw.checkError()) {
                    System.out.println("客户端断开连接");
                    return;
                }
                Thread.sleep(1000);
                pw.write(makeResp(r));
                pw.flush();
                i++;
            }
            System.out.println("达到阈值，结束发送......");
            pw.write("data:stop\n\n");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private String makeResp(Random r) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("retry:2000\n")
                .append("data:")
                .append(r.nextInt(100) + 50 + ",")
                .append(r.nextInt(40) + 35)
                .append("\n\n");

        return stringBuilder.toString();
    }
}
