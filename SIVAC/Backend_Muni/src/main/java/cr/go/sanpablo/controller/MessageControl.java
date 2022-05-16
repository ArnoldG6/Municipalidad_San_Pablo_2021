/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.controller;

import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import cr.go.sanpablo.model.Message;
import cr.go.sanpablo.service.MessageService;
import cr.go.sanpablo.service.MessageServiceImpl;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author jegon
 */

@Controller
@RequestMapping(value = "/api/messages")
public class MessageControl {
    private MessageService service = new MessageServiceImpl();
    
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Message saveMessage(@RequestBody Message message) {
        try {
            Message saved = service.insertMessage(message);
            return saved;
        } catch (DaoExceptions | SQLException | ServiceExceptions | NullPointerException ex) {
            Logger.getLogger(MessageControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @GetMapping()
    @ResponseBody
    public List<Message> allMessage(){
        
        try {
            List<Message> messages = service.listAll();
            return messages;
        } catch (DaoExceptions | SQLException | ServiceExceptions ex) {
            Logger.getLogger(LaboralControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
