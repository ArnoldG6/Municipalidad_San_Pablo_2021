/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import common.model.User;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LogoutService", urlPatterns = {"/LogoutService"})
public class LogoutService extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        try {
            HttpSession session = request.getSession(true);
            User user = (User) session.getAttribute("user");

            if (!Objects.isNull(user)) {

                request.getSession(true).invalidate();
                response.sendRedirect("/home");
            } else {
                response.sendRedirect("/home/sigep/common/nouser.jsp");
            }
        } catch (IOException ex) {
            Logger.getLogger(LogoutService.class.getName()).log(Level.SEVERE, null, ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

}
