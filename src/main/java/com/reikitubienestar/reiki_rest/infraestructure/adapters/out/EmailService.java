package com.reikitubienestar.reiki_rest.infraestructure.adapters.out;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom("juanfajardoreyes@hotmail.com"); // Asegúrate de usar el mismo correo configurado en spring.mail.username
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
