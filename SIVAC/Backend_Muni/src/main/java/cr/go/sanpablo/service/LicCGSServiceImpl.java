/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.service;

import cr.go.sanpablo.dao.LicCGSDao;
import cr.go.sanpablo.dao.LicCGSDaoImpl;
import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import cr.go.sanpablo.model.LicenseCGS;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author jegon
 */
public class LicCGSServiceImpl implements LicCGSService{
    private LicCGSDao dao;

    @Override
    public LicenseCGS insertLicences(LicenseCGS lic) throws DaoExceptions, SQLException, ServiceExceptions{
        LicenseCGS saved;
        this.dao = new LicCGSDaoImpl();
        saved = this.dao.insertLicences(returnLicenseCGS(lic));
        return saved;
    }

    @Override
    public boolean deleteLicenseCGS(int id) throws DaoExceptions, SQLException, ServiceExceptions{
        this.dao = new LicCGSDaoImpl();
        return this.dao.deleteLicenseCGS(id);
    }

    @Override
    public List<LicenseCGS> listAll() throws DaoExceptions, SQLException, ServiceExceptions {
        List licensesCGS =  null;
        this.dao = new LicCGSDaoImpl();
        licensesCGS = this.dao.listAll();
        return licensesCGS;
    }

    @Override
    public LicenseCGS updateLicense(LicenseCGS cgs) throws DaoExceptions, SQLException, ServiceExceptions {
        LicenseCGS updated;
        this.dao = new LicCGSDaoImpl();
        updated = this.dao.updateLicense(cgs);
        return updated;
    }
    
    private LicenseCGS returnLicenseCGS(LicenseCGS cgs) {
        if (returnLessDate(cgs) || returnSameDate(cgs)) {
            return cgs;
        }
        return null;
    }
    
    private boolean returnLessDate(LicenseCGS cgs) {
        return cgs.getStart_Date().before(cgs.getFinal_Date());
    }
    
    private boolean returnSameDate(LicenseCGS cgs) {
        return cgs.getStart_Date().equals(cgs.getFinal_Date());
    }
    
}
