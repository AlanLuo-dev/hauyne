package com.luoyx.hauyne.uaa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bingoohuang.patchca.custom.ConfigurableCaptchaService;
import com.github.bingoohuang.patchca.service.Captcha;
import com.luoyx.hauyne.framework.utils.rsa.RSAUtil;
import com.luoyx.hauyne.uaa.constant.Constant;
import com.luoyx.hauyne.uaa.dto.CachedCaptchaDTO;
import com.luoyx.hauyne.uaa.response.ImageCodeVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.security.interfaces.RSAKey;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 图形验证码 Controller
 *
 * @author 罗英雄
 */
@Slf4j
@Tag(name = "CaptchaController", description = "图形验证码控制器")
@RestController
@RequiredArgsConstructor
public class CaptchaController {

    public static final String REDIS_KEY_IMAGE_CODE = "REDIS_KEY_IMAGE_CODE:";

    private final ConfigurableCaptchaService captchaService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Operation(summary = "获取图形验证码", description = "获取图形验证码")
    @GetMapping(value = Constant.CAPTCHAS)
    public ImageCodeVO captchaImage() throws Exception {
        Captcha captcha = captchaService.getCaptcha();
        //生成RSA公私钥对
        Map<String, RSAKey> rsaKeysMap = RSAUtil.initKey();

        CachedCaptchaDTO cachedCaptchaDTO = new CachedCaptchaDTO();
        cachedCaptchaDTO.setActualImgCode(captcha.getChallenge());
        cachedCaptchaDTO.setRsaPrivateKey(RSAUtil.getBase64PrivateKeyStr(rsaKeysMap));

        String captchaKey = UUID.randomUUID().toString();
        // 以随机字符串作为key,将验证码实际值存储到redis，有效期5分钟

        final String key = REDIS_KEY_IMAGE_CODE + captchaKey;
        final String value = new ObjectMapper().writeValueAsString(cachedCaptchaDTO);
        redisTemplate.opsForValue().set(key, value, 300, TimeUnit.SECONDS);

        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ImageIO.write(captcha.getImage(), "png", bs);

        //转换成baes64串
        String base64Png = new Base64().encodeAsString(bs.toByteArray()).trim();
        //删除 \r\n
        base64Png = base64Png.replaceAll("\n", "").replaceAll("\r", "");
        ImageCodeVO imageCodeVO = new ImageCodeVO();
        imageCodeVO.setBase64ImageCode("data:image/png;base64," + base64Png);
        imageCodeVO.setRsaPublicKey(RSAUtil.getBase64PublicKeyStr(rsaKeysMap));
        imageCodeVO.setCaptchaKey(captchaKey);

        return imageCodeVO;
    }
}
