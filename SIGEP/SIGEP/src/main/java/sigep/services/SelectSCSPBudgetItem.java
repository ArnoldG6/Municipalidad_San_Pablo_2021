package sigep.services;

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
import sigep.dao.BudgetBalanceCertificateRequestDAO;
import sigep.dao.BudgetDAO;
import sigep.dao.BudgetLockDAO;
import sigep.model.Budget;
import sigep.model.BudgetBalanceCertificateRequest;
import sigep.model.BudgetBalanceCertificateRequestId;
import sigep.model.BudgetLock;

@WebServlet(name = "SelectSCSPBudgetItem", urlPatterns = {"/SelectSCSPBudgetItem"})
public class SelectSCSPBudgetItem extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;
        try {
            out = response.getWriter();
            User currentUser = (User) request.getSession(true).getAttribute("user");

            if (!Objects.isNull(currentUser)) {
                if (User.validateBudgetAdminRol(currentUser) || User.validateSuperAdminRol(currentUser)) {
                    String budget = request.getParameter("budget");
                    String scsp = request.getParameter("scsp");

                    if (!Objects.isNull(budget) && !Objects.isNull(scsp) && scsp.length() > 8) {

                        Budget budgetItem = BudgetDAO.getInstance().searchById(new Budget(budget));

                        if (!Objects.isNull(budgetItem)) {

                            BudgetBalanceCertificateRequest scspr = new BudgetBalanceCertificateRequest();
                            scspr.setId(new BudgetBalanceCertificateRequestId(Integer.parseInt(scsp.substring(8, scsp.length())), new SimpleDateFormat("yyyyMMdd").parse(scsp.substring(0, 8))));
                            scspr = BudgetBalanceCertificateRequestDAO.getInstance().searchById(scspr);
                            if (!Objects.isNull(scspr)) {
                                if (budgetItem.getAvailableAmount() >= scspr.getAmount()) {
                                    scspr.setBudgetItem(budgetItem);

                                    BudgetLock lock = BudgetLockDAO.getInstance().searchById(scspr.getLock());
                                    lock.setBudgetItem(budgetItem);

                                    List<Budget> list = new ArrayList<>(BudgetDAO.getInstance().getItems().values());
                                    if (list.isEmpty()) {
                                        BudgetDAO.getInstance().addBudgetData();
                                    }
                                    BudgetDAO.getInstance().addBlockedAmount(lock.getBudgetItem().getIdItem(), lock.getAmount());

                                    BudgetLockDAO.getInstance().update(lock);
                                    BudgetLockDAO.getInstance().addLockEvent(lock);
                                    BudgetBalanceCertificateRequestDAO.getInstance().update(scspr);

                                } else {
                                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                                    out.print("La partida seleccionada no posee fondos suficientes");
                                    out.flush();
                                }
                            } else {
                                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                                out.print("No se ha seleccionado una solicitud valida");
                                out.flush();
                            }
                        } else {
                            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            out.print("No se ha seleccionado una partida valida");
                            out.flush();
                        }
                    } else {
                        if (Objects.isNull(budget)) {
                            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            out.print("No se ha seleccionado una partida valida");
                            out.flush();
                        } else {
                            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            out.print("No se ha seleccionado una solicitud de saldo valida");
                            out.flush();
                        }
                    }
                } else {
                    response.sendRedirect("/home/sigep/common/invaliduser.jsp");
                }
            } else {
                response.sendRedirect("/home/sigep/common/nouser.jsp");
            }

        } catch (IOException ex) {
            Logger.getLogger(SelectSCSPBudgetItem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(SelectSCSPBudgetItem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
