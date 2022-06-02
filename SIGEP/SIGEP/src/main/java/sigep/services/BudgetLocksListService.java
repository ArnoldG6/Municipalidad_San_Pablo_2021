package sigep.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import sigep.dao.BudgetLockDAO;
import sigep.model.BudgetLock;

@WebServlet(name = "BudgetLocksListService", urlPatterns = {"/BudgetLocksListService"})
public class BudgetLocksListService extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();

            User currentUser = (User) request.getSession(true).getAttribute("user");

            if (!Objects.isNull(currentUser)) {

                if (User.validateSuperAdminRol(currentUser) || User.validateBudgetAdminRol(currentUser)) {

                    String idBudget = request.getParameter("idBudget");

                    if (!Objects.isNull(idBudget)) {

                        BudgetLockDAO dao = new BudgetLockDAO();
                        List<BudgetLock> list = dao.listAllBudget(idBudget);

                        GsonBuilder builder = new GsonBuilder();
                        builder.setPrettyPrinting();

                        Gson gson = builder.setDateFormat("MM-dd-yyyy").create();
                        String json = gson.toJson(list);

                        out.println(json);

                    } else {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        out.print("Debe ingresar una partida válida.");
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
                Logger.getLogger(BudgetLocksListService.class.getName()).log(Level.SEVERE, null, ex);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("Ocurrió un error interno, contacte al departamento de TI.");
                out.flush();
            } catch (IOException ex1) {
                Logger.getLogger(BudgetListService.class.getName()).log(Level.SEVERE, null, ex1);
            } finally {
                out.close();
            }

        }

    }

}
