/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.service;

import cr.go.sanpablo.dao.AdminDao;
import cr.go.sanpablo.dao.AdminDaoImpl;
import cr.go.sanpablo.model.AdminFile;
import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author jegon
 */
public class AdminServImpl implements AdminService {

    AdminDao dao;

    @Override
    public AdminFile insertAdmin(AdminFile admin) throws DaoExceptions, SQLException, ServiceExceptions{
        AdminFile saved;
        this.dao = new AdminDaoImpl();
        saved = this.dao.insertFile(admin);
        return saved;
    }

    @Override
    public AdminFile updateAdmin(AdminFile admin) throws DaoExceptions, SQLException, ServiceExceptions{
        AdminFile updated;
        this.dao = new AdminDaoImpl();
        updated = this.dao.updateFile(admin);
        return updated;
    }

    @Override
    public boolean deleteAdmin(int id) throws DaoExceptions, SQLException, ServiceExceptions{
        this.dao = new AdminDaoImpl();
        return this.dao.deleteFile(id);
    }

    @Override
    public List<AdminFile> allFiles() throws DaoExceptions, SQLException, ServiceExceptions {
        List users = null;
        dao = new AdminDaoImpl();
        users = dao.listAll();
        return users;
    }

    @Override
    public AdminFile searchByID(int id) throws DaoExceptions, SQLException, ServiceExceptions{
        AdminFile search;
        this.dao = new AdminDaoImpl();
        search = dao.searchByID(id);
        return search;
    }

    @Override
    public AdminFile updateHolidays(AdminFile admin) throws DaoExceptions, SQLException, ServiceExceptions {
        AdminFile updated;
        this.dao = new AdminDaoImpl();
        updated = this.dao.updateHolidays(admin);
        return updated;
    }
    
    @Override
    public AdminFile updateEarlyVacations(AdminFile admin) throws DaoExceptions, SQLException, ServiceExceptions{
        AdminFile updated;
        this.dao = new AdminDaoImpl();
        updated = this.dao.updateEarlyVacations(admin);
        return updated;
    }
}
