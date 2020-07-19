package com.hpkarugendo.components;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class EmailComponent extends SimpleMailMessage {

    private JavaMailSender sender;
    private SimpleMailMessage message;

    public EmailComponent(JavaMailSender sender) {
        this.sender = sender;
    }

    public void sendMessage(String to, String from, String subject, String message){
        this.message = new SimpleMailMessage();
        this.message.setReplyTo("noreply@hpkarugendo.com");
        this.message.setTo(to == null ? "henry.p.karugendo@gmail.com" : to);
        this.message.setFrom(to == null ? "system@hpkarugendo.com" : from);
        this.message.setSubject(subject);
        this.message.setText(message);
        this.message.setSentDate(new Date());
        this.sender.send(this.message);

    }


}
