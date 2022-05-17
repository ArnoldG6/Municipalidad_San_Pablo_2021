/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.service;

import cr.go.sanpablo.dao.LicSGSDao;
import cr.go.sanpablo.dao.LicSGSDaoImpl;
import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import cr.go.sanpablo.model.LicenseSGS;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author jegon
 */
public class LicSGSServiceImpl implements LicSGSService{
    private LicSGSDao dao;

    @Override
    public LicenseSGS insertLicences(LicenseSGS lic) throws DaoExceptions, SQLException, ServiceExceptions {
        LicenseSGS saved;
        this.dao = new LicSGSDaoImpl();
        saved = this.dao.insertLicences(returnLicenseSGS(lic));
        return saved;
    }

    @Override
    public boolean deleteLicenseSGS(int id) throws DaoExceptions, SQLException, ServiceExceptions {
        this.dao = new LicSGSDaoImpl();
        return this.dao.deleteLicenseSGS(id);
    }

    @Override
    public List<LicenseSGS> listAll() throws DaoExceptions, SQLException, ServiceExceptions {
        List licensesSGS;
        this.dao = new LicSGSDaoImpl();
        licensesSGS = this.dao.listAll();
        return licensesSGS;
    }

    @Override
    public LicenseSGS updateLicense(LicenseSGS sgs) throws DaoExceptions, SQLException, ServiceExceptions {
        LicenseSGS updated;
        this.dao = new LicSGSDaoImpl();
        updated = this.dao.updateLicense(sgs);
        return updated;
    }
    
    private LicenseSGS returnLicenseSGS(LicenseSGS sgs) {
        if (returnLessDate(sgs) || returnSameDate(sgs)) {
            return sgs;
        }
        return null;
    }
    
    private boolean returnLessDate(LicenseSGS sgs) {
        return sgs.getStart_Date().before(sgs.getFinal_Date());
    }
    
    private boolean returnSameDate(LicenseSGS sgs) {
        return sgs.getStart_Date().equals(sgs.getFinal_Date());
    }
    
}
