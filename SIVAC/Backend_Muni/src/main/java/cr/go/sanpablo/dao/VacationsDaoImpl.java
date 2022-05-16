/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.dao;

import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import cr.go.sanpablo.model.Vacations;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author jegon
 */
public class VacationsDaoImpl implements VacationsDao{
     //private final Session session = HibernateUtil.getSessionFactory().openSession();
    EntityManager manage = HibernateUtil.getSessionManager();

    @Override
    public List<Vacations> listAllVacations() throws DaoExceptions, SQLException, ServiceExceptions{
        List<Vacations> vaca = (List<Vacations>) manage.createQuery("From vacation").getResultList();
        manage.close();
        return vaca;
    }
    
    @Override
    public Vacations insertVacations(Vacations vacation) throws DaoExceptions, SQLException, ServiceExceptions{
        EntityTransaction et = manage.getTransaction();
        et.begin();
        Query query = manage.createNativeQuery("insert into T_Vacations"
                + " (start_date, final_date, status, id_vac"
                + ") values(?, ?, ?, ?)");
        query.setParameter(1, vacation.getStart_Date());
        query.setParameter(2, vacation.getFinal_Date());
        query.setParameter(3, vacation.getStatus());
        query.setParameter(4, vacation.getFile().getID());
        query.executeUpdate();
        et.commit();
        /*manage.getTransaction().begin();
        manage.persist(vacation);
        manage.getTransaction().commit();*/
        return vacation;
    }


    @Override
    public boolean deleteVacations(int numero_solicitud) throws DaoExceptions, SQLException, ServiceExceptions{
        manage.getTransaction().begin();
        Query query = manage.createQuery(
                "DELETE FROM vacation WHERE id_vac =" + numero_solicitud);
        int deletedCount = query.executeUpdate();
        manage.getTransaction().commit();
        return deletedCount != -1;
    }
    
   
    @Override
    public Vacations updateVacations(Vacations vac) throws DaoExceptions, SQLException, ServiceExceptions {
        EntityTransaction et = manage.getTransaction();
        et.begin();
        Query query = manage.createQuery("UPDATE vacation SET  " + "status="+"'"
                + ""+vac.getStatus()+"'"+ 
                                " where vacation_number=" + vac.getVacations_ID());
        query.executeUpdate();
        et.commit();
        manage.close();
        return vac;
    }

    private java.sql.Date parse(java.util.Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = simpleDateFormat.format(date);
        java.sql.Date date1 = java.sql.Date.valueOf(formattedDate);
        return date1;
    }


}