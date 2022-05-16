/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.controller;

import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import cr.go.sanpablo.model.AdminFile;
import cr.go.sanpablo.model.LicenseSGS;
import cr.go.sanpablo.service.LicSGSService;
import cr.go.sanpablo.service.LicSGSServiceImpl;
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
@RequestMapping(value = "/api/licSGS")
public class LicSGSControl {
    private LicSGSService serviceLic = new LicSGSServiceImpl();
    
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseBody
    public LicenseSGS saveLicense(@RequestBody LicenseSGS lic) {
        LicenseSGS saved = null;
        try {
            saved = serviceLic.insertLicences(lic);
            return saved;
        } catch (DaoExceptions | SQLException | ServiceExceptions | NullPointerException ex) {
            Logger.getLogger(LicSGSControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return saved;
    }
    
    @DeleteMapping("{id}")
    @ResponseBody
    public boolean deleteLicenseSGS(@PathVariable int id) {
        boolean isDeleted = false;
        try {
            isDeleted = serviceLic.deleteLicenseSGS(id);
            return isDeleted;
        } catch (DaoExceptions | SQLException | ServiceExceptions ex) {
            Logger.getLogger(LicSGSControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isDeleted;
    }
    
    @GetMapping()
    @ResponseBody
    public List<LicenseSGS> allLicensesSGS() {
        try {
            List sgs = serviceLic.listAll();
            return sgs;
        } catch (DaoExceptions ex) {
            Logger.getLogger(LicSGSControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LicSGSControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServiceExceptions ex) {
            Logger.getLogger(LicSGSControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @PutMapping(consumes = "application/json", produces = "application/json")
    @ResponseBody
    public LicenseSGS updateLicenseSGS(@RequestBody LicenseSGS sgs) {
        try {
            LicenseSGS updated = serviceLic.updateLicense(sgs);
            return updated;
        } catch (DaoExceptions ex) {
            Logger.getLogger(LicSGSControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LicSGSControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServiceExceptions ex) {
            Logger.getLogger(LicSGSControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
