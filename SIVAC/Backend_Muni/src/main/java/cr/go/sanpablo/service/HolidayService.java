/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.service;

import cr.go.sanpablo.model.Holidays;
import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author jegon
 */
public interface HolidayService {
    
    List<Holidays> listAll() throws DaoExceptions, SQLException, ServiceExceptions;
    Holidays insertHoliday(Holidays holiday) throws DaoExceptions, SQLException, ServiceExceptions;
    boolean deleteHoliday(int id) throws DaoExceptions, SQLException, ServiceExceptions;
    Holidays searchByID(int id) throws DaoExceptions, SQLException, ServiceExceptions;
    Holidays updateHoliday(Holidays holiday) throws DaoExceptions, SQLException, ServiceExceptions;
}
