package com.daw2.fct_bbdd.auth.services.impl;

import com.daw2.fct_bbdd.auth.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendVerificationEmail(String toEmail, String token) {
        String subject = "Verifica tu cuenta";
        String verificationUrl = "http://localhost:4200/verify?token=" + token;
        String body = "Haz clic en el siguiente enlace para verificar tu cuenta:\n" + verificationUrl;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}
