package com.webshop.user.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin  //跨域
public class CaptchaController {
    @Autowired
    @SuppressWarnings("unused")
    private DefaultKaptcha defaultKaptcha;



    @Autowired
    @SuppressWarnings("unused")
    private RedisTemplate<String, String> redisTemplateString;

    @GetMapping("/createImageCode")
    public void createImageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*
         * ===== 原验证码生成逻辑（已停用，便于接口调试；需要时取消注释） =====
         *
         * response.setHeader("Cache-Control", "no-store, no-cache");
         * response.setContentType("image/jpeg");
         * String text = defaultKaptcha.createText();
         * BufferedImage image = defaultKaptcha.createImage(text);
         * redisTemplateString.opsForValue().set("imageCode",text,60, TimeUnit.SECONDS);
         *
         * ServletOutputStream out = response.getOutputStream();
         * ImageIO.write(image, "jpg", out);
         * out.flush();
         * out.close();
         */
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    // 验证验证码

    /**
     * 这个验证的返回的值有问题的，不能返回一个true啊，至少用类似token或者加密的方法吧，我把这一部分的内容放到tokens中了
     * @param inputCaptcha
     * @return
     */
    @PostMapping("/api/captcha/validate")
    public ResponseEntity<Boolean> validateCaptcha(@RequestParam("inputCaptcha") String inputCaptcha) {
        /*
         * ===== 原验证码校验逻辑（已停用，需要时取消注释） =====
         *
         * String cachedCaptcha = redisTemplateString.opsForValue().get("imageCode");
         * if (cachedCaptcha == null) {
         *     return ResponseEntity.ok(false);
         * }
         * boolean isValid = cachedCaptcha.equalsIgnoreCase(inputCaptcha);
         * if (isValid) {
         *     redisTemplateString.delete("imageCode");
         * }
         * return ResponseEntity.ok(isValid);
         */
        return ResponseEntity.ok(true);
    }
}
