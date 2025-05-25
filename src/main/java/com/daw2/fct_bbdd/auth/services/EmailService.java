package com.daw2.fct_bbdd.auth.services;

public interface EmailService {

    void sendVerificationEmail(String toEmail, String token);

    void sendPasswordResetEmail(String toEmail, String resetLink);
}
