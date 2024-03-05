package tn.esprit.crud.services;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class SendingMail {
    public static void send(String recepient,int mot) throws MessagingException {
        System.out.println("Preparing Send email");
        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.timeout", "5000");
        String myAccountEmail ="nasri.youssef@esprit.tn";
        String password ="gfrq ypcd qlbv xjdn";
        Session session = Session.getDefaultInstance(props,new Authenticator()
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });
        Message message = prepareMessage(session,myAccountEmail,recepient,mot);
        Transport.send(message);
        System.out.println("message sent");
    }

    private static Message prepareMessage(Session session,String myAccountEmail,String recepient,int mot) throws AddressException, MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(myAccountEmail));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
        message.setSubject("password reset");
        message.setText(mot+" your verification code");
        return message;
    }

    public static void sendBody(String recepient, String subject, String body) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.timeout", "5000");
        String myAccountEmail ="nasri.youssef@esprit.tn";
        String password ="gfrq ypcd qlbv xjdn";
        Session session = Session.getDefaultInstance(props,new Authenticator()
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });
        Message message = prepareMessage(session, myAccountEmail, recepient, subject, body);
        Transport.send(message);
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recepient, String subject, String body) throws AddressException, MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(myAccountEmail));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
        message.setSubject(subject);
        message.setText(body);
        return message;
    }

}
