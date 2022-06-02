package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import common.dao.UserDAO;
import common.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UserListService", urlPatterns = {"/UserListService"})
public class UserListService extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();

            User currentUser = (User) request.getSession(true).getAttribute("user");

            if (!Objects.isNull(currentUser)) {

                if (User.validateSuperAdminRol(currentUser)) {

                    UserDAO userDAO = UserDAO.getInstance();

                    GsonBuilder builder = new GsonBuilder();
                    builder.setPrettyPrinting();

                    Gson gson = builder.create();
                    List<User> users = userDAO.listAll();

                    String json = gson.toJson(users);
                    out.println(json);

                } else {
                    response.sendRedirect("/home/sigep/common/invaliduser.jsp");
                }

            } else {
                response.sendRedirect("/home/sigep/common/nouser.jsp");
            }
        } catch (IOException ex) {
            try {
                out = response.getWriter();
                Logger.getLogger(UserListService.class.getName()).log(Level.SEVERE, null, ex);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("Ocurrió un error interno, contacte al departamento de TI.");
                out.flush();
            } catch (IOException ex1) {
                Logger.getLogger(UserListService.class.getName()).log(Level.SEVERE, null, ex1);
            } finally {
                out.close();
            }
        }

    }

}
