/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.controller;

import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import cr.go.sanpablo.model.Vacations;
import cr.go.sanpablo.service.VacationsService;
import cr.go.sanpablo.service.VacationsServiceImpl;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author jegon
 */

@Controller
@RequestMapping(value = "/api/vacations")
public class VacationsControl {
    private VacationsService service = new VacationsServiceImpl();
    
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Vacations saveVacations(@RequestBody Vacations vac) {
        Vacations saved;
        try {
            saved = service.insertVacations(vac);
            return saved;
        } catch (DaoExceptions | SQLException | ServiceExceptions | NullPointerException ex) {
            Logger.getLogger(VacationsControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @DeleteMapping("{number}")
    @ResponseBody
    public boolean deleteVacations(@PathVariable int number) {
        boolean isDeleted = false;
        try {
            isDeleted = service.deleteVacations(number);
            return isDeleted;
        } catch (DaoExceptions | SQLException | ServiceExceptions ex) {
            Logger.getLogger(VacationsControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isDeleted;
    }
    
    @GetMapping()
    @ResponseBody
    public List<Vacations> allVacations() throws DaoExceptions, SQLException, ServiceExceptions {    
    try {
            List all = service.allVacations();
            return all;
        } catch (DaoExceptions ex) {
            Logger.getLogger(VacationsControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(VacationsControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServiceExceptions ex) {
            Logger.getLogger(VacationsControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @PutMapping(consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Vacations updateVacations(@RequestBody Vacations vac) {
        try {
            Vacations updated = service.updateVacations(vac);
            return updated;
        } catch (DaoExceptions ex) {
            Logger.getLogger(VacationsControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(VacationsControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServiceExceptions ex) {
            Logger.getLogger(VacationsControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}