package com.example.phoenixstorebe.service.impl;

import com.example.phoenixstorebe.exception.BadRequestException;
import com.example.phoenixstorebe.service.MailService;
import lombok.RequiredArgsConstructor;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;

    @Override
    public void sendVerificationMail(String to, String verifyLink, String username) {
        String subject = "Xác thực tài khoản Phoenix Store";
        String htmlContent = String.format("""
                <div style='font-family: Arial, sans-serif; max-width: 480px; margin: auto; border: 1px solid #eee; border-radius: 8px; padding: 24px;'>
                    <h2 style='color: #2d8cf0;'>Chào %s,</h2>
                    <p>Cảm ơn bạn đã đăng ký tài khoản tại <b>Phoenix Store</b>.</p>
                    <p>Vui lòng nhấn vào nút bên dưới để xác thực tài khoản:</p>
                    <div style='text-align: center; margin: 24px 0;'>
                        <a href='%s' style='background: #2d8cf0; color: #fff; padding: 12px 32px; border-radius: 4px; text-decoration: none; font-weight: bold;'>Xác thực tài khoản</a>
                    </div>
                    <p>Nếu bạn không đăng ký tài khoản, vui lòng bỏ qua email này.</p>
                    <hr>
                    <div style='font-size: 12px; color: #888;'>Phoenix Store &copy; 2026</div>
                </div>
                """, username, verifyLink);
        sendHtmlMail(to, subject, htmlContent);
    }

    @Override
    public void sendHtmlMail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new BadRequestException("Send HTML mail failed: " + e.getMessage());
        }
    }
}
