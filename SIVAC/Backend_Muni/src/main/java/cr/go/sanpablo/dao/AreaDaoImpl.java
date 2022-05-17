/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.dao;

import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import cr.go.sanpablo.model.AreaMuni;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author jegon
 */
public class AreaDaoImpl implements AreaDao{
    
    EntityManager manage = HibernateAreaUtil.getSessionManager();

    @Override
    public List<AreaMuni> listAll() throws DaoExceptions, SQLException, ServiceExceptions {
        List<AreaMuni> areas = (List<AreaMuni>) manage.createQuery("From area").getResultList();
        manage.close();
        return areas;
    }
   
}
