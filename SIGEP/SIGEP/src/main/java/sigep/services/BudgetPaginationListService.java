package sigep.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import common.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sigep.dao.BudgetDAO;
import sigep.model.Budget;

@WebServlet(name = "BudgetPaginationListService", urlPatterns = {"/BudgetPaginationListService"})
public class BudgetPaginationListService extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();

            User currentUser = (User) request.getSession(true).getAttribute("user");

            if (!Objects.isNull(currentUser)) {

                String indexParameter = request.getParameter("index");
                String quantityParameter = request.getParameter("quantity");

                if (!Objects.isNull(indexParameter) && !Objects.isNull(quantityParameter)) {

                    if (User.validateSuperAdminRol(currentUser) || User.validateBudgetAdminRol(currentUser) || User.validateUserRol(currentUser)) {

                        int index = Integer.parseInt(indexParameter);
                        int quantity = Integer.parseInt(quantityParameter);
                        int firstPosition = index * quantity - quantity;
                        int lastPosition = index * quantity - 1;

                        List<Budget> list = new ArrayList<>(BudgetDAO.getInstance().getItems().values());
                        
                        List<Budget> subList = null;

                        if (lastPosition > list.size() - 1) {
                            subList = list.subList(firstPosition, list.size() - 1);
                        } else {
                            subList = list.subList(firstPosition, lastPosition);
                        }

                        GsonBuilder builder = new GsonBuilder();
                        builder.setPrettyPrinting();

                        Gson gson = builder.setDateFormat("MM-dd-yyyy").create();
                        String json = gson.toJson(subList);

                        out.println(json);

                    } else {
                        response.sendRedirect("/home/sigep/common/invaliduser.jsp");
                    }

                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print("La cantidad de registros y el número de página no fueron recibidos.");
                    out.flush();
                }

            } else {
                response.sendRedirect("/home/sigep/common/nouser.jsp");
            }

        } catch (IOException ex) {
            try {
                out = response.getWriter();
                Logger.getLogger(BudgetListService.class.getName()).log(Level.SEVERE, null, ex);
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
