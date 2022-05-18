/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author jegon
 */
public class HibernateAreaUtil {
    private static EntityManager manager;
    private static EntityManagerFactory manFact;
    private static final String ARCHIVO_PERSISTENCIA = "persistenceArea";
    
    private static EntityManagerFactory getManager() {
        if (manFact == null) {
            manFact = Persistence.createEntityManagerFactory(ARCHIVO_PERSISTENCIA);
        }
        return manFact;
    }
    
    public static EntityManager getSessionManager() {
        //manFact = Persistence.createEntityManagerFactory(ARCHIVO_PERSISTENCIA);
        if (getManager() != null) {
          manager = getManager().createEntityManager();
        }
        //manager = manFact.createEntityManager();
        return manager;
    }
}