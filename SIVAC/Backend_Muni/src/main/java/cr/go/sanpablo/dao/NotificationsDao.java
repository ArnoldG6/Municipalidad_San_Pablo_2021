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

/**
 *
 * @author jegon
 */
public interface NotificationsDao {
    Notifications addNotification(Notifications notification) throws DaoExceptions, SQLException, ServiceExceptions;
    
}
