package com.Binde.swiftwallet.service.implementations;

import com.Binde.swiftwallet.exceptions.CustomException;
import com.Binde.swiftwallet.dtos.request.EmailDto;
import com.Binde.swiftwallet.service.SendMailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendMailServiceImpl implements SendMailService {
    private final JavaMailSender mailSender;

    @Async
    public void sendEmail(EmailDto emailDto) {
        log.info("inside Send email, building mail!!");
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom(emailDto.getSender());
            mimeMessageHelper.setTo(emailDto.getRecipient());
            mimeMessageHelper.setSubject(emailDto.getSubject());
            mimeMessageHelper.setText(emailDto.getBody());
        };

        try {
            CompletableFuture.runAsync(() ->
            mailSender.send(mimeMessagePreparator)).exceptionally(exp -> {
                throw new CustomException("Exception occurred sending mail [message]: " + exp.getLocalizedMessage());
            });
            log.info("email has sent!!");
        }catch (MailException exception) {
            log.error("Exception occurred when sending mail {}",exception.getMessage());
            throw new CustomException("Exception occurred when sending mail to " + emailDto.getRecipient(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Async
    public void sendEmailWithAttachment(EmailDto emailDto) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom("noreply@gmail.com");
        mimeMessageHelper.setTo(emailDto.getRecipient());
        mimeMessageHelper.setSubject(emailDto.getSubject());
        mimeMessageHelper.setText(emailDto.getBody());

        try {
            FileSystemResource fileSystemResource =
                    new FileSystemResource(new File(emailDto.getAttachment()));

            mimeMessageHelper
                    .addAttachment(Objects
                            .requireNonNull(fileSystemResource.getFilename()),fileSystemResource);

            mailSender.send(mimeMessage);
            log.info("email has sent!!");
        } catch (MailException exception) {
            log.error("Exception occurred when sending mail {}",exception.getMessage());
            throw new CustomException("Exception occurred when sending mail to " + emailDto.getRecipient(), HttpStatus.EXPECTATION_FAILED);
        }
    }
}
