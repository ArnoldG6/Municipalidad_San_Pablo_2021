/**
 * @author ArnoldG6
 * AuthServlet class is in charge of handling requests from the clients
 * that require authorization.
 */
package services;

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

@WebServlet(name = "AuthServlet",
        urlPatterns = {
            "/API/Auth"
        }
)
public class AuthServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD");
        response.setContentType("application/json");
        try {
            switch (request.getServletPath()) {
                case "/API/Auth":
                    authUser(request, response);
                    break;
                //case "/API/ExpireSession": expireSession(request,response); break;
            }
        } catch (IOException | ServletException ex) {
            System.err.println(ex);
            Logger.getLogger(AuthServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Auth methods.">
    private void authUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONObject responseJSON = new JSONObject();
        JSONObject requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
        User u = UserDAO.getInstance().userAuth(requestJSON.getString("username"), requestJSON.getString("pwd"));
        if (u == null) {
            responseJSON.put("authStatus", false);
        } else {
            responseJSON.put("authStatus", true);
            responseJSON.put("username", String.valueOf(u.getIdUser()));
            responseJSON.put("full_name", u.getOfficial().getName() + " " + u.getOfficial().getSurname());
            responseJSON.put("roles", u.getRoles());
            responseJSON.put("token", "xd");
        }
        if(responseJSON == null){
             //Custom exception
            response.getWriter().write(new UserNotFoundEx().jsonify());
        }else{
            response.getWriter().write(responseJSON.toString());
        }
        System.out.println(responseJSON.toString());
        
        response.getWriter().flush();
        response.getWriter().close();
    }

    //private void expireSession(HttpServletRequest request, HttpServletResponse response) 
    //throws ServletException, IOException {
    //    throw new UnsupportedOperationException("Service not implemented yet");
    //}
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods.">
    @Override
    public String getServletInfo() {
        return "Auth Servlet. Do not try to attack it. :)";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AuthServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AuthServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD");
            //processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AuthServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // </editor-fold>

}