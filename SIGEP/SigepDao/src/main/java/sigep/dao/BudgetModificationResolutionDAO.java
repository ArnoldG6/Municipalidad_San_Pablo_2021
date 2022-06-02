/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sigep.dao;

import common.dao.generic.GenericDAO;
import javax.persistence.EntityManager;
import sigep.model.BudgetModificationResolution;

/**
 *
 * @author Diego Quiros
 */
public class BudgetModificationResolutionDAO extends GenericDAO{
    
    private static BudgetModificationResolutionDAO uniqueInstance;

    public static BudgetModificationResolutionDAO getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new BudgetModificationResolutionDAO();
        }
        return uniqueInstance;
    }

    public void insert(BudgetModificationResolution request) {
        try {
            EntityManager manager = emf.createEntityManager();

            manager.getTransaction().begin();
            manager.persist(request);
            manager.getTransaction().commit();

            manager.close();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
        }
    }
}
