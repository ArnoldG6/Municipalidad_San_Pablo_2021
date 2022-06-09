/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.service;

import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import cr.go.sanpablo.model.AdminFile;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author jegon
 */
public interface AdminService {

    AdminFile insertAdmin(AdminFile admin) throws DaoExceptions, SQLException, ServiceExceptions;
    
    List<AdminFile> allFiles() throws DaoExceptions, SQLException, ServiceExceptions;
    
    AdminFile updateAdmin(AdminFile admin) throws DaoExceptions, SQLException, ServiceExceptions;

    boolean deleteAdmin(int id) throws DaoExceptions, SQLException, ServiceExceptions;
    
    AdminFile searchByID(int id) throws DaoExceptions, SQLException, ServiceExceptions;
    
    AdminFile updateHolidays(AdminFile admin) throws DaoExceptions, SQLException, ServiceExceptions;
    
    AdminFile updateEarlyVacations(AdminFile admin) throws DaoExceptions, SQLException, ServiceExceptions;
}
