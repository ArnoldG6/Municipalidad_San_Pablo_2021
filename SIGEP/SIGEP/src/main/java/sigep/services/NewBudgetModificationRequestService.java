package sigep.services;

import common.model.Official;
import common.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import sigep.dao.BudgetBalanceCertificateDAO;
import sigep.dao.BudgetDAO;
import sigep.dao.BudgetModificationRequestDAO;
import sigep.dao.SelectedBudgetDAO;
import sigep.model.Budget;
import sigep.model.BudgetBalanceCertificate;
import sigep.model.BudgetBalanceCertificateId;
import sigep.model.BudgetModificationRequest;
import sigep.model.BudgetModificationRequestId;
import sigep.model.RequestStatus;
import sigep.model.SelectedBudget;

@WebServlet(name = "NewBudgetModificationRequestService", urlPatterns = {"/NewBudgetModificationRequestService"})
@MultipartConfig()
public class NewBudgetModificationRequestService extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();

            User currentUser = (User) request.getSession(true).getAttribute("user");

            if (!Objects.isNull(currentUser)) {

                BudgetModificationRequest msp = new BudgetModificationRequest();

                Official official = currentUser.getOfficial();

                msp.setId(new BudgetModificationRequestId(new Date(System.currentTimeMillis())));
                msp.setApplicant(official);
                msp.setStatus(new RequestStatus(1, "EN REVISION"));

                List<SelectedBudget> moves = new ArrayList<>();

                int i = 1;
                while (request.getParameter("decreaseBudget" + i) != null) {
                    SelectedBudget sb = new SelectedBudget();

                    String decreaseBudgetId = request.getParameter("decreaseBudget" + i);
                    String increaseBudgetId = request.getParameter("increaseBudget" + i);

                    Budget decreaseBudget = null;
                    Budget increaseBudget = null;

                    if (!Objects.isNull(decreaseBudgetId) && decreaseBudgetId.length() > 0) {
                        String[] parts = decreaseBudgetId.split("-");
                        decreaseBudgetId = parts[1];
                        decreaseBudget = BudgetDAO.getInstance().searchById(new Budget(decreaseBudgetId));
                    }
                    if (!Objects.isNull(increaseBudgetId) && increaseBudgetId.length() > 0) {
                        String[] parts = increaseBudgetId.split("-");
                        increaseBudgetId = parts[1];
                        increaseBudget = BudgetDAO.getInstance().searchById(new Budget(increaseBudgetId));
                    }

                    String amount = request.getParameter("amount" + i);
                    double amountParsed = Double.parseDouble(amount);

                    if (!Objects.isNull(amountParsed) || (!Objects.isNull(decreaseBudgetId)
                            && decreaseBudgetId.length() > 0) || (!Objects.isNull(increaseBudgetId)
                            && increaseBudgetId.length() > 0)) {
                        
                        if (decreaseBudget.getAvailableAmount() < amountParsed) {
                            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            out.print("La partida no tiene los suficientes fondos disponibles");
                            out.flush();
                        } else {
                            sb.setBudgetA(decreaseBudget);
                            sb.setBudgetB(increaseBudget);
                            sb.setAmount(amountParsed);

                            moves.add(sb);
                        }

                    } else {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        out.print("Debe ingresar correctamente todos los datos.");
                        out.flush();
                    }

                    i++;

                }

                BudgetModificationRequestDAO.getInstance().insert(msp);

                for (SelectedBudget m : moves) {
                    BudgetModificationRequest mspDB = BudgetModificationRequestDAO.getInstance().getLastData();
                    m.setBudgetModificationRequest(mspDB);
                    SelectedBudgetDAO.getInstance().insert(m);
                }

            } else {
                response.sendRedirect("/home/sigep/common/nouser.jsp");
            }

        } catch (IOException | NumberFormatException ex) {
            try {
                out = response.getWriter();
                Logger.getLogger(NewBudgetModificationRequestService.class.getName()).log(Level.SEVERE, null, ex);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("Ocurrió un error interno, contacte al departamento de TI.");
                out.flush();
            } catch (IOException ex1) {
                Logger.getLogger(NewBudgetModificationRequestService.class.getName()).log(Level.SEVERE, null, ex1);
            } finally {
                out.close();
            }
        }
    }

}
