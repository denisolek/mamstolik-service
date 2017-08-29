package pl.denisolek.Email;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class EmailSenderImpl implements EmailSender {

    @Value("${spring.mail.username}")
    String MAIL_USERNAME;
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String target, String subject, String content) {
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(target);
            helper.setReplyTo(MAIL_USERNAME);
            helper.setFrom(MAIL_USERNAME, "MamStolik.pl");
            helper.setSubject(subject);
            helper.setText(content, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(mail);
    }
}