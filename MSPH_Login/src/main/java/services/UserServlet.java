/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

/**
 *
 * @author tebya
 */
import com.google.gson.Gson;
import common.dao.UserDAO;
import common.model.User;
import ex.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

@WebServlet(name = "User",
        urlPatterns = {
            "/API/User",
            "/API/User/edit",
            "/API/User/add"                       
        }
)
public class UserServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD");
        response.setContentType("application/json");
        try {
            switch (request.getServletPath()) {
                case "/API/User/":
                    getUser(request, response);
                    break;
                case "/API/User/edit": 
                    editUser(request, response); break;
                
                case "/API/User/add": break;
                
                //case "/API/ExpireSession": expireSession(request,response); break;
            }
        } catch (IOException | ServletException ex) {
            System.err.println(ex);
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="User methods.">
    private void getUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            JSONObject responseJSON = new JSONObject();
            JSONObject requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
            User u = UserDAO.getInstance().searchById(requestJSON.getInt("username"));
            if (u == null) throw new AuthException();
            responseJSON.put("username", String.valueOf(u.getIdUser()));
            responseJSON.put("full_name", u.getOfficial().getName() + " " + u.getOfficial().getSurname());
            responseJSON.put("roles", u.getRoles());
            responseJSON.put("email", u.getEmail());
            responseJSON.put("department", u.getOfficial().getDepartment().getDescription());
            responseJSON.put("token", "xd");
            response.getWriter().write(responseJSON.toString());

        } catch (AuthException e) {
            response.getWriter().write(e.jsonify());
        } finally {
            response.getWriter().flush();
            response.getWriter().close();
        }

    }
    
    private void editUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
    
        JSONObject requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
        User user = UserDAO.getInstance().searchById(requestJSON.getInt("userID"));
        
        User editUser = new Gson().fromJson(requestJSON.toString(), User.class);
        
        if ((!user.hasRol("SUPER_ADMIN") && !user.hasRol("ADMIN"))) {
            throw new IOException();
        }
        
        if(UserDAO.getInstance().searchById(requestJSON.getInt("username"))!=null){
            UserDAO.getInstance().update(editUser);
        }
        else {
            throw new IOException("El usuario no existe.");
        }
    }
    
    
    
    private void addUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        
     JSONObject requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
     
     User newUser = new Gson().fromJson(requestJSON.toString(), User.class);
     
     if(UserDAO.getInstance().searchById(requestJSON.getInt("userID")) != null){
         
         throw new IOException("El usuario ya existe.");
         
     }else UserDAO.getInstance().add(newUser);
    
    }
    

    //private void expireSession(HttpServletRequest request, HttpServletResponse response) 
    //throws ServletException, IOException {
    //    throw new UnsupportedOperationException("Service not implemented yet");
    //}
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods.">
    @Override
    public String getServletInfo() {
        return "User Servlet. Do not try to attack it. :)";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Userorization");
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD");
            //processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //</editor-fold>
   

}
