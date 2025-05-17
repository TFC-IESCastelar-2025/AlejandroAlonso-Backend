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
            helper.setSubject("Verifica tu cuenta");
            helper.setText("<p>Haz clic en el siguiente enlace para verificar tu cuenta:</p>"
                    + "<a href=\"http://localhost:4200/verify?token=" + token + "\">Verificar cuenta</a>", true);
            helper.setFrom("noreply@tudominio.com");

            // ✅ Añadir cabeceras extra
            message.setHeader("X-Mailer", "Spring Boot");
            message.setHeader("Precedence", "bulk");
            message.setHeader("X-Priority", "3");

            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
