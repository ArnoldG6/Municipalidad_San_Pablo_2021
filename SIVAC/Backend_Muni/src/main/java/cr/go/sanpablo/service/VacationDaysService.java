/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.service;

import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import cr.go.sanpablo.model.VacationDays;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author jegon
 */
public interface VacationDaysService {
    
    VacationDays insertVacationDay(VacationDays vacation) throws DaoExceptions, SQLException, ServiceExceptions;
    List<VacationDays> listAllDays() throws DaoExceptions, SQLException, ServiceExceptions;
    VacationDays updateVacations(VacationDays vac) throws DaoExceptions, SQLException, ServiceExceptions;
    VacationDays searchByID(int id) throws DaoExceptions, SQLException, ServiceExceptions;
    boolean deleteDaysVacation(int id) throws DaoExceptions, SQLException, ServiceExceptions;
    
}
