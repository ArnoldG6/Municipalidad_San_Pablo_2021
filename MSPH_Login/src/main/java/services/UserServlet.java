/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

/**
 *
 * @author tebya
 */
import com.google.gson.Gson;
import common.dao.DepartmentDAO;
import common.dao.RolDAO;
import common.dao.UserDAO;
import common.dao.generic.Transaction;
import common.model.Department;
import common.model.Rol;
import common.model.User;
import ex.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

@WebServlet(name = "UserServlet",
        urlPatterns = {
            "/API/User",
            "/API/User/edit",
            "/API/User/add",
            "/API/Department"
        }
)
public class UserServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Exception {
        try {
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD");
            response.setContentType("application/json");
            switch (request.getServletPath()) {
                case "/API/User":
                    getUser(request, response);
                    break;
                case "/API/User/edit":
                    editUser(request, response);
                    break;
                case "/API/User/add":
                    addUser(request, response);
                    break;
                case "/API/Department":
                    getDepartments(request, response);
                    break;

                //case "/API/ExpireSession": expireSession(request,response); break;
            }
        } catch (IOException | ServletException ex) {
            System.err.println(ex);
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="User methods.">
    private void getUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            JSONObject responseJSON = new JSONObject();
            JSONObject requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
            User u = UserDAO.getInstance().searchById(requestJSON.getInt("username"));
            if (u == null) {
                throw new AuthException();
            }
            responseJSON.put("username", String.valueOf(u.getIdUser()));
            responseJSON.put("full_name", u.getOfficial().getName() + " " + u.getOfficial().getSurname());
            responseJSON.put("roles", u.getRoles().get(0).getDescription());
            responseJSON.put("email", u.getEmail());
            responseJSON.put("department", u.getOfficial().getDepartment().getDescription());
            response.getWriter().write(responseJSON.toString());

        } catch (Exception e) {
            response.sendError(500, e.getMessage());
        } finally {
            response.getWriter().flush();
            response.getWriter().close();
        }

    }

    private void editUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {

        JSONObject requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
        User user = UserDAO.getInstance().searchById(requestJSON.getInt("userID"));
        //User editUser = new Gson().fromJson(requestJSON.toString(), User.class);
        User editUser = UserDAO.getInstance().searchByEmail(requestJSON.getString("username"));
        if ((!user.hasRol("SUPER_ADMIN") && !user.hasRol("ADMIN"))) {
            throw new IOException();
        }
        if (editUser != null) {

            try {
                editUser.getOfficial().setName(requestJSON.getString("name"));
                editUser.getOfficial().setEmail(requestJSON.getString("email"));
                Department depa = DepartmentDAO.getInstance().searchById(requestJSON.getInt("department"));
                editUser.getOfficial().setDepartment(depa);
                editUser.setEmail(requestJSON.getString("email"));
                editUser.getRoles().clear();
                Rol role = RolDAO.getInstance().searchById(requestJSON.getInt("role"));
                editUser.getRoles().add(role);
                UserDAO.getInstance().update(editUser);
                UserDAO.getInstance().recordTransaction(user.getEmail(), Transaction.USER_EDITION, Boolean.TRUE, "New name: " + requestJSON.getString("name")
                        + " New email: " + requestJSON.getString("email") + " New department: " + String.valueOf(requestJSON.getInt("department")) + " New role: "
                        + String.valueOf(requestJSON.getInt("role")));
            } catch (Exception e) {
                UserDAO.getInstance().recordTransaction(user.getEmail(), Transaction.USER_EDITION, Boolean.FALSE, "New name: " + requestJSON.getString("name")
                        + " New email: " + requestJSON.getString("email") + " New department: " + String.valueOf(requestJSON.getInt("department")) + " New role: "
                        + String.valueOf(requestJSON.getInt("role")));
                throw e;
            }

        } else {
            throw new IOException("El usuario no existe.");
        }
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {

        JSONObject requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));

        if (UserDAO.getInstance().searchById(requestJSON.getInt("userID")) != null) {

            throw new IOException("El usuario ya existe.");

        } else {
            User newUser = new Gson().fromJson(requestJSON.toString(), User.class);
            StringBuilder sb = new StringBuilder();
            try {

                sb.append("Username: ").append(newUser.getIdUser());
                sb.append("Name: ").append(newUser.getOfficial().getName() + " " + newUser.getOfficial().getSurname());
                sb.append("Email: ").append(newUser.getOfficial().getEmail());
                sb.append("Roles: [");
                for (Rol r : newUser.getRoles()) {
                    sb.append(r.getIdRol() + ",");
                }
                sb.append("]");
                UserDAO.getInstance().add(newUser);
                UserDAO.getInstance().recordTransaction(requestJSON.getString("userEmail"), Transaction.USER_CREATION, Boolean.TRUE, sb.toString());
            } catch (Exception e) {
                UserDAO.getInstance().recordTransaction(requestJSON.getString("userEmail"), Transaction.USER_CREATION, Boolean.FALSE, sb.toString());
                throw e;
            }

        }

    }

    private void getDepartments(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
//pedir usaruio y ver roles
            String responseJSON;
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            responseJSON = new Gson().toJson(DepartmentDAO.getInstance().listAll());
            if (responseJSON == null) {
                throw new NullPointerException("Hubo un error cargando los departamentos.");
            }
            response.getWriter().write(responseJSON);
        } catch (Exception e) {
            response.sendError(500, e.getMessage());
        } finally {
            response.getWriter().flush();
            response.getWriter().close();
        }
    }

    //private void expireSession(HttpServletRequest request, HttpServletResponse response) 
    //throws ServletException, IOException {
    //    throw new UnsupportedOperationException("Service not implemented yet");
    //}
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods.">
    @Override
    public String getServletInfo() {
        return "User Servlet. Do not try to attack it. :)";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Userorization");
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD");
            //processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //</editor-fold>

}
