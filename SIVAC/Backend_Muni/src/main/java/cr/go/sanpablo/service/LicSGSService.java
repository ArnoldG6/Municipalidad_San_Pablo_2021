/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.service;

import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import cr.go.sanpablo.model.LicenseSGS;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author jegon
 */
public interface LicSGSService {
    LicenseSGS insertLicences(LicenseSGS lic) throws DaoExceptions, SQLException, ServiceExceptions;
    boolean deleteLicenseSGS(int id) throws DaoExceptions, SQLException, ServiceExceptions;
    List<LicenseSGS> listAll() throws DaoExceptions, SQLException, ServiceExceptions;
    LicenseSGS updateLicense(LicenseSGS sgs) throws DaoExceptions, SQLException, ServiceExceptions;
}
