package sigep.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import common.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sigep.dao.BudgetModificationRequestDAO;
import sigep.dao.SelectedBudgetDAO;
import sigep.model.BudgetModificationRequest;
import sigep.model.BudgetModificationRequestId;
import sigep.model.SelectedBudget;

@WebServlet(name = "ModificationMovementsListService", urlPatterns = {"/ModificationMovementsListService"})
public class ModificationMovementsListService extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        try {

            User currentUser = (User) request.getSession(true).getAttribute("user");

            if (!Objects.isNull(currentUser)) {

                if (User.validateSuperAdminRol(currentUser) || User.validateBudgetAdminRol(currentUser) || User.validateUserRol(currentUser)) {

                    String idModification = request.getParameter("idModification");

                    if (!Objects.isNull(idModification)) {

                        BudgetModificationRequest modification = new BudgetModificationRequest(new BudgetModificationRequestId(Integer.parseInt(idModification.substring(8, idModification.length())), new SimpleDateFormat("yyyyMMdd").parse(idModification.substring(0, 8))));

                        response.setContentType("application/json;charset=UTF-8");
                        PrintWriter out = response.getWriter();

                        BudgetModificationRequestDAO dao = new BudgetModificationRequestDAO();
                        modification = dao.searchById(modification);

                        GsonBuilder builder = new GsonBuilder();
                        builder.setPrettyPrinting();

                        Gson gson = builder.setDateFormat("dd MM yyyy").create();

                        List<SelectedBudget> budgets = SelectedBudgetDAO.getInstance().listAllByRequest(modification.getId());
                        List<SelectedBudget> moves = new ArrayList<SelectedBudget>();
                        for (SelectedBudget s : budgets) {
                            SelectedBudget sb = new SelectedBudget();
                            sb.setId(s.getId());
                            sb.setAmount(s.getAmount());
                            sb.setBudgetA(s.getBudgetA());
                            sb.setBudgetB(s.getBudgetB());
                            moves.add(sb);
                            sb = null;
                        }
                        String json = gson.toJson(moves);

                        out.println(json);

                    } else {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Debe ingresar correctamente todos los datos requeridos");
                    }

                } else {
                    response.sendRedirect("/home/sigep/common/invaliduser.jsp");
                }

            } else {
                response.sendRedirect("/home/sigep/common/nouser.jsp");
            }

        } catch (IOException ex) {

            try {
                Logger.getLogger(ModificationMovementsListService.class.getName()).log(Level.SEVERE, null, ex);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ocurrió un error al recuperar la información");
            } catch (IOException ex1) {
                Logger.getLogger(ModificationMovementsListService.class.getName()).log(Level.SEVERE, null, ex1);

            }

        } catch (ParseException ex) {
            try {
                Logger.getLogger(ModificationMovementsListService.class.getName()).log(Level.SEVERE, null, ex);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ocurrió un error al recuperar la información");
            } catch (IOException ex1) {
                Logger.getLogger(ModificationMovementsListService.class.getName()).log(Level.SEVERE, null, ex1);

            }
        }

    }

}
