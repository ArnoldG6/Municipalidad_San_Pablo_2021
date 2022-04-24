/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sfr.dao;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author Luis D
 */
public class EmailFactory {
    private final String sender = "asd@gmail.com";
    private Properties props;
    
    public EmailFactory() {
        this.props = new Properties();
        props.put("mail.smtp.auth", "true");
    }
    
}
