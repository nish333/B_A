package com.MPKA_Group.demo.service.impl;

import java.io.File;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.MPKA_Group.demo.dto.EmailDetails;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
@Component
public class EmailServiceImpl implements EmailService 
{
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    private static Logger log = LogManager.getLogger(EmailServiceImpl.class);

    @Override
    public void sendEmailAlert(EmailDetails emailDetails) 
    {
        try 
        {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(senderEmail);
            String recipient = emailDetails.getRecipient();
            if (recipient != null) 
            {
                mailMessage.setTo(recipient);
            } 
            else 
            {
                log.error("Recipient email address is null.");
                return;
            }
            mailMessage.setText(emailDetails.getMessageBody());
            mailMessage.setSubject(emailDetails.getSubject());

            javaMailSender.send(mailMessage);
            System.out.println("Mail sent Successfully.....");
        } 
        catch (MailException e) 
        {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void sendEmailWithAttachment(EmailDetails emailDetails) 
    {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try 
        {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(senderEmail);
            String recipient = emailDetails.getRecipient();
            if (recipient != null) 
            {
                mimeMessageHelper.setTo(recipient);
            }
            else 
            {
                log.error("Recipient email address is null.");
                return;
            }
            mimeMessageHelper.setText(emailDetails.getMessageBody());
            mimeMessageHelper.setSubject(emailDetails.getSubject());

            File attachmentFile = new File(emailDetails.getAttachment());
            if (attachmentFile.exists()) 
            {
                FileSystemResource file = new FileSystemResource(attachmentFile);
                mimeMessageHelper.addAttachment(Objects.requireNonNull(file.getFilename()), file);
            } 
            else 
            {
                log.error("Attachment file does not exist: " + emailDetails.getAttachment());
                return;
            }
            javaMailSender.send(mimeMessage);

            log.info("Attachment " + attachmentFile.getName() + " has been sent to user with email " + recipient);
        } 
        catch (MessagingException e) 
        {
            throw new RuntimeException(e);
        }

    }

}
