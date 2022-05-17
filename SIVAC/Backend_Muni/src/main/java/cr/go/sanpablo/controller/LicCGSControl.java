/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.controller;

import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import cr.go.sanpablo.model.LicenseCGS;
import cr.go.sanpablo.service.LicCGSService;
import cr.go.sanpablo.service.LicCGSServiceImpl;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author jegon
 */
@Controller
@RequestMapping(value = "/api/licCGS")
public class LicCGSControl {
    private LicCGSService serviceLic = new LicCGSServiceImpl();
    
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseBody
    public LicenseCGS saveLicense(@RequestBody LicenseCGS lic) {
        LicenseCGS saved = null;
        try {
            saved = serviceLic.insertLicences(lic);
            return saved;
        } catch (DaoExceptions | SQLException | ServiceExceptions | NullPointerException ex) {
            Logger.getLogger(LicCGSControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return saved;
    }
    
    @DeleteMapping("{id}")
    @ResponseBody
    public boolean deleteLicenseCGS(@PathVariable int id) {
        boolean isDeleted = false;
        try {
            isDeleted = serviceLic.deleteLicenseCGS(id);
            return false;
        } catch (DaoExceptions | SQLException | ServiceExceptions ex) {
            Logger.getLogger(LicCGSControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isDeleted;
    }
    
    @GetMapping()
    @ResponseBody
    public List<LicenseCGS> allLicensesSGS() {
        
        try {
            List sgs = serviceLic.listAll();
            return sgs;
        } catch (DaoExceptions ex) {
            Logger.getLogger(LicCGSControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LicCGSControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServiceExceptions ex) {
            Logger.getLogger(LicCGSControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @PutMapping(consumes = "application/json", produces = "application/json")
    @ResponseBody
    public LicenseCGS updateLicenseSGS(@RequestBody LicenseCGS cgs) {
        try {
            LicenseCGS updated = serviceLic.updateLicense(cgs);
            return updated;
        } catch (DaoExceptions ex) {
            Logger.getLogger(LicCGSControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LicCGSControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServiceExceptions ex) {
            Logger.getLogger(LicCGSControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
