/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.dao;

import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import cr.go.sanpablo.model.EarlyVacations;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author jegon
 */
public class EarlyVacationsDaoImpl implements EarlyVacationsDao{
    EntityManager manage = HibernateUtil.getSessionManager();

    @Override
    public EarlyVacations insertVacations(EarlyVacations early) throws DaoExceptions, SQLException, ServiceExceptions {
        EntityTransaction et = manage.getTransaction();
        et.begin();
        Query query = manage.createNativeQuery("insert into T_Early_Vacations"
                + " (start_date, final_date, T_Administrative_File_id"
                + ") values(?, ?, ?)");
        query.setParameter(1, early.getStart_Date());
        query.setParameter(2, early.getFinal_Date());
        query.setParameter(3, early.getFile().getID());
        query.executeUpdate();
        et.commit();
        return early;
    }
    
}
