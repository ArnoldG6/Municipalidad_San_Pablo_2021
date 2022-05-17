/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.controller;

import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import cr.go.sanpablo.model.LaboraInhability;
import cr.go.sanpablo.service.LaboralService;
import cr.go.sanpablo.service.LaboralServiceImpl;
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
@RequestMapping(value = "/api/inhability")
public class LaboralControl {
    private LaboralService serviceLab = new LaboralServiceImpl();
    
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseBody
    public LaboraInhability saveLaboral(@RequestBody LaboraInhability lic) {
        LaboraInhability saved = serviceLab.insertLaboral(lic);
        return saved;
    }
    
    @DeleteMapping("{id}")
    @ResponseBody
    public boolean deleteLaboral(@PathVariable int id) {
        boolean isDeleted = false;
        isDeleted = serviceLab.deleteLaboral(id);
        return isDeleted;
    }
    
    @GetMapping()
    @ResponseBody
    public List<LaboraInhability> allInhability(){
        
        try {
            List sgs = serviceLab.listAll();
            return sgs;
        } catch (DaoExceptions ex) {
            Logger.getLogger(LaboralControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LaboralControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServiceExceptions ex) {
            Logger.getLogger(LaboralControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @PutMapping(consumes = "application/json", produces = "application/json")
    @ResponseBody
    public LaboraInhability updateLaboral(@RequestBody LaboraInhability cgs) {
        try {
            LaboraInhability updated = serviceLab.updateLicense(cgs);
            return updated;
        } catch (DaoExceptions ex) {
            Logger.getLogger(LaboralControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LaboralControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServiceExceptions ex) {
            Logger.getLogger(LaboralControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
