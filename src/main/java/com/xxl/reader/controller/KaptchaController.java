package com.xxl.reader.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class KaptchaController {
    @Resource
    private Producer kaptchaProducer;

    @GetMapping("/verify_code")
    public void createVerifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store,no-cache,must-revalidate");
        response.setHeader("Cache-Control", "post-check=0,pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/png");

        // 生成验证码字符文本
        String verifyCode = kaptchaProducer.createText();
        // 将正确的文本放入到当前会话中
        request.getSession().setAttribute("kaptchaVerifyCode", verifyCode);
        System.out.println(request.getSession().getAttribute("kaptchaVerifyCode"));
        // 创建验证码图片 - 二进制
        BufferedImage image = kaptchaProducer.createImage(verifyCode);
        ServletOutputStream out = response.getOutputStream();
        // 向客户端发送图片
        ImageIO.write(image, "png", out);
        out.flush();
        out.close();
    }
}
