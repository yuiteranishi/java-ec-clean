package com.example.ec.domain.user.port;

public interface MailPort {
    void send (String to, String subject, String body);
}
