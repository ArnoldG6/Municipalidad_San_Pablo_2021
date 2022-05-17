/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.controller;

import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import cr.go.sanpablo.model.Notifications;
import cr.go.sanpablo.service.NotificationsServImpl;
import cr.go.sanpablo.service.NotificationsService;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author jegon
 */
@Controller
@RequestMapping(value = "/api/notifications")
public class NotificationsController {
    private NotificationsService service = new NotificationsServImpl();
    
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Notifications addNotification(@RequestBody Notifications notification) {
        try {
            Notifications saved = this.service.addNotification(notification);
            return saved;
        } catch (DaoExceptions | SQLException | ServiceExceptions ex) {
            Logger.getLogger(NotificationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
