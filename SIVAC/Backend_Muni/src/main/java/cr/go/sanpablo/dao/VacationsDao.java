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
import java.util.List;

/**
 *
 * @author jegon
 */
public interface VacationsDao {
    Vacations insertVacations(Vacations vacation) throws DaoExceptions, SQLException, ServiceExceptions;
    boolean deleteVacations(int numero_solicitud) throws DaoExceptions, SQLException, ServiceExceptions;
    List<Vacations> listAllVacations() throws DaoExceptions, SQLException, ServiceExceptions;
    Vacations updateVacations(Vacations vac) throws DaoExceptions, SQLException, ServiceExceptions;
}
