package com.Binde.swiftwallet.service;

import com.Binde.swiftwallet.dtos.request.EmailDto;
import jakarta.mail.MessagingException;


public interface SendMailService {
    void sendEmail(EmailDto emailDto);
    void sendEmailWithAttachment(EmailDto emailDto) throws MessagingException;
}
