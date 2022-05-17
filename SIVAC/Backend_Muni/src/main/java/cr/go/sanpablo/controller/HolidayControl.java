/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.controller;

import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import cr.go.sanpablo.service.HolidayService;
import cr.go.sanpablo.service.HolidayServiceImpl;
import cr.go.sanpablo.model.Holidays;
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
@RequestMapping(value = "/api/holiday")
public class HolidayControl {
    private HolidayService service = new HolidayServiceImpl();
    
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Holidays insertHoliday(@RequestBody Holidays holiday) {
        try {
            Holidays saved = service.insertHoliday(holiday);
            return saved;
        } catch (DaoExceptions  | ServiceExceptions  ex) {
            System.out.println("Datos duplicados u error en la base de datos");
        } catch (SQLException e) {
            Logger.getLogger(AdminControl.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
    
    @PutMapping(consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Holidays updateHoliday(@RequestBody Holidays holiday) {
        try {
            Holidays updated = service.updateHoliday(holiday);
            return updated;
        } catch (DaoExceptions | SQLException | ServiceExceptions ex) {
            Logger.getLogger(AdminControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @DeleteMapping("{id}")
    @ResponseBody
    public boolean deleteHoliday(@PathVariable int id) {
        try {
            boolean isDeleted = false;
            isDeleted = service.deleteHoliday(id);
            return isDeleted;
        } catch (DaoExceptions | SQLException | ServiceExceptions ex) {
            Logger.getLogger(AdminControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @GetMapping()
    @ResponseBody
    public List<Holidays> allAdminHolidays() throws DaoExceptions, SQLException, ServiceExceptions {    
    try {
            List allHolidays = service.listAll();
            return allHolidays;
        } catch (DaoExceptions | SQLException | ServiceExceptions ex) {
            Logger.getLogger(AdminControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @GetMapping("{id}")
    @ResponseBody
    public Holidays searchByID(@PathVariable int id) {
        try {
            Holidays result = null;
            result = service.searchByID(id);
            return result;
        } catch (DaoExceptions | SQLException | ServiceExceptions ex) {
            Logger.getLogger(AdminControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
