/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.service;

import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import cr.go.sanpablo.model.LaboraInhability;
import java.sql.SQLException;
import java.util.List;


/**
 *
 * @author jegon
 */
public interface LaboralService {
    LaboraInhability insertLaboral(LaboraInhability lic);
    boolean deleteLaboral(int id);
    List<LaboraInhability> listAll() throws DaoExceptions, SQLException, ServiceExceptions;
    LaboraInhability updateLicense(LaboraInhability cgs) throws DaoExceptions, SQLException, ServiceExceptions;
}
