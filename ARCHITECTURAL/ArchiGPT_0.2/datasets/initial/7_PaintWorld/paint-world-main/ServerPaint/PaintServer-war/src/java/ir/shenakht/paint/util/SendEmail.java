/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.util;

import ir.shenakht.paint.domain.User;
import ir.shenakht.paint.logic.interfaces.UserLogicIntf;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import javax.ejb.EJB;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author hossien
 */
public class SendEmail {

    @EJB
    UserLogicIntf ul;

    public void send_tls_html_mail(String to[], String subject, String txtMSG, String htmlMSG, Message.RecipientType type) {
        Properties pr = new Properties();
        pr.put("mail.host", ConstantValues.EmailValues.host);
        pr.put("mail.smtp.port", ConstantValues.EmailValues.TLS_port);
        pr.put("mail.smtp.starttls.enable", true);
        Authenticator auth = null;
        if (ConstantValues.EmailValues.authentication) {
            pr.put("mail.smtp.auth", true);
            auth = new Authenticator() {
                PasswordAuthentication pa
                        = new PasswordAuthentication(ConstantValues.EmailValues.username, ConstantValues.EmailValues.password);

                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return pa;
                }

            };
        }

//        Session session = Session.getDefaultInstance(pr, auth);
        Session session = Session.getInstance(pr,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(ConstantValues.EmailValues.username, ConstantValues.EmailValues.password);
            }
        });

        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(ConstantValues.EmailValues.from));
            InternetAddress recipients[] = new InternetAddress[to.length];
            for (int i = 0; i < to.length; i++) {
                recipients[i] = new InternetAddress(to[i]);
            }
            message.setRecipients(type, recipients);
            message.setSubject(subject);
            message.setSentDate(new Date());

            Multipart multipart = new MimeMultipart("alternative");

            //txtMSG :
            MimeBodyPart txtPart = new MimeBodyPart();
            txtPart.setText(txtMSG);

            //htmlMSG :
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(htmlMSG, "text/html; charset=utf-8");

            multipart.addBodyPart(txtPart);
            multipart.addBodyPart(htmlPart);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("HTML Message has been sent");
        } catch (Exception ex) {
            System.out.println("Error!" + ex.getMessage());
        }
    }

    public void sendMessageToEmail(String to[], User user, String password, String appName) throws UnsupportedEncodingException {
        String txtMSG = "بازيابي رمز شما در " + appName;
        String htmlMSG = "<div dir=\"rtl\">" + "سلام" + " " + user.getFirstName() + " " + user.getLastName() + " "
                + "</h1> <p>نام کاربری : <span color:\"blue\">" + user.getFirstName() + " " + user.getLastName() + "</span></p></html>"
                + "</h1> <p>رمز عبور جدید : <span color:\"blue\">" + password + "</span></p>"
                + "<br /><p>شما می توانید در صورت تمایل به تغییر این پسورد در قسمت ویرایش اطلاعات بعد از ورود به نرم افزار   " + " " + appName + " " + "اقدام کنید .</p><br /><p>توجه</p></html></div>";
        System.out.println(htmlMSG);
        send_tls_html_mail(to, txtMSG, txtMSG, htmlMSG, Message.RecipientType.TO);
    }

}
