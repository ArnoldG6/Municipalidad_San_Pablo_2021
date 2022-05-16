/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.service;

import cr.go.sanpablo.model.Holidays;
import cr.go.sanpablo.dao.HolidayDao;
import cr.go.sanpablo.dao.HolidayDaoImpl;
import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author jegon
 */
public class HolidayServiceImpl implements HolidayService{
    
    HolidayDao dao ;
    @Override
    public List<Holidays> listAll() throws DaoExceptions, SQLException, ServiceExceptions {
        //return this.dao.listAll();
        List<Holidays> holi = null;
        dao = new HolidayDaoImpl();
        holi = dao.listAll();
        return holi;
    }

    @Override
    public Holidays insertHoliday(Holidays holiday) throws DaoExceptions, SQLException, ServiceExceptions {
        /*Holidays saved;
        saved = this.dao.insertHoliday(holiday);
        return saved;*/
        Holidays saved = null;
        this.dao = new HolidayDaoImpl();
        saved = this.dao.insertHoliday(holiday);
        return saved;
    }

    @Override
    public boolean deleteHoliday(int id) throws DaoExceptions, SQLException, ServiceExceptions {
        dao = new HolidayDaoImpl();
        return this.dao.deleteHoliday(id);
    }

    @Override
    public Holidays searchByID(int id) throws DaoExceptions, SQLException, ServiceExceptions {
        Holidays found;
        found = this.dao.searchByID(id);
        return found;
    }

    @Override
    public Holidays updateHoliday(Holidays holiday) throws DaoExceptions, SQLException, ServiceExceptions {
        Holidays updated;
        dao = new HolidayDaoImpl();
        updated = this.dao.updateHoliday(holiday);
        return updated;
    }
    
}
