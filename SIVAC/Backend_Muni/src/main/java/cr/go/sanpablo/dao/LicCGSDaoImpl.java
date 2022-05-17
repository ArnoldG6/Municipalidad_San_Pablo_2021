/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.dao;

import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import cr.go.sanpablo.model.LicenseCGS;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author jegon
 */
public class LicCGSDaoImpl implements LicCGSDao {

    EntityManager manage = HibernateUtil.getSessionManager();
    
    public LicCGSDaoImpl() {
        
    }

    @Override
    public LicenseCGS insertLicences(LicenseCGS lic) throws DaoExceptions, SQLException, ServiceExceptions{
        EntityTransaction et = manage.getTransaction();
        et.begin();
        Query query = manage.createNativeQuery("INSERT INTO T_License_CGS(category, start_date, "
                + "final_date, justification, status, PDF,id_CGS) "
                + "values(?, ?, ?, ?, ?, ?, ?)");
        query.setParameter(1, lic.getCategory());
        query.setParameter(2, lic.getStart_Date());
        query.setParameter(3, lic.getFinal_Date());
        query.setParameter(4, lic.getJustification());
        query.setParameter(5, lic.getStatus());
        query.setParameter(6, lic.getPDF());
        query.setParameter(7, lic.getFile().getID());
        query.executeUpdate();
        et.commit();
        manage.close();
        /*manage.getTransaction().begin();
        manage.persist(lic);
        manage.getTransaction().commit();*/
        return lic;
    }

    @Override
    public boolean deleteLicenseCGS(int id) throws DaoExceptions, SQLException, ServiceExceptions{
        manage.getTransaction().begin();
        Query query = manage.createQuery(
                "DELETE FROM cgs WHERE id_CGS =" + id);
        int deletedCount = query.executeUpdate();
        manage.getTransaction().commit();
        if (deletedCount != -1) {
            manage.close();
            return true;
        }
        return false;
    }

    @Override
    public List<LicenseCGS> listAll() throws DaoExceptions, SQLException, ServiceExceptions {
        List<LicenseCGS> licenses = (List<LicenseCGS>)manage.createQuery("FROM cgs").getResultList();
        manage.close();
        return licenses;
    }

    @Override
    public LicenseCGS updateLicense(LicenseCGS cgs) throws DaoExceptions, SQLException, ServiceExceptions {
        EntityTransaction et = manage.getTransaction();
        et.begin();
        Query query = manage.createQuery("UPDATE cgs SET category=" +"'"+cgs.getCategory()+"'"+","
                + "start_date="+"'"+parse(cgs.getStart_Date())+"'"+", final_date="+"'"+parse(cgs.getFinal_Date())+"'"+","
                        + "justification="+"'"+cgs.getJustification()+"'"+",status="+"'"+cgs.getStatus()+"'"+ 
                                " where number_CGS=" + cgs.getNumber_License());
        query.executeUpdate();
        et.commit();
        manage.close();
        return cgs;
    }
    
    private java.sql.Date parse(java.util.Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = simpleDateFormat.format(date);
        java.sql.Date date1 = java.sql.Date.valueOf(formattedDate);
        return date1;
    }

}
