package com.feed.news.service;


import com.feed.news.entity.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {

    @Autowired()
    private JavaMailSender emailSender;


    public void sendEmail(Mail mail) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            helper.setTo(mail.getTo());
            helper.setText(mail.getContent());
            helper.setSubject(mail.getSubject());
            helper.setFrom(mail.getFrom());
            emailSender.send(message);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
