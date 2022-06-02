package sigep.services;

import common.model.Official;
import common.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sigep.dao.BudgetBalanceCertificateRequestDAO;
import sigep.dao.BudgetDAO;
import sigep.dao.BudgetLockDAO;
import sigep.model.Budget;
import sigep.model.BudgetBalanceCertificateRequest;
import sigep.model.BudgetBalanceCertificateRequestId;
import sigep.model.BudgetLock;
import sigep.model.RequestStatus;

@WebServlet(name = "NewBudgetBalanceCertificateRequestService", urlPatterns = {"/NewBudgetBalanceCertificateRequestService"})
public class NewBudgetBalanceCertificateRequestService extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;
        try {
            out = response.getWriter();

            User currentUser = (User) request.getSession(true).getAttribute("user");

            if (!Objects.isNull(currentUser)) {

                String budgetItemId = request.getParameter("budget");
                String description = request.getParameter("description");
                String amount = request.getParameter("amount");

                if (!Objects.isNull(amount) && ((!Objects.isNull(description) && description.length() > 0) || (!Objects.isNull(budgetItemId) && budgetItemId.length() > 0))) {

                    double amountParsed = Double.parseDouble(amount);
                    Official official = currentUser.getOfficial();

                    BudgetBalanceCertificateRequest scsp = new BudgetBalanceCertificateRequest();

                    scsp.setId(new BudgetBalanceCertificateRequestId(new Date(System.currentTimeMillis())));
                    scsp.setAmount(amountParsed);
                    scsp.setApplicant(official);
                    scsp.setDescription(description);
                    scsp.setStatus(new RequestStatus(1));
                    scsp.setBudgetItem(null);
                    
                    BudgetLock lock = new BudgetLock();

                    Calendar c = Calendar.getInstance();
                    c.setTime(scsp.getId().getDate());
                    c.add(Calendar.DATE, 15);

                    lock.setAmount(amountParsed);
                    lock.setApplicant(official);
                    lock.setUsed(false);
                    lock.setInitialDate(scsp.getId().getDate());
                    lock.setEndDate(c.getTime());

                    if (!Objects.isNull(budgetItemId) && budgetItemId.length() > 0) {
                        String[] parts = budgetItemId.split("-");

                        if (parts.length == 2) {

                            budgetItemId = parts[1];
                            Budget budget = BudgetDAO.getInstance().searchById(new Budget(budgetItemId));
                            scsp.setBudgetItem(budget);

                            if (Objects.isNull(budget)) {
                                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                                out.print("Debe ingresar una partida válida.");
                                out.flush();
                            } else {

                                if (budget.getAvailableAmount() < amountParsed) {
                                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                                    out.print("La partida no contiene presupuesto suficiente.");
                                    out.flush();
                                } else {
                                    lock.setBudgetItem(scsp.getBudgetItem());
                                    BudgetLockDAO.getInstance().addWithId(lock);
                                    scsp.setLock(lock);
                                    BudgetBalanceCertificateRequestDAO.getInstance().insert(scsp);
                                }
                            }

                        } else {
                            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            out.print("Selecciona una opcion válida.");
                            out.flush();
                        }
                    } else {
                        BudgetLockDAO.getInstance().addWithIdNoEvent(lock);
                        scsp.setLock(lock);
                        BudgetBalanceCertificateRequestDAO.getInstance().insert(scsp);
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print("Debe ingresar correctamente todos los datos.");
                    out.flush();
                }

            } else {
                response.sendRedirect("/home/sigep/common/nouser.jsp");
            }

        } catch (Exception ex) {
            try {
                out = response.getWriter();
                Logger.getLogger(NewBudgetBalanceCertificateRequestService.class.getName()).log(Level.SEVERE, null, ex);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("Ocurrió un error interno, contacte al departamento de TI.");
                out.flush();
            } catch (IOException ex1) {
                Logger.getLogger(NewBudgetBalanceCertificateRequestService.class.getName()).log(Level.SEVERE, null, ex1);
            } finally {
                out.close();
            }
        }

    }

}
