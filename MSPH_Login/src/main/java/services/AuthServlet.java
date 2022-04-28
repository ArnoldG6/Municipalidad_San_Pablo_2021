/**
 * @author ArnoldG6
 * AuthServlet class is in charge of handling requests from the clients
 * that require authorization.
 */
package services;

import common.dao.UserDAO;
import common.model.User;
import ex.*;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jakarta.mail.MessagingException;
import org.json.JSONObject;

@WebServlet(name = "AuthServlet",
        urlPatterns = {
            "/API/Auth",
            "/API/PasswordReset",
            "/API/ValidateResetCode"
        }
)
public class AuthServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD");
        response.setContentType("application/json");
        try {
            switch (request.getServletPath()) {
                case "/API/Auth":
                    authUser(request, response);
                    break;
                case "/API/PasswordReset":
                    passwordReset(request, response);
                    break;
                case "/API/ValidateResetCode":
                    validateCode(request, response);
                    break;

                //case "/API/ExpireSession": expireSession(request,response); break;
            }
        } catch (IOException | ServletException ex) {
            System.err.println(ex);
            Logger.getLogger(AuthServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Auth methods.">
    private void authUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            JSONObject responseJSON = new JSONObject();
            JSONObject requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
            User u = UserDAO.getInstance().userAuth(requestJSON.getString("username"), requestJSON.getString("pwd"));
            if (u == null) {
                throw new AuthException();
            }
            responseJSON.put("authStatus", true);
            responseJSON.put("username", String.valueOf(u.getIdUser()));
            responseJSON.put("full_name", u.getOfficial().getName() + " " + u.getOfficial().getSurname());
            responseJSON.put("roles", u.getRoles());
            responseJSON.put("token", "xd");
            response.getWriter().write(responseJSON.toString());
        } catch (AuthException e) {
            response.getWriter().write(e.jsonify());
        } finally {
            response.getWriter().flush();
            response.getWriter().close();
        }
    }

    private void passwordReset(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            JSONObject requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
            String email = requestJSON.getString("userEmail");
            User user = UserDAO.getInstance().searchByEmail(email);

            if (user == null) {
                throw new NoSuchElementException("No se encontró un usuario con el correo indicado.");
            }

            SecureRandom rnd = new SecureRandom();

            int max = 999999999;
            int min = 100000000;

            Integer code = rnd.nextInt(max - min + 1) + min;

            UserDAO.getInstance().handlePasswordReset(user, code);

        } catch (MessagingException e) {
            response.sendError(404, "Hubo un error enviando el correo solicitado.");
        } catch (NoSuchElementException e) {
            response.sendError(400, "No se encontró un usuario con el correo indicado.");
        } catch (Exception e) {
            response.sendError(500, "Hubo un error desconocido.");
        } finally {
            response.getWriter().flush();
            response.getWriter().close();
        }
    }

        private void validateCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            JSONObject requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
            String userCode = requestJSON.getString("validationCode");
            String newPassword = requestJSON.getString("newPassword");
            User user = UserDAO.getInstance().searchByEmail(requestJSON.getString("userEmail"));

            if (user == null) {
                throw new NoSuchElementException("No se encontró un usuario con el correo indicado.");
            }
            
            if (!userCode.equals(UserDAO.getInstance().getPasswordCode(user))) {
                throw new IllegalArgumentException("El código ingresado por el usuario es incorrecto.");
            }
            
            UserDAO.getInstance().changePassword(user, newPassword);

        } catch (IllegalArgumentException e) {
            response.sendError(401, "El codigo ingresado por el usuario es incorrecto.");
        } catch (NoSuchElementException e) {
            response.sendError(400, "No se encontró un usuario con el correo indicado.");
        } catch (Exception e) {
            response.sendError(500, "Hubo un error desconocido.");
        } finally {
            response.getWriter().flush();
            response.getWriter().close();
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods.">
    @Override
    public String getServletInfo() {
        return "Auth Servlet. Do not try to attack it. :)";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AuthServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AuthServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD");
            //processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AuthServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // </editor-fold>

}
