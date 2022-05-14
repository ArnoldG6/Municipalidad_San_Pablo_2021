/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sfr.dao;

import common.dao.UserDAO;
import common.model.User;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.*;
import sfr.model.Plan;

/**
 *
 * @author Luis D
 */
public class EmailFactoryNotification {

    private static EmailFactoryNotification ef;

    private final String from = "pruebasinge071@gmail.com";
    private final String username = "pruebasinge071@gmail.com";
    private final String password = "pruebasSFR";
    private final String host = "imap.gmail.com";

    private Properties props;
    private final Session session;

    private EmailFactoryNotification() {
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

    public static EmailFactoryNotification getInstance() {
        if (ef == null) {
            ef = new EmailFactoryNotification();
        }
        return ef;
    }

    /**
     *
     * @param user which will get the email
     * @throws MessagingException
     */
    public void tempfun(User user) throws MessagingException {
        Message msj = new MimeMessage(session);
        msj.setFrom(new InternetAddress(from));
        msj.setRecipient(
                Message.RecipientType.TO,
                new InternetAddress(user.getEmail())
        );

        msj.setSubject("Reinicio de contraseña");

        String htmlCode
                = "<h3>Estimado/a " + user.getOfficial().getName() + "<br/>"
                + "Hemos recibido una solicitud de cambio de contraseña en su cuenta.<br/>"
                + "Por favor ingrese el siguiente código en el campo solicitado en el sistema:</h3>"
                + "<h1> cambienme :v </h1>"
                + "<h3>Si usted no realizó esta solicitud, por favor ponerse en contacto con su correspondiente Administrador Tecnológico</h3>"
                + "<h5>Este es un mensaje automático, por favor no responda a el mismo.</h5>";

        msj.setContent(htmlCode, "text/html");

        Transport.send(msj);
        System.err.println("Done");
    }
    
    public void sendAddPlan(User user, Plan p) throws MessagingException {
        User controlInterno = UserDAO.getInstance().searchById(51);
        Message msj = new MimeMessage(session);
        msj.setFrom(new InternetAddress(from));
        msj.setRecipient(
                Message.RecipientType.TO,
                new InternetAddress("mgonzalex236@gmail.com")
        );

        msj.setSubject("Ingreso de un Plan");

        String htmlCode
                = "<h3>Estimado/a " + controlInterno.getOfficial().getName()+" "+ controlInterno.getOfficial().getSurname() + "<br/>"
                + ".<br/>"
                + "Este mensaje automático por el SFR es para notificarle lo siguiente:</h3>"
                + "<h1>El usuario "+ user.getOfficial().getName()+" "+ user.getOfficial().getSurname() +" ha ingresado un nuevo plan ID: "+ p.getId() +".</h1>"
                + "<h3>Si usted desea acceder al nuevo plan creado, por favor acceda al siguiente enlace: http://localhost:3001/SFR/#/plan?id="+p.getId()+"</h3>"
                + "<h5>Este es un mensaje automático, por favor no responda a el mismo.</h5>";

        msj.setContent(htmlCode, "text/html");

        Transport.send(msj);
        System.err.println("Done");
    }

}
