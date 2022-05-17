/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.controller;

import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import cr.go.sanpablo.model.AdminFile;
import cr.go.sanpablo.service.AdminServImpl;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cr.go.sanpablo.service.AdminService;
import java.sql.SQLException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author jegon
 */

@Controller
@RequestMapping(value = "/api/files")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class AdminControl {
    private AdminService service = new AdminServImpl();
    
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseBody
    public AdminFile insertAdmin(@RequestBody AdminFile adm) {
        try {
            AdminFile saved = service.insertAdmin(adm);
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
    public AdminFile updateAdmin(@RequestBody AdminFile adm) {
        try {
            AdminFile updated = service.updateAdmin(adm);
            return updated;
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
            isDeleted = service.deleteAdmin(id);
            return isDeleted;
        } catch (DaoExceptions | SQLException | ServiceExceptions ex) {
            Logger.getLogger(AdminControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @GetMapping()
    @ResponseBody
    public List<AdminFile> allAdminFiles() throws DaoExceptions, SQLException, ServiceExceptions {    
    try {
            List allUsers = service.allFiles();
            return allUsers;
        } catch (DaoExceptions | SQLException | ServiceExceptions ex) {
            Logger.getLogger(AdminControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @GetMapping("{id}")
    @ResponseBody
    public AdminFile searchByID(@PathVariable int id) {
        try {
            AdminFile result = null;
            result = service.searchByID(id);
            return result;
        } catch (DaoExceptions | SQLException | ServiceExceptions ex) {
            Logger.getLogger(AdminControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    

    
}