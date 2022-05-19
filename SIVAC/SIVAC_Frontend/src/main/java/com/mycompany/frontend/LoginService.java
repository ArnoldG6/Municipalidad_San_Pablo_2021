/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.frontend;

import common.dao.UserDAO;
import common.model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;
import org.json.JSONObject;

public class LoginService extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD");
            handleFriendlyRedict(request, response);

        } catch (Exception ex) {
            System.err.printf("%s\n", ex.getMessage());
        }

    }

    private void handleFriendlyRedict(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
        User u = UserDAO.getInstance().searchById(requestJSON.getInt("username"));
        System.out.println(requestJSON.getInt("username"));
        if (u == null) {
            throw new IOException("User not found");
        }
        request.getSession().setAttribute("user", u);
        response.sendRedirect("moduloSivac/MainScreen.jsp");
        System.out.println(request.getAttribute("userID"));
        System.out.println(u.toString());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().flush();
        response.getWriter().close();
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
