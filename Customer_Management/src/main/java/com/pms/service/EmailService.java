package com.pms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("policytrustproject@gmail.com");

        mailSender.send(message);
    }
    
    public void sendAccountStatusEmail(String email, String name, boolean isApproved) {
        String subject = isApproved ? "Account Approved" : "Account Rejected";
        String body = "Dear " + name + ",\n\n" +
                      (isApproved 
                          ? "Congratulations! Your account has been approved. You can now log in and use our services."
                          : "We regret to inform you that your account has been rejected/deactivated by Admin.\n\n Please Register with another Email.") +
                      "\n\nThank you,\nPolicy Trust Team";

        sendEmail(email, subject, body);
    }
}
