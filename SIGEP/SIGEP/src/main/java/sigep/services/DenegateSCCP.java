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
import sigep.dao.BudgetCodeCertificateRequestsDAO;
import sigep.dao.BudgetDAO;
import sigep.dao.BudgetLockDAO;
import sigep.model.Budget;
import sigep.model.BudgetCodeCertificateRequests;
import sigep.model.BudgetCodeCertificateRequestsId;
import sigep.model.BudgetLock;

@WebServlet(name = "DenegateSCCP", urlPatterns = {"/DenegateSCCP"})
public class DenegateSCCP extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;
        try {
            out = response.getWriter();
            User currentUser = (User) request.getSession(true).getAttribute("user");

            if (!Objects.isNull(currentUser)) {

                if (User.validateSuperAdminRol(currentUser) || User.validateBudgetAdminRol(currentUser)) {

                    String idRequest = request.getParameter("sccp");
                    String reason = request.getParameter("reason");

                    if (!Objects.isNull(idRequest) && !Objects.isNull(reason) && !(idRequest.length() <= 8)) {
                        BudgetCodeCertificateRequests sccp = new BudgetCodeCertificateRequests();
                        sccp.setId(new BudgetCodeCertificateRequestsId(Integer.parseInt(idRequest.substring(8, idRequest.length())), new SimpleDateFormat("yyyyMMdd").parse(idRequest.substring(0, 8))));
                        sccp = BudgetCodeCertificateRequestsDAO.getInstance().searchById(sccp);

                        if (!Objects.isNull(sccp)) {

                            if (!(sccp.getStatus().getIdRequestStatus() == 2) && !(sccp.getStatus().getIdRequestStatus() == 3)) {
                                List<Budget> list = new ArrayList<>(BudgetDAO.getInstance().getItems().values());
                                if (list.isEmpty()) {
                                    BudgetDAO.getInstance().addBudgetData();
                                }
                                sccp.getStatus().setIdRequestStatus(3);
                                sccp.setRefusalReason(reason);
                                BudgetLock lock = BudgetLockDAO.getInstance().searchById(sccp.getLock());
                                sccp.setLock(null);
                                BudgetCodeCertificateRequestsDAO.getInstance().update(sccp);
                                BudgetLockDAO.getInstance().removeLock(lock);
                            } else {
                                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                                out.print("No es posible denegar una solicitud aprobada o previamente denegada");
                                out.flush();
                            }

                        } else {
                            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            out.print("Solicitud no encontrada");
                            out.flush();
                        }
                    } else {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        out.print("Debe ingresar correctamente todos los datos");
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
                Logger.getLogger(DenegateSCSP.class.getName()).log(Level.SEVERE, null, ex);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("Ocurrió un error interno, contacte al departamento de TI.");
                out.flush();
            } catch (IOException ex1) {
                Logger.getLogger(DenegateSCSP.class.getName()).log(Level.SEVERE, null, ex1);
            } finally {
                out.close();
            }
        } catch (ParseException ex) {
            Logger.getLogger(DenegateSCSP.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
