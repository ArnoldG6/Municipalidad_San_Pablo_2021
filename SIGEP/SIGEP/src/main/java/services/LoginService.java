/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import common.dao.UserDAO;
import java.io.IOException;
import common.model.User;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoginService", urlPatterns = {"/LoginService"})
public class LoginService extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            //could be username or email
            String username = (String)request.getParameter("username");
            String pwd = (String) request.getParameter("pwd");
            UserDAO userDAO = new UserDAO();
            User user = userDAO.searchById(Integer.parseInt(username));
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
            request.getRequestDispatcher("/Pagina1.jsp").forward(request, response);
        } catch (Exception ex) {
         Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
            System.err.printf("%s\n", ex.getMessage());
            response.sendRedirect("index.jsp");
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
