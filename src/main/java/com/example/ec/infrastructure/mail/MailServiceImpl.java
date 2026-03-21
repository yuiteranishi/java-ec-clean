package com.example.ec.infrastructure.mail;

import com.example.ec.domain.user.port.MailPort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailServiceImpl implements MailPort {
    private final JavaMailSender sender;

    public MailServiceImpl(JavaMailSender sender)
    {
        this.sender = sender;
    }

    @Override
    public void send(String to, String subject, String body)
    {
        var msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(body);
        sender.send(msg);
    }

}
