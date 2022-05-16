/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.service;

import cr.go.sanpablo.dao.LaboralDao;
import cr.go.sanpablo.dao.LaboralDaoImpl;
import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import cr.go.sanpablo.model.LaboraInhability;
import java.sql.SQLException;
import java.util.List;


/**
 *
 * @author jegon
 */
public class LaboralServiceImpl implements LaboralService{
    private LaboralDao dao;

    @Override
    public LaboraInhability insertLaboral(LaboraInhability lic) {
        LaboraInhability saved;
        this.dao = new LaboralDaoImpl();
        saved = this.dao.insertLaboral(lic);
        return saved;
    }

    @Override
    public boolean deleteLaboral(int id) {
        this.dao = new LaboralDaoImpl();
        return this.dao.deleteLaboral(id);
    }

    @Override
    public List<LaboraInhability> listAll() throws DaoExceptions, SQLException, ServiceExceptions {
        List laboraInhability =  null;
        this.dao = new LaboralDaoImpl();
        laboraInhability = this.dao.listAll();
        return laboraInhability;
    }

    @Override
    public LaboraInhability updateLicense(LaboraInhability cgs) throws DaoExceptions, SQLException, ServiceExceptions {
        LaboraInhability updated;
        this.dao = new LaboralDaoImpl();
        updated = this.dao.updateLaboral(cgs);
        return updated;
    }
    
}
