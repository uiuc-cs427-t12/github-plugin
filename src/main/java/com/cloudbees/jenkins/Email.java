package com.cloudbees.jenkins;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Used to send email to multiple account from gmail.
 * @author Yinshen Tang
 *
 */
public class Email {

    private final static String username = "jenkinsgithubt12@gmail.com";
    private final static String password = "qasxedfv";

    /**
     * send email to multiple account
     * @param address : address of receipts
     * @param title : email title
     * @param content : email content
     * @param isHtml : true for html content, false for text content
     */
    private static void sendEmail(String[] address, String title,
            String content, boolean isHtml) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("jenkinsgithubt12@gmail.com",
                    "Jenkins-Github admin"));
            for (int i = 0; i < address.length; i++) {
                message.addRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(address[i]));
            }
            message.setSubject(title);
            if (isHtml) {
                message.setContent(content, "text/html");
            } else {
                message.setText(content);
            }
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Send text email
     * @param address : address of receipts
     * @param title : email title
     * @param content : email content
     */
    public static void sendEmail(String[] address, String title, String content) {
        sendEmail(address, title, content, false);
    }
    
    /**
     * Send html email
     * @param address : address of receipts
     * @param title : email title
     * @param content : email content
     */
    public static void sendHtmlEmail(String[] address, String title, String content) {
        sendEmail(address, title, content, true);
    }

    /*
    public static void main(String[] args) {
        //manual testing function
        String[] address = { "tomtang0514@gmail.com" };
        sendHtmlEmail(
                address,
                "test email from machine",
                "<a href='https://wiki.engr.illinois.edu/display/cs427fa13/T12'>Test html content</a>");
    }
    */

}
