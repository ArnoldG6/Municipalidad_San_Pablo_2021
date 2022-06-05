/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.dao;

import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import cr.go.sanpablo.model.Message;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author jegon
 */
public interface MessageDao {
    Message insertMessage(Message message) throws DaoExceptions, SQLException, ServiceExceptions;
    List<Message> listAll() throws DaoExceptions, SQLException, ServiceExceptions;
    Message updateMessage(Message message) throws DaoExceptions, SQLException, ServiceExceptions;
}
