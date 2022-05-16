package sfr.dao;

import java.io.IOException;
import java.util.List;
import javax.persistence.Query;
import sfr.model.Comment;

/**
 *
 * @author GONCAR
 */
public class CommentDAO extends GenericDAO {
    
    private static CommentDAO uniqueInstance; //Singleton Pattern Object

    /**
     * @return the Singleton Pattern Object of CommentDAO class.
     */
    public static CommentDAO getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new CommentDAO();
        }
        return uniqueInstance;
        
    }
    
    public String translateColumnName(String column, String order) throws IOException {
        order = order.toUpperCase();
        if (!(order.equals("ASC") || order.equals("DESC"))) {
            throw new IOException("Invalid order parameter");
        }
        switch (column.toUpperCase()) {
            case "PK_ID":
                return "pkID";
            case "COMMENT":
                return "comment";
            case "URL":
                return "url";
            case "AUTHOR":
                return "author";
            case "ENTRYDATE":
                return "entryDate";
            default:
                throw new IOException("Invalid column");
        }
    }
    
    /**
     *
     * @return a Plan object that matches with
     * @param id
     */
    public Comment searchById(int id) {
        em = getEntityManager();
        return (Comment) em.find(Comment.class, id);
    }
    
    /**
     * @return a sorted list of Comment objects depending on the next
     * parameters:
     * @param column specifies the sorting criteria from the selected column.
     * Default value for this project is: "ENTRYDATE".
     * @param order specifies the 'ascendent' or 'descendent' sorting criteria.
     * Default value for this project is: "DESC".
     * @throws java.lang.Exception
     */
    public List<Comment> listByColumn(String column, String order) throws Exception {
        try {
            order = order.toUpperCase();
            column = this.translateColumnName(column, order);
            String cmd = new StringBuilder().append("SELECT p from Comment p order by p.")
                    .append(column).append(" ").append(order).toString();
            em = getEntityManager();
            Query query = em.createQuery(cmd);
            return (List<Comment>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.err.println(e.getMessage());
            throw e;
        }
    }
    
    /**
     * This method adds a Comment object into the DB record, using HQL.
     *
     * @param comment is the Comment Object to be added.
     */
    public void add(Comment comment) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(comment);
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
        } finally {
            closeEntityManager();
        }
    }
    
    /**
     * This method updates a Comment object into the DB record, using HQL.
     *
     * @param comment is the Comment Object to be updated.
     */
    public void update(Comment comment) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(comment);
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
        } finally {
            closeEntityManager();
        }
    }
    
    /**
     * This method deletes a Comment object into the DB record, using HQL.
     *
     * @param comment is the Comment Object to be deleted.
     */
    public void delete(Comment comment) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.remove(em.merge(comment));
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
        } finally {
            closeEntityManager();
        }
    }
    
}
