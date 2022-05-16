/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.excepciones;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author jegon
 */
public class ServiceExceptions extends Exception{
    private static final Logger logger = LogManager.getLogger(ServiceExceptions.class);
    
    public ServiceExceptions(String message) {
        super(message);
        logger.error("Error importante - Capa Service");
    }

    public ServiceExceptions(String message, Throwable cause) {
        super(message, cause);
        logger.error("Error importante - Capa Service", cause);
    }
}
