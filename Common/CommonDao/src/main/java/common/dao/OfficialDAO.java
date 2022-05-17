/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package common.dao;

import common.dao.generic.GenericDAO;
import common.model.Official;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author jorsu
 */
public class OfficialDAO extends GenericDAO {

    private static OfficialDAO uniqueInstance;

    public static OfficialDAO getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new OfficialDAO();
        }
        return uniqueInstance;
    }

    public List<Official> listAll() {
        try {
            em = getEntityManager();
            Query query = em.createQuery("SELECT d from common.model.Official d");
            return (List<Official>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.err.println(e.getMessage());
            throw e;
        } finally {
            closeEntityManager();
        }
    }

    public Official searchById(int id) {
        em = getEntityManager();
        return (Official) em.find(Official.class, id);
    }

    public void update(Official offi) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(offi);
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
            throw ex;
        } finally {
            closeEntityManager();
        }

    }

    public void add(Official offi) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(offi);
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
            throw ex;
        } finally {
            closeEntityManager();
        }
    }

}
