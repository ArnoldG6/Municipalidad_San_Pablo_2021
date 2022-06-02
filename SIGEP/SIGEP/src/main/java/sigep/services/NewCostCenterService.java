package sigep.services;

import common.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import sigep.dao.BudgetDAO;
import sigep.model.Budget;

@WebServlet(name = "NewCostCenterService", urlPatterns = {"/NewCostCenterService"})
public class NewCostCenterService extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;
        try {
            out = response.getWriter();
            User currentUser = (User) request.getSession(true).getAttribute("user");

            if (!Objects.isNull(currentUser)) {

                if (User.validateSuperAdminRol(currentUser) || User.validateBudgetAdminRol(currentUser)) {

                    String[] codigo = request.getParameterValues("codigo");
                    String[] description = request.getParameterValues("nombre");
                    String amountString = request.getParameter("amount");
                    String id = "";
                    String parent = "";

                    if (!Objects.isNull(codigo) && codigo.length > 0 && !Objects.isNull(description) && description.length > 0) {
                        for (int i = 0; i < codigo.length; i++) {
                            if (i < codigo.length - 1) {
                                id += codigo[i] + ".";
                            } else {
                                parent = id;
                                id += codigo[i];
                            }
                        }

                        Budget budget = new Budget();
                        budget.setIdItem(id);
                        if (codigo.length > 1) {
                            budget.setFather(BudgetDAO.getInstance().searchById(new Budget(parent)));
                        } else {
                            budget.setFather(null);
                        }

                        budget.setDescription(description[description.length - 1]);
                        budget.setOrdinaryAmount(0);
                        budget.setAvailableAmount(0);
                        budget.setExtraordinaryAmount(0);
                        budget.setBlockedAmount(0);
                        budget.setPettyCash(0);
                        budget.setTenders(0);
                        budget.setIncreased(0);
                        budget.setDecreased(0);

                        if (!Objects.isNull(amountString)) {
                            budget.setOrdinaryAmount(Double.parseDouble(amountString));
                            budget.setAvailableAmount(Double.parseDouble(amountString));
                        } else {
                            budget.setOrdinaryAmount(0);
                            budget.setAvailableAmount(0);
                        }

                        BudgetDAO budgetDao = new BudgetDAO();

                        List<Budget> list = new ArrayList<>(BudgetDAO.getInstance().getItems().values());
                        if (!list.isEmpty()) {
                            BudgetDAO.getInstance().getItems().put(budget.getIdItem(), budget);
                            BudgetDAO.getInstance().addBudgetAmount(BudgetDAO.getInstance().getItems().get(budget.getIdItem()));
                        }

                        budgetDao.insert(budget);

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
                Logger.getLogger(NewCostCenterService.class.getName()).log(Level.SEVERE, null, ex);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("Ocurrió un error interno, contacte al departamento de TI.");
                out.flush();
            } catch (IOException ex1) {
                Logger.getLogger(NewCostCenterService.class.getName()).log(Level.SEVERE, null, ex1);
            } finally {
                out.close();
            }
        }

    }

}
