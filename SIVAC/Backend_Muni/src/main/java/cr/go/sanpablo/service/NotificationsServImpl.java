/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.service;

import cr.go.sanpablo.dao.NotificationsDao;
import cr.go.sanpablo.dao.NotificationsDaoImpl;
import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import cr.go.sanpablo.model.Notifications;
import java.sql.SQLException;

/**
 *
 * @author jegon
 */
public class NotificationsServImpl implements NotificationsService{
    private NotificationsDao dao;

    @Override
    public Notifications addNotification(Notifications notification) throws DaoExceptions, SQLException, ServiceExceptions {
        Notifications saved;
        this.dao = new NotificationsDaoImpl();
        saved = this.dao.addNotification(notification);
        return saved;
    }
    
}
