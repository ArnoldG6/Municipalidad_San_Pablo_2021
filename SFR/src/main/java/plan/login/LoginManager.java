/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plan.login;

import common.dao.UserDAO;
import common.model.User;
import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

/**
 *
 * @author Arnold
 */
@WebServlet(name = "LoginManager", urlPatterns = {"/API/LoginManager/test"})
public class LoginManager extends HttpServlet {
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        response.setHeader("Access-Control-Allow-Headers", "application/json, Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
        response.addHeader("Access-Control-Max-Age", "86400");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        try {
            switch (request.getServletPath()) {
                case "/API/LoginManager/test":
                    String requestData = request.getReader().lines().collect(Collectors.joining());
                    JSONObject jsonObj = new JSONObject(requestData);
                    String value = jsonObj.getString("type");
                    HttpSession session = request.getSession(true);
                    User u;
                    switch(Integer.parseInt(value)){
                        case 1: 
                            u = UserDAO.getInstance().searchById(50);
                            session.setAttribute("userID",u.getIdUser());
                            session.setAttribute("userRol",u.getRoles().get(0).getDescription());
                            break; 
                        case 2: 
                            u = UserDAO.getInstance().searchById(51);
                            session.setAttribute("userID",u.getIdUser());
                            session.setAttribute("userRol",u.getRoles().get(0).getDescription());
                            break;
                        case 3: 
                            u = UserDAO.getInstance().searchById(52);
                            session.setAttribute("userID",u.getIdUser());
                            session.setAttribute("userRol",u.getRoles().get(0).getDescription());
                            break;
                        case 4: 
                            u = UserDAO.getInstance().searchById(53);
                            session.setAttribute("userID",u.getIdUser());
                            session.setAttribute("userRol",u.getRoles().get(0).getDescription());
                            break;
                        default: throw new IOException("Invalid parameters");
                    }
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.err.println(e.getMessage());
            throw e;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
