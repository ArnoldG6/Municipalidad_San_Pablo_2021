/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import common.dao.UserDAO;
import common.model.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    private void handleFriendlyRedict(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        JSONObject requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
        User u = UserDAO.getInstance().searchById(requestJSON.getInt("username"));
        request.getSession(true).setAttribute("user", u);
        //System.out.println(requestJSON.getInt("username"));       
        request.getSession().setAttribute("id", requestJSON.getInt("username"));
        request.getRequestDispatcher("moduloSivac/MainScreen.jsp").forward(request, response);
    }

            //request.getRequestDispatcher("/Pagina1.jsp").forward(request, response);


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
