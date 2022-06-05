/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.service;

import cr.go.sanpablo.dao.MessageDao;
import cr.go.sanpablo.dao.MessageDaoImpl;
import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import cr.go.sanpablo.model.Message;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author jegon
 */
public class MessageServiceImpl implements MessageService{
    
    MessageDao dao;

    @Override
    public Message insertMessage(Message message) throws DaoExceptions, SQLException, ServiceExceptions {
        Message saved = null;
        this.dao = new MessageDaoImpl();
        saved = this.dao.insertMessage(message);
        return saved;
    }

    @Override
    public List<Message> listAll() throws DaoExceptions, SQLException, ServiceExceptions {
        List<Message> messages = null;
        dao = new MessageDaoImpl();
        messages = dao.listAll();
        return messages;
    }
    
    @Override
    public Message updateMessage(Message message) throws DaoExceptions, SQLException, ServiceExceptions{
        Message updated = null;
        this.dao = new MessageDaoImpl();
        updated = this.dao.updateMessage(message);
        return updated;
    }
}
