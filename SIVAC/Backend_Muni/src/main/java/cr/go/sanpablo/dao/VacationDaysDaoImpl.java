/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.dao;

import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import cr.go.sanpablo.model.VacationDays;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author jegon
 */
public class VacationDaysDaoImpl implements VacationDaysDao {

    EntityManager manage = HibernateUtil.getSessionManager();

    @Override
    public VacationDays insertVacationDay(VacationDays vacation) throws DaoExceptions, SQLException, ServiceExceptions {
        EntityTransaction et = manage.getTransaction();
        et.begin();
        Query query = manage.createNativeQuery("insert into T_Vacation_Days"
                + " (days, status, id_day_user"
                + ") values(?, ?, ?)");
        query.setParameter(1, vacation.getDays());
        query.setParameter(2, vacation.getStatus());
        query.setParameter(3, vacation.getFile().getID());
        query.executeUpdate();
        et.commit();
        return vacation;
    }

    @Override
    public List<VacationDays> listAllDays() throws DaoExceptions, SQLException, ServiceExceptions {
        List<VacationDays> days = (List<VacationDays>) manage.createQuery("From vacationday").getResultList();
        manage.close();
        return days;
    }

    @Override
    public VacationDays updateVacations(VacationDays vac) throws DaoExceptions, SQLException, ServiceExceptions {
        EntityTransaction et = manage.getTransaction();
        et.begin();
        Query query = manage.createQuery("UPDATE vacationday SET status=" + "'" + vac.getStatus() + "'"
                + " where id_day=" + vac.getId_day());
        query.executeUpdate();
        et.commit();
        manage.close();
        return vac;
    }

    @Override
    public VacationDays searchByID(int id) throws DaoExceptions, SQLException, ServiceExceptions {
        manage.getTransaction().begin();
        VacationDays day = manage.find(VacationDays.class, id);
        manage.getTransaction().commit();
        return day;
    }

}
