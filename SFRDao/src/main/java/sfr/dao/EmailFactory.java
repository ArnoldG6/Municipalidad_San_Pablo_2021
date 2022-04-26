/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sfr.dao;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.*;

/**
 *
 * @author Luis D
 */
public class EmailFactory {

    private static EmailFactory ef;

    private final String from = "pruebasinge071@gmail.com";
    private final String username = "pruebasinge071@gmail.com";
    private final String password = "pruebasSFR";
    private final String host = "imap.gmail.com";

    private Properties props;
    private Session session;

    private EmailFactory() {
        this.props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.imap.ssl.enable", "true");
        
        session = Session.getInstance(props,
                new jakarta.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        }
        );
    }

    public static EmailFactory getInstance() {
        if (ef == null) {
            ef = new EmailFactory();
        }
        return ef;
    }

    public void sendResetPassword(String to) throws MessagingException {
        Message msj = new MimeMessage(session);
        msj.setFrom(new InternetAddress(from));
        msj.setRecipient(
                Message.RecipientType.TO,
                new InternetAddress(to)
        );
        msj.setSubject("Reinicio de contraseña");
        msj.setText("Usted ha solicitado un reinicio de contraseña.");

        Transport.send(msj);
        System.err.println("Done");
    }

}
