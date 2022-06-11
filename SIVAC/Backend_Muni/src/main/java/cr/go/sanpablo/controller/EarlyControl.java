/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.controller;

import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import cr.go.sanpablo.model.EarlyVacations;
import cr.go.sanpablo.service.EarlyVacationsService;
import cr.go.sanpablo.service.EarlyVacationsServiceImpl;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author jegon
 */

@Controller
@RequestMapping(value = "/api/early")
public class EarlyControl {
    private EarlyVacationsService service = new EarlyVacationsServiceImpl();
    
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseBody
    public EarlyVacations saveVacations(@RequestBody EarlyVacations early) {
        EarlyVacations saved;
        try {
            saved = service.insertVacations(early);
            return saved;
        } catch (DaoExceptions | SQLException | ServiceExceptions | NullPointerException ex) {
            Logger.getLogger(VacationsControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @GetMapping()
    @ResponseBody
    public List<EarlyVacations> AllEarly() throws DaoExceptions, SQLException, ServiceExceptions {    
    try {
            List early = service.AllEarly();
            return early;
        } catch (DaoExceptions | SQLException | ServiceExceptions ex) {
            Logger.getLogger(AdminControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @DeleteMapping("{id}")
    @ResponseBody
    public boolean deleteAdmin(@PathVariable int id) {
        try {
            boolean isDeleted = false;
            isDeleted = service.deleteEarlyVacation(id);
            return isDeleted;
        } catch (DaoExceptions | SQLException | ServiceExceptions ex) {
            Logger.getLogger(AdminControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
