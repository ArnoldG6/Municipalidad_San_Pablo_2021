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
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author jegon
 */
public class MessageDaoImpl implements MessageDao{
    
    EntityManager manage = HibernateUtilMessage.getSessionManager();

    @Override
    public Message insertMessage(Message message) throws DaoExceptions, SQLException, ServiceExceptions {
        manage.getTransaction().begin();
        manage.merge(message);
        manage.getTransaction().commit();
        manage.close();
        return message;
    }

    @Override
    public List<Message> listAll() throws DaoExceptions, SQLException, ServiceExceptions {
        List<Message> messages = (List<Message>) manage.createQuery("From message").getResultList();
        manage.close();
        return messages;
    }
    
}
