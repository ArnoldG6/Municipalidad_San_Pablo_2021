/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plan.login;

import common.dao.UserDAO;
import common.model.User;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import plan.services.PlanServlet;

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
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD");
        response.setContentType("application/json");
        try {
            switch (request.getServletPath()) {
                case "/API/LoginManager/test":
                    String requestData = request.getReader().lines().collect(Collectors.joining());
                    JSONObject jsonObj = new JSONObject(requestData);
                    Integer value = jsonObj.getInt("type");
                    HttpSession session = request.getSession(true);
                    //User u;
                    switch (value) {
                        case 1:
                            //u = UserDAO.getInstance().searchById(50);
                            session.setAttribute("userID", 50);
                            session.setAttribute("userRol", "SUPER_ADMIN");
                            response.getWriter().write("{\"userID\": 50, \"userRol\": \"SUPER_ADMIN\"}");
                            response.getWriter().flush();
                            response.getWriter().close();
                            break;
                        case 2:
                            //u = UserDAO.getInstance().searchById(51);
                            //session.setAttribute("userID",u.getIdUser());
                            //session.setAttribute("userRol",u.getRoles().get(0).getDescription());
                            session.setAttribute("userID", 51);
                            session.setAttribute("userRol", "ADMIN");
                            response.getWriter().write("{\"userID\": 51, \"userRol\": \"ADMIN\"}");
                            response.getWriter().flush();
                            response.getWriter().close();
                            break;
                        case 3:
                            session.setAttribute("userID", 52);
                            session.setAttribute("userRol", "USER");
                            response.getWriter().write("{\"userID\": 52, \"userRol\": \"USER\"}");
                            response.getWriter().flush();
                            response.getWriter().close();
                            break;
                        case 4:
                            session.setAttribute("userID", 53);
                            session.setAttribute("userRol", "USER");
                            response.getWriter().write("{\"userID\": 53, \"userRol\": \"USER\"}");
                            response.getWriter().flush();
                            response.getWriter().close();
                            break;
                        default:
                            throw new IOException("Invalid parameters");
                    }
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.err.println(e.getMessage());
            throw e;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(PlanServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(PlanServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(PlanServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(PlanServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(PlanServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
