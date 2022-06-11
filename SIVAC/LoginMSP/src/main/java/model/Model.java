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
   
    public HashMap<Integer, User> getUsers(){return users;}
    public void setUsers(HashMap<Integer, User> users){this.users = users;}

    public User searchUser(String username, String pwd) throws Exception{
        try{
            User u = UserDAO.getInstance().userAuth(username, pwd);
            if (u == null) 
                throw new Exception("Invalid credentials");
            return u;
        }catch(Exception e){
            throw e;
        }
    }
}
