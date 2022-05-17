/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.service;

import cr.go.sanpablo.dao.AreaDao;
import cr.go.sanpablo.dao.AreaDaoImpl;
import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import cr.go.sanpablo.model.AreaMuni;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author jegon
 */
public class AreaServiceImpl implements AreaService{

    private AreaDao area;
    
    @Override
    public List<AreaMuni> listAll() throws DaoExceptions, SQLException, ServiceExceptions {
        this.area = new AreaDaoImpl();
        return this.area.listAll();
    }
    
}
