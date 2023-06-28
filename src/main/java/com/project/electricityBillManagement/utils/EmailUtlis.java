package com.project.electricityBillManagement.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class EmailUtlis {

    @Autowired
    private JavaMailSender mailSender;

    public void  sendSimpleMessage(String to, String subject, String text, List<String> list){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("windsorsuite555@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        if(list != null && list.size()>0)
            message.setCc(getCCArray(list));
        mailSender.send(message);
    }

    private String[] getCCArray(List<String> ccList){
        String[] cc = new String[ccList.size()];
        for(int i =0; i< ccList.size();i++){
            cc[i] = ccList.get(i);
        }
        return cc;
    }

    public void forgotEmail(String to,String subject,String url) throws MessagingException{
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setFrom("windsorsuite555@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        String htmlMsg = "<html><body style=\"font-family: Arial, sans-serif;\">"
                + "<h2 style=\"color: #008080;\">Reset Password</h2>"
                + "<p><strong>Email:</strong> " + to + "</p>"
                + "<p><strong>Password:</strong> 123456</p>"
                + "<p><strong>URL:</strong> " + url + "</p>"
                + "<p>Please click the following link to reset your password:</p>"
                + "<p><a href=\"http://localhost:4200/reset-password\">Reset Password</a></p>"
                + "<p>If you did not request a password reset, please ignore this email.</p>"
                + "<p>Best regards,<br>Electricity Bill Management Team</p>"
                + "</body></html>";
//        String htmlMsg = "<p><b>Reset Password</b><br><b>Email: </b> " + to + " <br><br><b>Password: </b> 123456 <br><b>Url: </b> " + url + "<br><a href=\"http://localhost:4200/\">Click here to login</a></p>";
        message.setContent(htmlMsg,"text/html");
        mailSender.send(message);
    }
}
