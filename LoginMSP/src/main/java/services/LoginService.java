/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Model;
import model.User;


public class LoginService extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String username = (String) request.getAttribute("username");
        String pwd = (String) request.getAttribute("pwd");
        try{
            User user = Model.getInstance().searchUser(username, pwd);
            request.getRequestDispatcher("").forward(request, response);
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
            response.sendRedirect("Pagina1.jsp");
        }catch(Exception ex){
            System.out.printf("%s\n", ex.getMessage());
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
