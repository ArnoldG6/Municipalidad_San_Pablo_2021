/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package common.dao;

import common.model.User;
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
    private final String password = "ghvmdhhdizpwykpk";
    private final String host = "imap.gmail.com";

    private Properties props;
    private final Session session;

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

    public void sendResetPassword(User user, String code) throws MessagingException {
        Message msj = new MimeMessage(session);
        msj.setFrom(new InternetAddress(from));
        msj.setRecipient(
                Message.RecipientType.TO,
                new InternetAddress(user.getEmail())
        );

        msj.setSubject("Reinicio de contraseña");

        String htmlCode
                = "<h3>Estimado/a " + user.getOfficial().getName() + "<br/>"
                + "Hemos recibido una solicitud de cambio de contraseña para su cuenta en el Sistema de Identificación Municipal.<br/>"
                + "Por favor ingrese el siguiente código en el campo solicitado en el sistema:</h3>"
                + "<h1>" + code + "</h1>"
                + "<h3>Si usted no realizó esta solicitud, por favor ponerse en contacto con su correspondiente Administrador Tecnológico</h3>"
                + "<h5>Este es un mensaje automático, por favor no responda a el mismo.</h5>";

        msj.setContent(htmlCode, "text/html; charset=UTF-8");

        Transport.send(msj);
    }

    /**
     *
     * @param user which will get the email
     * @throws MessagingException
     */
    public void sendAddUser(User user) throws MessagingException {
        Message msj = new MimeMessage(session);
        msj.setFrom(new InternetAddress(from));
        msj.setRecipient(
                Message.RecipientType.TO,
                new InternetAddress(user.getEmail())
        );
        msj.setSubject("Ingreso de un usuario");
        String htmlCode
                = "<h3>Estimado/a " + user.getOfficial().getName() + " " + user.getOfficial().getSurname() + ".<br/>"
                + "Este mensaje automático por el Sistema de Identificación Municipal es para notificarle lo siguiente:</h3>"
                + "<h4>Se ha creado su usuario exitosamente.</h4>"
                + "<h4>Puede ingresar a las funcionalidades del sistema utilizando sus credenciales en el siguiente link: http://localhost:3001/SIMSP/</h4>"
                + "<h5>Este es un mensaje automático, por favor no responda a el mismo.</h5>";
        msj.setContent(htmlCode, "text/html; charset=UTF-8");
        Transport.send(msj);
    }

}
