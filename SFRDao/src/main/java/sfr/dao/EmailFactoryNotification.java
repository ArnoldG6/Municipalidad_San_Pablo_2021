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
import sfr.model.Comment;
import sfr.model.Incidence;
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
     * @param newUser new user added
     * @throws MessagingException
     */
        public void sendAddUser(User user, User newUser) throws MessagingException {
        User controlInterno = UserDAO.getInstance().searchById(50);
        Message msj = new MimeMessage(session);
        msj.setFrom(new InternetAddress(from));
        msj.setRecipient(
                Message.RecipientType.TO,
                new InternetAddress(user.getEmail())
        );
        msj.setSubject("Ingreso de un usuario");
        String htmlCode
                = "<h3>Estimado/a " + controlInterno.getOfficial().getName()+" "+ controlInterno.getOfficial().getSurname() + "<br/>"
                + ".<br/>"
                + "Este mensaje automático por el SFR es para notificarle lo siguiente:</h3>"
                + "<h1>El usuario "+ user.getOfficial().getName()+" "+ user.getOfficial().getSurname() +" ha sido ingresado.</h1>"
                + "<h5>Este es un mensaje automático, por favor no responda a el mismo.</h5>";
        msj.setContent(htmlCode, "text/html");
        Transport.send(msj);
        System.err.println("Done");
    }
     /**
     *
     * @param user which will get the email
     * @param user2 who modify the plan
     * @throws MessagingException
     */
    
    public void sendEditPlan(User user, User user2, Plan p) throws MessagingException {
        Message msj = new MimeMessage(session);
        msj.setFrom(new InternetAddress(from));
        msj.setRecipient(
                Message.RecipientType.TO,
                new InternetAddress(user.getEmail())
        );
        msj.setSubject("Modificación de un Plan");
        String htmlCode
                = "<h3>Estimado/a " + user.getOfficial().getName() + " " + user.getOfficial().getSurname() + "<br/>"
                + ".<br/>"
                + "Este mensaje automático por el SFR es para notificarle lo siguiente:</h3>"
                + "<h1>El usuario " + user2.getOfficial().getName() + " " + user2.getOfficial().getSurname() + " ha modificado el plan ID: " + p.getId() + ".</h1>"
                + "<h3>Si usted desea acceder al plan, por favor acceda al siguiente enlace: http://localhost:3001/SFR/#/plan?id=" + p.getId() + "</h3>"
                + "<h5>Este es un mensaje automático, por favor no responda a el mismo.</h5>";
        msj.setContent(htmlCode, "text/html");
        Transport.send(msj);
        System.err.println("Done");
    }
    
    public void sendAllEditPlan(User user, Plan p) throws MessagingException {
        User controlInterno = UserDAO.getInstance().searchById(51);
        for(User involved: p.getInvolvedList()){
            sendEditPlan(involved, user, p);
        }
        sendEditPlan(controlInterno, user, p);
    }
    
    public void sendAddPlan(User user, Plan p) throws MessagingException {
        User controlInterno = UserDAO.getInstance().searchById(51);
        Message msj = new MimeMessage(session);
        msj.setFrom(new InternetAddress(from));
        msj.setRecipient(
                Message.RecipientType.TO,
                new InternetAddress(controlInterno.getEmail())
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
    
    public void sendAddInvolvedPlan(User user, Plan p) throws MessagingException {
        User controlInterno = UserDAO.getInstance().searchById(51);
        Message msj = new MimeMessage(session);
        msj.setFrom(new InternetAddress(from));
        msj.setRecipient(
                Message.RecipientType.TO,
                new InternetAddress(controlInterno.getEmail())
        );

        msj.setSubject("Ingreso de Nuevo Involucrado a un Plan");

        String htmlCode
                = "<h3>Estimado/a " + controlInterno.getOfficial().getName()+" "+ controlInterno.getOfficial().getSurname() + "<br/>"
                + ".<br/>"
                + "Este mensaje automático por el SFR es para notificarle lo siguiente:</h3>"
                + "<h1>El usuario "+ user.getOfficial().getName()+" "+ user.getOfficial().getSurname() +" ha sido ingresado como involucrado en el plan ID: "+ p.getId() +".</h1>"
                + "<h3>Si usted desea acceder al plan, por favor acceda al siguiente enlace: http://localhost:3001/SFR/#/plan?id="+p.getId()+"</h3>"
                + "<h5>Este es un mensaje automático, por favor no responda a el mismo.</h5>";

        msj.setContent(htmlCode, "text/html");

        Transport.send(msj);
        System.err.println("Done");
    }
    
    public void sendAddCommentPlan(User user, Plan p, Comment c) throws MessagingException {
        User controlInterno = UserDAO.getInstance().searchById(51);
        Message msj = new MimeMessage(session);
        msj.setFrom(new InternetAddress(from));
        msj.setRecipient(
                Message.RecipientType.TO,
                new InternetAddress(controlInterno.getEmail())
        );
        msj.setSubject("Ingreso de Nuevo Comentario a un Plan");
        String htmlCode
                = "<h3>Estimado/a " + controlInterno.getOfficial().getName()+" "+ controlInterno.getOfficial().getSurname() + "<br/>"
                + ".<br/>"
                + "Este mensaje automático por el SFR es para notificarle lo siguiente:</h3>"
                + "<h1>El usuario "+ user.getOfficial().getName()+" "+ user.getOfficial().getSurname() +" ha ingresado un comentario en el plan ID: "+ p.getId() +".</h1>"
                + "<h3>El contenido del comentario es el siguiente: "+c.getComment()+"</h3>"
                + "<h5>Este es un mensaje automático, por favor no responda a el mismo.</h5>";
        msj.setContent(htmlCode, "text/html");
        Transport.send(msj);
        System.err.println("Done");
    }

    public void sendAddIncidencePlan(User user, Plan p, Incidence i) throws MessagingException {
        User controlInterno = UserDAO.getInstance().searchById(51);
        Message msj = new MimeMessage(session);
        msj.setFrom(new InternetAddress(from));
        msj.setRecipient(
                Message.RecipientType.TO,
                new InternetAddress(controlInterno.getEmail())
        );
        msj.setSubject("Ingreso de Nueva Incidencia a un Plan");
        String htmlCode
                = "<h3>Estimado/a " + controlInterno.getOfficial().getName()+" "+ controlInterno.getOfficial().getSurname() + "<br/>"
                + ".<br/>"
                + "Este mensaje automático por el SFR es para notificarle lo siguiente:</h3>"
                + "<h1>El usuario "+ user.getOfficial().getName()+" "+ user.getOfficial().getSurname() +" ha ingresado una incidencia en el plan ID: "+ p.getId() +".</h1>"
                + "<h3>El nombre de la incidencia es: "+i.getName()+"</h3>"
                + "<h5>Este es un mensaje automático, por favor no responda a el mismo.</h5>";
        msj.setContent(htmlCode, "text/html");
        Transport.send(msj);
        System.err.println("Done");
    }
}
