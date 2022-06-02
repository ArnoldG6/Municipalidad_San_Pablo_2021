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
import sigep.dao.BudgetBalanceCertificateDAO;
import sigep.dao.BudgetDAO;
import sigep.dao.BudgetLockDAO;
import sigep.dao.PurchaseOrderDAO;
import sigep.model.Budget;
import sigep.model.BudgetBalanceCertificate;
import sigep.model.BudgetLock;
import sigep.model.PurchaseOrder;
import sigep.model.PurchaseOrderId;
import sigep.model.RequestStatus;

@WebServlet(name = "DecreasePurchaseOrderAmount", urlPatterns = {"/DecreasePurchaseOrderAmount"})
public class DecreasePurchaseOrderAmount extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;
        try {
            out = response.getWriter();

            User currentUser = (User) request.getSession(true).getAttribute("user");

            if (!Objects.isNull(currentUser)) {
                if (User.validateSuperAdminRol(currentUser) || User.validateTenderAdminRol(currentUser)) {
                    String idPurchaseOrder = request.getParameter("id-po");

                    if (!Objects.isNull(idPurchaseOrder)) {
                        PurchaseOrderDAO dao = new PurchaseOrderDAO();

                        PurchaseOrder purchaseOrder = new PurchaseOrder();
                        purchaseOrder.setId(new PurchaseOrderId(Integer.parseInt(idPurchaseOrder.substring(8, idPurchaseOrder.length())), new SimpleDateFormat("yyyyMMdd").parse(idPurchaseOrder.substring(0, 8))));
                        purchaseOrder = dao.findById(purchaseOrder);

                        if (!Objects.isNull(purchaseOrder)) {

                            BudgetBalanceCertificate csp = purchaseOrder.getBiddingAct().getInitialAct().getCertificate();

                            if (!Objects.isNull(csp.getBudgetLock())) {
                                Budget budget = csp.getBudgetItem();
                                BudgetLock lock = csp.getBudgetLock();

                                budget = BudgetDAO.getInstance().searchById(budget);
                                lock = BudgetLockDAO.getInstance().searchById(lock);
                                csp = BudgetBalanceCertificateDAO.getInstance().searchById(csp);

                                budget.setTenders(budget.getTenders() + csp.getAmount());
                                budget.setBlockedAmount(budget.getBlockedAmount() - csp.getAmount());

                                List<Budget> list = new ArrayList<>(BudgetDAO.getInstance().getItems().values());
                                if (list.isEmpty()) {
                                    BudgetDAO.getInstance().addBudgetData();
                                }

                                BudgetDAO.getInstance().getItems().get(budget.getIdItem()).setTenders(budget.getTenders() + csp.getAmount());
                                BudgetDAO.getInstance().getItems().get(budget.getIdItem()).setBlockedAmount(budget.getBlockedAmount() - csp.getAmount());

                                csp.setBudgetLock(null);

                                BudgetBalanceCertificateDAO.getInstance().update(csp);
                                BudgetDAO.getInstance().update(budget);
                                BudgetLockDAO.getInstance().removeLock(lock);
                                purchaseOrder.setStatus(new RequestStatus(2));

                                PurchaseOrderDAO.getInstance().update(purchaseOrder);

                            } else {
                                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                                out.print("Ya se ha rebajado el monto ha esta orden de compra");
                                out.flush();
                            }

                        } else {
                            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            out.print("No se encontro la orden de compra");
                            out.flush();
                        }
                    } else {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        out.println("No se ha recibido el id de la órden de compra");
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
                Logger.getLogger(DecreasePurchaseOrderAmount.class.getName()).log(Level.SEVERE, null, ex);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("Ocurrió un error interno, contacte al departamento de TI.");
                out.flush();
            } catch (IOException ex1) {
                Logger.getLogger(DecreasePurchaseOrderAmount.class.getName()).log(Level.SEVERE, null, ex1);
            } finally {
                out.close();
            }
        } catch (ParseException ex) {
            Logger.getLogger(DecreasePurchaseOrderAmount.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
