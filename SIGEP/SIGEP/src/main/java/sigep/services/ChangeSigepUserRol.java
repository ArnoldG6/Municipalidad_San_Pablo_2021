package sigep.services;

import common.dao.RolDAO;
import common.dao.UserDAO;
import common.model.Rol;
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

@WebServlet(name = "ChangeSigepUserRol", urlPatterns = {"/ChangeSigepUserRol"})
public class ChangeSigepUserRol extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;
        try {
            out = response.getWriter();

            User currentUser = (User) request.getSession(true).getAttribute("user");

            if (!Objects.isNull(currentUser)) {

                if (User.validateSuperAdminRol(currentUser)) {

                    String idUser = request.getParameter("id-user");
                    String idRol = request.getParameter("id-rol");

                    if (!Objects.isNull(idUser) && !Objects.isNull(idRol)) {

                        User user = UserDAO.getInstance().searchById(Integer.parseInt(idUser));
                        Rol rol = RolDAO.getInstance().searchById(new Rol(Integer.parseInt(idRol)));
                        if (!Objects.isNull(rol)) {
                            if (User.validateRol(user, rol)) {
                                User.removeRol(user, rol);
                            } else {
                                user.getRoles().add(rol);
                            }

                            UserDAO.getInstance().update(user);

                        } else {
                            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            out.print("Rol no encontrado.");
                            out.flush();
                        }

                    } else {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        out.print("Debe ingresar correctamente todos los datos.");
                        out.flush();
                    }

                } else {
                    response.sendRedirect("/home/sigep/common/invaliduser.jsp");
                }

            } else {
                response.sendRedirect("/home/sigep/common/nouser.jsp");
            }

        } catch (IOException ex) {

            try {
                out = response.getWriter();
                Logger.getLogger(InitialBiddingActDocumentService.class.getName()).log(Level.SEVERE, null, ex);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("Ocurrió un error interno, contacte al departamento de TI.");
                out.flush();
            } catch (IOException ex1) {
                Logger.getLogger(InitialBiddingActDocumentService.class.getName()).log(Level.SEVERE, null, ex1);
            } finally {
                out.close();
            }

        }

    }

}
