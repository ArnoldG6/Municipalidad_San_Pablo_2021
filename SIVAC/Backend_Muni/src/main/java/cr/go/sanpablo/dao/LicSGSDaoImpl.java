/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.dao;

import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import cr.go.sanpablo.model.LicenseSGS;
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
public class LicSGSDaoImpl implements LicSGSDao{
    EntityManager manage = HibernateUtil.getSessionManager();

    @Override
    public LicenseSGS insertLicences(LicenseSGS lic) throws DaoExceptions, SQLException, ServiceExceptions{
        EntityTransaction et = manage.getTransaction();
        et.begin();
        Query query = manage.createNativeQuery("INSERT INTO T_License_SGS(category, start_date, "
                + "final_date, justification, status, id_SGS) "
                + "values(?, ?, ?, ?, ?, ?)");
        query.setParameter(1, lic.getCategory());
        query.setParameter(2, lic.getStart_Date());
        query.setParameter(3, lic.getFinal_Date());
        query.setParameter(4, lic.getJustification());
        query.setParameter(5, lic.getStatus());
        query.setParameter(6, lic.getFile().getID());
        query.executeUpdate();
        et.commit();
        /*manage.getTransaction().begin();
        manage.persist(lic);
        manage.getTransaction().commit();*/
        return lic;
    }

    @Override
    public boolean deleteLicenseSGS(int id) throws DaoExceptions, SQLException, ServiceExceptions{
        manage.getTransaction().begin();
        Query query = manage.createQuery(
                "DELETE FROM sgs WHERE id_SGS =" + id);
        int deletedCount = query.executeUpdate();
        manage.getTransaction().commit();
        if (deletedCount != -1) {
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     * @throws cr.go.sanpablo.excepciones.DaoExceptions
     * @throws java.sql.SQLException
     * @throws cr.go.sanpablo.excepciones.ServiceExceptions
     */
    @Override
    public List<LicenseSGS> listAll() throws DaoExceptions, SQLException, ServiceExceptions{
        List<LicenseSGS> licenses = (List<LicenseSGS>)manage.createQuery("FROM sgs").getResultList();
        manage.close();
        return licenses;
    }

    @Override
    public LicenseSGS updateLicense(LicenseSGS sgs) throws DaoExceptions, SQLException, ServiceExceptions {
        EntityTransaction et = manage.getTransaction();
        et.begin();
        Query query = manage.createQuery("UPDATE sgs SET category=" +"'"+sgs.getCategory()+"'"+","
                + "start_date="+"'"+parse(sgs.getStart_Date())+"'"+", final_date="+"'"+parse(sgs.getFinal_Date())+"'"+","
                        + "justification="+"'"+sgs.getJustification()+"'"+",status="+"'"+sgs.getStatus()+"'"+ 
                                " where number_SGS=" + sgs.getNumber_License());
        query.executeUpdate();
        et.commit();
        manage.close();
        return sgs;
    }
    
    private java.sql.Date parse(java.util.Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = simpleDateFormat.format(date);
        java.sql.Date date1 = java.sql.Date.valueOf(formattedDate);
        return date1;
    }
}
