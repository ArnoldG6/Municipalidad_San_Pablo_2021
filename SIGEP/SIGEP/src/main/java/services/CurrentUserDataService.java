/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import common.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CurrentUserDataService", urlPatterns = {"/CurrentUserDataService"})
public class CurrentUserDataService extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;
        try {
            out = response.getWriter();
            User currentUser = (User) request.getSession(true).getAttribute("user");

            if (!Objects.isNull(currentUser)) {

                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();

                Gson gson = builder.create();

                String json = gson.toJson(currentUser);
                out.println(json);

            } else {
                response.sendRedirect("/home/sigep/common/nouser.jsp");
            }
        } catch (IOException ex) {
            try {
                out = response.getWriter();
                Logger.getLogger(CurrentUserDataService.class.getName()).log(Level.SEVERE, null, ex);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("Ocurrió un error interno, contacte al departamento de TI.");
                out.flush();
            } catch (IOException ex1) {
                Logger.getLogger(CurrentUserDataService.class.getName()).log(Level.SEVERE, null, ex1);
            } finally {
                out.close();
            }
        }
    }

}
