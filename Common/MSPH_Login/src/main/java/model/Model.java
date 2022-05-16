package model;

import java.util.HashMap;
import common.dao.UserDAO;
import common.model.User;

/**
 *
 * @author arnol
 */
public class Model {
    private static Model uniqueInstance;
    HashMap<Integer, User> users;
    public static Model getInstance(){
        if (uniqueInstance == null) 
            uniqueInstance = new Model();
        return uniqueInstance;
    }
    private Model(){
        users = new HashMap<>();
    }
    private void updateModel(){ 
        try{
            users = UserDAO.getInstance().listAllHM();
            System.out.println(users);
        }catch(Exception ex){
            System.err.print(ex.getMessage());
            throw ex;
        }
    
        
    }
    public HashMap<Integer, User> getUsers(){return users;}
    public void setUsers(HashMap<Integer, User> users){this.users = users;}

    public User searchUser(String username, String pwd) throws Exception{
        User u = null;
        try{
            //this.updateModel();
            //System.out.println(users);
            Integer uname = Integer.parseInt(username);
            u = UserDAO.getInstance().idValidation(uname, pwd);
            if (u != null) return u;
        }catch(NumberFormatException e){
            u = UserDAO.getInstance().emailValidation(username, pwd);
            if (u != null) return u;
        }catch(Exception e){
            throw e;
        }
        return u;
    }
}
