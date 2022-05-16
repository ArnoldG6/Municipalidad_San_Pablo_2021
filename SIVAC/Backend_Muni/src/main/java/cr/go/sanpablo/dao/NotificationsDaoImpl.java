/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.dao;

import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import cr.go.sanpablo.model.Notifications;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author jegon
 */
public class NotificationsDaoImpl implements NotificationsDao{
    EntityManager manage = HibernateNotificationsUtil.getSessionManager();

    @Override
    public Notifications addNotification(Notifications notification) throws DaoExceptions, SQLException, ServiceExceptions {
        EntityTransaction et = manage.getTransaction();
        et.begin();
        Query query = manage.createNativeQuery("insert into t_Notifications"
                + " (id_Transmitter, id_receiver,is_Read, description) values(?, ?, ?, ?)");
        query.setParameter(1, notification.getId_Transmitter());
        query.setParameter(2, notification.getIdReceiver());
        query.setParameter(3, notification.getIsRead());
        query.setParameter(4, notification.getDescription());
        query.executeUpdate();
        et.commit();
        return notification;
    }
    
}
