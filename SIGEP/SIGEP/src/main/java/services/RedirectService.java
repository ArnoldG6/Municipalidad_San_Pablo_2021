package services;

import common.dao.UserDAO;
import common.model.User;
import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

@WebServlet(name = "RedirectService", urlPatterns = {"/RedirectService"})
public class RedirectService extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
        User u = UserDAO.getInstance().searchById(requestJSON.getInt("username"));
        request.getSession().setAttribute("user", u);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().flush();
        response.getWriter().close();
    }

}