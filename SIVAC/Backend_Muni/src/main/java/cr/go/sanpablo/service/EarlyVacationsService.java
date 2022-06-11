/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.service;

import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import cr.go.sanpablo.model.EarlyVacations;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author jegon
 */
public interface EarlyVacationsService {
    EarlyVacations insertVacations(EarlyVacations early) throws DaoExceptions, SQLException, ServiceExceptions;
    List<EarlyVacations> AllEarly() throws DaoExceptions, SQLException, ServiceExceptions;
    boolean deleteEarlyVacation(int id) throws DaoExceptions, SQLException, ServiceExceptions;
}
