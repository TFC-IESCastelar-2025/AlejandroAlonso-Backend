package com.daw2.fct_bbdd.auth.services.impl;

import com.daw2.fct_bbdd.auth.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendVerificationEmail(String toEmail, String token) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setFrom("Soulsdle");
            helper.setSubject("Verifica tu cuenta");

            String verificationUrl = "http://localhost:4200/verify?token=" + token;

            String htmlContent = "<div style='background-color: #f8f9fa; padding: 20px; font-family: Arial, sans-serif;'>"
                    + "<h2 style='color: #343a40;'>¡Bienvenido!</h2>"
                    + "<p>Gracias por registrarte. Haz clic en el botón para activar tu cuenta:</p>"
                    + "<a href='" + verificationUrl + "' "
                    + "style='display: inline-block; padding: 10px 20px; background-color: #007bff; color: white; "
                    + "text-decoration: none; border-radius: 5px; font-weight: bold;'>Activar cuenta</a>"
                    + "<p style='margin-top: 20px; color: #6c757d;'>Este enlace expirará en 24 horas.</p>"
                    + "</div>";

            helper.setText(htmlContent, true);

            message.setHeader("X-Mailer", "Spring Boot");
            message.setHeader("Precedence", "bulk");
            message.setHeader("X-Priority", "3");

            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
