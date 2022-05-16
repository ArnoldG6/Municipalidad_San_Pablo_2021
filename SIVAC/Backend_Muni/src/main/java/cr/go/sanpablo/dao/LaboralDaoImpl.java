/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.dao;

import cr.go.sanpablo.model.LaboraInhability;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author jegon
 */
public class LaboralDaoImpl implements LaboralDao{
    EntityManager manage = HibernateUtil.getSessionManager();

    @Override
    public LaboraInhability insertLaboral(LaboraInhability lic) {
       EntityTransaction et = manage.getTransaction();
        et.begin();
        Query query = manage.createNativeQuery("insert into T_Inhability"
                + " (voucher, main_Date, id_inha"
                + ") values(?, ?, ?)");
        query.setParameter(1, lic.getVoucher());
        query.setParameter(2, lic.getMainDate());
        query.setParameter(3, lic.getFile().getID());
        query.executeUpdate();
        et.commit();
        /*manage.getTransaction().begin();
        manage.persist(lic);
        manage.getTransaction().commit();*/
        return lic;
    }

    @Override
    public boolean deleteLaboral(int id) {
        manage.getTransaction().begin();
        Query query = manage.createQuery(
                "DELETE FROM Inhability WHERE id_inha =" + id);
        int deletedCount = query.executeUpdate();
        manage.getTransaction().commit();
        if (deletedCount != -1) {
            return true;
        }
        return false;
    }

    @Override
    public List listAll() {
        List<LaboraInhability> licenses = (List<LaboraInhability>)manage.createQuery("FROM Inhability").getResultList();
        manage.close();
        return licenses;
    }

    @Override
    public LaboraInhability updateLaboral(LaboraInhability cgs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
