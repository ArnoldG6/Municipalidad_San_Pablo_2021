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

    private final String from = "webmaster@sanpablo.go.cr";
    private final String username = "webmaster@sanpablo.go.cr";
    private final String password = "Cristal87";
    private final String host = "smtp.office365.com";

    private Properties props;
    private final Session session;

    private EmailFactoryNotification() {
        this.props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        //props.setProperty("mail.transport.protocol", "smtp");
        //props.setProperty("mail.debug", "false");

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
        msj.setSubject("Modificación del Plan " + p.getId());
        String htmlCode
                = "<h3>Estimado/a " + user.getOfficial().getName() + " " + user.getOfficial().getSurname() + ".<br/>"
                + "Este mensaje es generado por el Sistema de Factibilidad de Riesgos para notificarle que:</h3>"
                + "<h4>El usuario " + user2.getOfficial().getName() + " " + user2.getOfficial().getSurname() + " ha modificado el siguiente Plan:</h4>"
                + "<h3>" + p.getName() + "</h3>"
                + "<h4>Si desea ver los cambios, por favor ingrese al siguiente enlace: http://10.58.0.88/SFR/#/plan?id=" + p.getId() + "</h4>"
                + "<h5>Este es un mensaje automático, por favor no responda a el mismo.</h5>";
        msj.setContent(htmlCode, "text/html; charset=UTF-8");
        Transport.send(msj);
    }

    public void sendAllEditPlan(User user, Plan p) throws MessagingException {
        User controlInterno = UserDAO.getInstance().searchByEmail("controlinterno@sanpablo.go.cr");
        for (User involved : p.getInvolvedList()) {
            if (!involved.getEmail().equals(controlInterno.getEmail())) {
                sendEditPlan(involved, user, p);
            }
        }
        sendEditPlan(controlInterno, user, p);
    }

    public void sendAddPlan(User user, Plan p) throws MessagingException {
        User controlInterno = UserDAO.getInstance().searchByEmail("controlinterno@sanpablo.go.cr");
        Message msj = new MimeMessage(session);
        msj.setFrom(new InternetAddress(from));
        msj.setRecipient(
                Message.RecipientType.TO,
                new InternetAddress(controlInterno.getEmail())
        );
        msj.setSubject("Ingreso de un Plan Nuevo");
        String htmlCode
                = "<h3>Estimado/a " + controlInterno.getOfficial().getName() + " " + controlInterno.getOfficial().getSurname() + ".<br/>"
                + "Este mensaje es generado por el Sistema de Factibilidad de Riesgos para notificarle que:</h3>"
                + "<h4>El usuario " + user.getOfficial().getName() + " " + user.getOfficial().getSurname() + " ha ingresado un nuevo Plan.</h4>"
                + "<h4>Si desea acceder al nuevo plan creado, por favor ingrese al siguiente enlace: http://10.58.0.88/SFR/#/plan?id=" + p.getId() + "</h4>"
                + "<h5>Este es un mensaje automático, por favor no responda a el mismo.</h5>";
        msj.setContent(htmlCode, "text/html; charset=UTF-8");
        Transport.send(msj);
    }

    public void sendAddInvolvedPlan(User user, Plan p) throws MessagingException {
        Message msj = new MimeMessage(session);
        msj.setFrom(new InternetAddress(from));
        msj.setRecipient(
                Message.RecipientType.TO,
                new InternetAddress(user.getEmail())
        );

        msj.setSubject("Ingreso de Nuevo Involucrado al Plan " + p.getId());

        String htmlCode
                = "<h3>Estimado/a " + user.getOfficial().getName() + " " + user.getOfficial().getSurname() + ".<br/>"
                + "Este mensaje es generado por el Sistema de Factibilidad de Riesgos para notificarle que:</h3>"
                + "<h4>Usted ha sido ingresado como involucrado en el siguiente Plan:</h4>"
                + "<h3>" + p.getName() + "</h3>"
                + "<h4>Si desea acceder al plan, por favor ingrese al siguiente enlace: http://10.58.0.88/SFR/#/plan?id=" + p.getId() + "</h4>"
                + "<h5>Este es un mensaje automático, por favor no responda a el mismo.</h5>";

        msj.setContent(htmlCode, "text/html; charset=UTF-8");

        Transport.send(msj);
    }

    public void sendAllCommentPlan(User user, Plan p, Comment c) throws MessagingException {
        for (User involved : p.getInvolvedList()) {
            sendAddCommentPlan(involved, p, c);
        }
    }

    public void sendAddCommentPlan(User user, Plan p, Comment c) throws MessagingException {
        Message msj = new MimeMessage(session);
        msj.setFrom(new InternetAddress(from));
        msj.setRecipient(
                Message.RecipientType.TO,
                new InternetAddress(user.getEmail())
        );
        msj.setSubject("Ingreso de Nuevo Comentario al Plan " + p.getId());
        String htmlCode
                = "<h3>Estimado/a " + user.getOfficial().getName() + " " + user.getOfficial().getSurname() + ".<br/>"
                + "Este mensaje es generado por el Sistema de Factibilidad de Riesgos para notificarle que:</h3>"
                + "<h4>El usuario " + c.getAuthor() + " ha ingresado un comentario en el siguiente Plan:</h4>"
                + "<h3>" + p.getName() + "</h3>"
                + "<h4>El contenido del comentario es el siguiente: <br/>" + c.getComment() + "</h4>"
                + "<h4>Si desea acceder al plan, por favor ingrese al siguiente enlace: http://10.58.0.88/SFR/#/plan?id=" + p.getId() + "</h4>"
                + "<h5>Este es un mensaje automático, por favor no responda a el mismo.</h5>";
        msj.setContent(htmlCode, "text/html; charset=UTF-8");
        Transport.send(msj);
    }

    public void sendAddIncidencePlan(User user, Plan p, Incidence i) throws MessagingException {
        User controlInterno = UserDAO.getInstance().searchByEmail("controlinterno@sanpablo.go.cr");
        Message msj = new MimeMessage(session);
        msj.setFrom(new InternetAddress(from));
        msj.setRecipient(
                Message.RecipientType.TO,
                new InternetAddress(controlInterno.getEmail())
        );
        msj.setSubject("Ingreso de Nueva Incidencia al Plan " + p.getId());
        String htmlCode
                = "<h3>Estimado/a " + controlInterno.getOfficial().getName() + " " + controlInterno.getOfficial().getSurname() + ".<br/>"
                + "Este mensaje es generado por el Sistema de Factibilidad de Riesgos para notificarle que:</h3>"
                + "<h4>El usuario " + user.getOfficial().getName() + " " + user.getOfficial().getSurname() + " ha ingresado una incidencia en el siguiente Plan:</h4>"
                + "<h3>" + p.getName() + "</h3>"
                + "<h4>El nombre de la incidencia es: " + i.getName() + "</h4>"
                + "<h4>Si desea acceder al plan, por favor ingrese al siguiente enlace: http://10.58.0.88/SFR/#/plan?id=" + p.getId() + "</h4>"
                + "<h5>Este es un mensaje automático, por favor no responda a el mismo.</h5>";
        msj.setContent(htmlCode, "text/html; charset=UTF-8");
        Transport.send(msj);
    }
}
