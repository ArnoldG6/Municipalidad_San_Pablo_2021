/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.dao;

import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import cr.go.sanpablo.model.AdminFile;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author jegon
 */
public class AdminDaoImpl implements AdminDao {

    //private final Session session = HibernateUtil.getSessionFactory().openSession();
    EntityManager manage = HibernateUtil.getSessionManager();

    @Override
    public List<AdminFile> listAll() throws DaoExceptions, SQLException, ServiceExceptions{
        List<AdminFile> files = (List<AdminFile>) manage.createQuery("From file").getResultList();
        manage.close();
        return files;
    }

    @Override
    public AdminFile insertFile(AdminFile file) throws DaoExceptions, SQLException, ServiceExceptions, SQLIntegrityConstraintViolationException{
        try {
        EntityTransaction et = manage.getTransaction();
        et.begin();
        Query query = manage.createNativeQuery("insert into T_Administrative_File"
                + " (id, name, lastname_1, lastname_2, areaMuni, employment, salary,admission_Date,public_years, "
                + "email, phone, holidays, early_vacations, total_early_vacations, is_early_vacations,Role_id) "
                + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        query.setParameter(1, file.getID());
        query.setParameter(2, file.getName());
        query.setParameter(3, file.getLastname_1());
        query.setParameter(4, file.getLastname_2());
        query.setParameter(5, file.getAreaMuni());
        query.setParameter(6, file.getEmployment());
        query.setParameter(7, file.getSalary());
        query.setParameter(8, file.getAdmission_Date());
        query.setParameter(9, file.getPublic_years());
        query.setParameter(10, file.getEmail());
        query.setParameter(11, file.getPhone());
        query.setParameter(12, file.getHolidays());
        query.setParameter(13, file.getDaysEarlyVacations());
        query.setParameter(14, file.getTotalEarlyVacations());
        query.setParameter(15, file.getIsEarlyVacations());
        query.setParameter(16, file.getRole().getId_role());
        query.executeUpdate();
        et.commit();
        /*
        manage.getTransaction().begin();
        manage.persist(file);
        manage.getTransaction().commit();*/
        return file;
        }catch (RuntimeException e) {
            System.out.println("Cedula duplicada");
        }
        return null;
    }

    @Override
    public AdminFile updateFile(AdminFile file) throws DaoExceptions, SQLException, ServiceExceptions{
        EntityTransaction et = manage.getTransaction();
        et.begin();
        Query query = manage.createQuery("UPDATE file"
                + " set name = " + "'" + file.getName() + "'" + ", lastname_1 = " + "'" + file.getLastname_1() + "'"
                + ", lastname_2 = " + "'" + file.getLastname_2() + "'"  + ", areaMuni = " + "'" + file.getAreaMuni() + "'"
                + ", employment = " + "'" + file.getEmployment() + "'"
                + ", admission_Date = " + "'" + parse(file.getAdmission_Date()) + "'" + ", public_years=" + file.getPublic_years()
                + ", email = " + "'" + file.getEmail() + "'"
                + ", phone = " + file.getPhone() + ", salary=" + file.getSalary()
                + " , Role_id = " + file.getRole().getId_role() + ", is_early_vacations=" + file.getIsEarlyVacations()
                        + " where id = " + file.getID());
        query.executeUpdate();
        et.commit();
        /*
        manage.getTransaction().begin();
        manage.persist(file);
        manage.getTransaction().commit();*/
        return file;
    }

    @Override
    public boolean deleteFile(int id) throws DaoExceptions, SQLException, ServiceExceptions{
        manage.getTransaction().begin();
        Query query = manage.createQuery(
                "DELETE FROM file WHERE id =" + id);
        int deletedCount = query.executeUpdate();
        manage.getTransaction().commit();
        if (deletedCount != -1) {
            return true;
        }
        return false;
    }

    @Override
    public AdminFile searchByID(int id) throws DaoExceptions, SQLException, ServiceExceptions{
        manage.getTransaction().begin();
        AdminFile file = manage.find(AdminFile.class, id);
        manage.getTransaction().commit();
        return file;
    }
    
    private java.sql.Date parse(java.util.Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = simpleDateFormat.format(date);
        java.sql.Date date1 = java.sql.Date.valueOf(formattedDate);
        return date1;
    }
    
    @Override
    public AdminFile updateHolidays(AdminFile file) throws DaoExceptions, SQLException, ServiceExceptions{
        EntityTransaction et = manage.getTransaction();
        et.begin();
        Query query = manage.createQuery("UPDATE file set holidays="+
                file.getHolidays() + " where id=" + file.getID());
        
        query.executeUpdate();
        et.commit();
        return file;
    }
    
    @Override
    public AdminFile updateEarlyVacations(AdminFile file) throws DaoExceptions, SQLException, ServiceExceptions{
        EntityTransaction et = manage.getTransaction();
        et.begin();
        Query query = manage.createQuery("UPDATE file set early_vacations="+
                file.getDaysEarlyVacations()+ ", total_early_vacations=" + file.getTotalEarlyVacations() + " where id=" + file.getID());
        query.executeUpdate();
        et.commit();
        return file;
    }

}
