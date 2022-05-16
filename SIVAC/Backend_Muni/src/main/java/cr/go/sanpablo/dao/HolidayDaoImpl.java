/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.dao;

import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import cr.go.sanpablo.model.Holidays;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author jegon
 */
public class HolidayDaoImpl implements HolidayDao {

    EntityManager manage = HibernateHolidays.getSessionManager();

    @Override
    public List<Holidays> listAll() throws DaoExceptions, SQLException, ServiceExceptions {
        List<Holidays> holidays = (List<Holidays>) manage.createQuery("From holiday").getResultList();
        manage.close();
        return holidays;
    }

    @Override
    public Holidays insertHoliday(Holidays holiday) throws DaoExceptions, SQLException, ServiceExceptions {

        manage.getTransaction().begin();
        manage.merge(holiday);
        manage.getTransaction().commit();
        manage.close();
        return holiday;
    }

    @Override
    public boolean deleteHoliday(int id) throws DaoExceptions, SQLException, ServiceExceptions {
        manage.getTransaction().begin();
        Query query = manage.createQuery(
                "DELETE FROM holiday WHERE id_Holidays =" + id);
        int deletedCount = query.executeUpdate();
        manage.getTransaction().commit();
        if (deletedCount != -1) {
            return true;
        }
        return false;
    }

    @Override
    public Holidays searchByID(int id) throws DaoExceptions, SQLException, ServiceExceptions {
        manage.getTransaction().begin();
        Holidays holiday = manage.find(Holidays.class, id);
        manage.getTransaction().commit();
        return holiday;
    }

    @Override
    public Holidays updateHoliday(Holidays holiday) throws DaoExceptions, SQLException, ServiceExceptions {
        EntityTransaction et = manage.getTransaction();
        et.begin();
        Query query = manage.createQuery("UPDATE holiday SET day=" + holiday.getDay() + ", name="  + "'" + holiday.getName() + "'"
        + ", month=" + holiday.getMonth() + " where id_holidays=" + holiday.getIdHolidays());
        query.executeUpdate();
        et.commit();
        manage.close();
        return holiday;
    }

}
