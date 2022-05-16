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
import common.dao.EmailFactory;
import common.dao.OfficialDAO;
import common.dao.RolDAO;
import common.dao.UserDAO;
import common.model.Department;
import common.model.Official;
import common.model.Rol;
import common.model.User;
import ex.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
            "/API/Department",
            "/API/Users"
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
                case "/API/Users":
                    getUsers(request, response);
                    break;

                //case "/API/ExpireSession": expireSession(request,response); break;
            }
        } catch (IOException | ServletException ex) {
            System.err.println(ex);
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    private void getUsers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String responseJSON;
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            responseJSON = new Gson().toJson(UserDAO.getInstance().listAll());
            if (responseJSON == null) {
                throw new NullPointerException("Hubo un error cargando los usuarios.");
            }
            response.getWriter().write(responseJSON);
        } catch (Exception e) {
            response.sendError(500, e.getMessage());
        } finally {
            response.getWriter().flush();
            response.getWriter().close();
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
            responseJSON.put("name", u.getOfficial().getName());
            responseJSON.put("surname", u.getOfficial().getSurname());
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
        System.out.print(requestJSON.getInt("usuarioLogeado"));
        User user = UserDAO.getInstance().searchById(requestJSON.getInt("usuarioLogeado"));
        User editUser = UserDAO.getInstance().searchByEmail(requestJSON.getString("emailOriginal"));
        if ((!user.hasRol("SUPER_ADMIN") && !user.hasRol("ADMIN") && !(user.getIdUser() == editUser.getIdUser()))) {
            throw new IOException();
        }
        if (editUser != null) {
            Official offi = editUser.getOfficial();
            offi.setName(requestJSON.getString("name"));
            offi.setSurname(requestJSON.getString("surname"));
            String rolLog = user.getRoles().get(0).getDescription();
            if (rolLog.equals("SUPER_ADMIN")) {
                Department depa = DepartmentDAO.getInstance().searchById(requestJSON.getInt("department"));
                editUser.getOfficial().setDepartment(depa);
            }
            OfficialDAO.getInstance().update(offi);
            if (rolLog.equals("SUPER_ADMIN")) {
                editUser.getRoles().clear();
                Rol role = RolDAO.getInstance().searchById(requestJSON.getInt("role"));
                editUser.getRoles().add(role);
            }
            try {
                UserDAO.getInstance().update(editUser);
                UserDAO.getInstance().recordTransaction(user.getIdUser().toString(), common.dao.generic.Transaction.USER_EDITION, Boolean.TRUE, "User edited:"
                        + String.valueOf(editUser.getIdUser()));
            } catch (Exception e) {
                UserDAO.getInstance().recordTransaction(user.getIdUser().toString(), common.dao.generic.Transaction.USER_EDITION, Boolean.FALSE, "User edited:"
                        + String.valueOf(editUser.getIdUser()));
                throw e;
            }

        } else {
            throw new IOException("El usuario no existe.");
        }
    }


    private void addUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {

        JSONObject requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
        int username = requestJSON.getInt("username");

        if (UserDAO.getInstance().searchById(username) != null) {
            throw new IOException("El usuario ya existe.");
        } else {
            String email = requestJSON.getString("email");
            try {
                if (UserDAO.getInstance().searchByEmail(email) != null) {
                    throw new IOException("El email ya existe.");
                }
            } catch (Exception e) {
            }
            Department depa = DepartmentDAO.getInstance().searchById(requestJSON.getInt("department"));
            Official newOffi = new Official(username, requestJSON.getString("name"), requestJSON.getString("surname"), email, depa);
            OfficialDAO.getInstance().add(newOffi);

            Rol rol = RolDAO.getInstance().searchById(requestJSON.getInt("role"));
            List<Rol> roles = new ArrayList();
            roles.add(rol);

            String password = requestJSON.getString("password");
            User newUser = new User(username, newOffi, email, password, roles);
            UserDAO.getInstance().add(newUser, password);

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
                OfficialDAO.getInstance().add(newOffi);
                UserDAO.getInstance().add(newUser, password);
                UserDAO.getInstance().recordTransaction(requestJSON.getString("userEmail"), common.dao.generic.Transaction.USER_CREATION, Boolean.TRUE, sb.toString());
                EmailFactory.getInstance().sendAddUser(newUser);
            } catch (Exception e) {
                UserDAO.getInstance().recordTransaction(requestJSON.getString("userEmail"), common.dao.generic.Transaction.USER_CREATION, Boolean.FALSE, sb.toString());
                throw e;
            }

        }

    }

    private void getDepartments(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
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
            Logger.getLogger(UserServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(UserServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
    //</editor-fold>

}
