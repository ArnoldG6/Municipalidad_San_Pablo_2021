package services;

import common.dao.RolDAO;
import common.dao.UserDAO;
import common.model.Rol;
import common.model.User;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AddUserRolService", urlPatterns = {"/AddUserRolService"})
public class AddUserRolService extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User currentUser = (User) request.getSession(true).getAttribute("user");

        if (!Objects.isNull(currentUser)) {

            // if (User.validateSuperAdminRol(currentUser)) {
            String idUser = request.getParameter("idUser");
            String idRol = request.getParameter("rolUser");

            if (!Objects.isNull(idUser) && !Objects.isNull(idRol)) {

                UserDAO userDAO = new UserDAO();
                RolDAO rolDAO = RolDAO.getInstance();

                int id = Integer.parseInt(idUser);
                User user = userDAO.searchById(Integer.parseInt(idUser));
                user.getRoles().add(rolDAO.searchById(new Rol(Integer.parseInt(idRol))));

                userDAO.update(user);

                response.sendRedirect("/home/sigep/budget/indexBudget.jsp");
                //request.getRequestDispatcher("/users.jsp").forward(request, response);

            } else {
                request.getRequestDispatcher("/invalidData.jsp").forward(request, response);
            }

            /*} else {
                request.getRequestDispatcher("/invalidRol.jsp").forward(request, response);
            }*/
        } else {
            request.getRequestDispatcher("/noUserError.jsp").forward(request, response);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
