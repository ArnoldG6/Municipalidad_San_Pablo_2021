/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.controller;

import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import cr.go.sanpablo.model.VacationDays;
import cr.go.sanpablo.service.VacationDaysService;
import cr.go.sanpablo.service.VacationDaysServiceImpl;
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
@RequestMapping(value = "/api/vacationdays")
public class VacationDaysControl {
    VacationDaysService service = new VacationDaysServiceImpl();
    
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseBody
    public VacationDays saveVacationDay(@RequestBody VacationDays days) {
        try {
            VacationDays saved = service.insertVacationDay(days);
            return saved;
        } catch (DaoExceptions | SQLException | ServiceExceptions | NullPointerException ex) {
            Logger.getLogger(MessageControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @GetMapping()
    @ResponseBody
    public List<VacationDays> allDays(){
        
        try {
            List<VacationDays> days = service.listAllDays();
            return days;
        } catch (DaoExceptions | SQLException | ServiceExceptions ex) {
            Logger.getLogger(LaboralControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @PutMapping(consumes = "application/json", produces = "application/json")
    @ResponseBody
    public VacationDays updateVacationDay(@RequestBody VacationDays day) {
        try {
            VacationDays updated = service.updateVacations(day);
            return updated;
        } catch (DaoExceptions | SQLException | ServiceExceptions ex) {
            Logger.getLogger(AdminControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @GetMapping("{id}")
    @ResponseBody
    public VacationDays searchByID(@PathVariable int id) {
        try {
            VacationDays result = null;
            result = service.searchByID(id);
            return result;
        } catch (DaoExceptions | SQLException | ServiceExceptions ex) {
            Logger.getLogger(AdminControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @DeleteMapping("{id}")
    @ResponseBody
    public boolean deleteDaysVacation(@PathVariable int id) {
        try {
            boolean isDeleted = false;
            isDeleted = service.deleteDaysVacation(id);
            return isDeleted;
        } catch (DaoExceptions | SQLException | ServiceExceptions ex) {
            Logger.getLogger(AdminControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
