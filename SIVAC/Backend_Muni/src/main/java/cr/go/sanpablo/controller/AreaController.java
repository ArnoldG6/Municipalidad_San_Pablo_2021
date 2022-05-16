/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.controller;

import cr.go.sanpablo.dao.AreaDao;
import cr.go.sanpablo.dao.AreaDaoImpl;
import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import cr.go.sanpablo.model.AreaMuni;
import cr.go.sanpablo.service.AreaService;
import cr.go.sanpablo.service.AreaServiceImpl;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author jegon
 */

@Controller
@RequestMapping(value = "/api/areas")
public class AreaController {
    
    AreaService service = new AreaServiceImpl();
    
    @GetMapping()
    @ResponseBody
    public List<AreaMuni> allAreas() throws DaoExceptions, SQLException, ServiceExceptions {    
    try {
            List allUsers = service.listAll();
            return allUsers;
        } catch (DaoExceptions ex) {
            Logger.getLogger(AdminControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AdminControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServiceExceptions ex) {
            Logger.getLogger(AdminControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
