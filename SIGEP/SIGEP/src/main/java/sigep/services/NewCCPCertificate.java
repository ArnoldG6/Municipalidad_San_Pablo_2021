package sigep.services;

import common.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import sigep.dao.BudgetCodeCertificateDAO;
import sigep.dao.BudgetCodeCertificateRequestsDAO;
import sigep.dao.BudgetLockDAO;
import sigep.model.BudgetCodeCertificate;
import sigep.model.BudgetCodeCertificateId;
import sigep.model.BudgetCodeCertificateRequests;
import sigep.model.BudgetCodeCertificateRequestsId;
import sigep.model.BudgetLock;
import sigep.model.RequestStatus;

@WebServlet(name = "NewCCPCertificate", urlPatterns = {"/NewCCPCertificate"})
@MultipartConfig()
public class NewCCPCertificate extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();

            User currentUser = (User) request.getSession(true).getAttribute("user");

            if (!Objects.isNull(currentUser)) {

                String idRequest = request.getParameter("idRequest");
                Part d = request.getPart("doc3");

                if (!Objects.isNull(idRequest) && idRequest.length() > 0) {

                    BudgetCodeCertificateRequests sccp = new BudgetCodeCertificateRequests();
                    sccp.setId(new BudgetCodeCertificateRequestsId(Integer.parseInt(idRequest.substring(8, idRequest.length())), new SimpleDateFormat("yyyyMMdd").parse(idRequest.substring(0, 8))));
                    sccp = BudgetCodeCertificateRequestsDAO.getInstance().searchById(sccp);

                    if (!Objects.isNull(sccp)) {

                        if (!Objects.isNull(sccp.getBudget())) {

                            if (!(sccp.getStatus().getIdRequestStatus() == 2)) {

                                if (!(sccp.getStatus().getIdRequestStatus() == 3)) {

                                    BudgetCodeCertificate ccp = new BudgetCodeCertificate();
                                    ccp.setTransmitter(currentUser.getOfficial());
                                    ccp.setBeneficiary(sccp.getApplicant());
                                    ccp.setBudgetItem(sccp.getBudget());
                                    ccp.setRequest(sccp);
                                    ccp.setAmount(sccp.getAmount());
                                    ccp.setId(new BudgetCodeCertificateId(new Date(System.currentTimeMillis())));

                                    String pattern = "yyyyMMddHHmmss"; // Change the pattern occording to your need
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                                    String path = "C:\\upload\\certificados-codigo\\" + simpleDateFormat.format(ccp.getId().getDate()) + sccp.getId().getConsecutive() + ".pdf";

                                    ccp.setDocumentPath(path);
                                    sccp.setStatus(new RequestStatus(2));

                                    ccp.setBudgetLock(sccp.getLock());

                                    BudgetCodeCertificateRequestsDAO.getInstance().update(sccp);
                                    BudgetCodeCertificateDAO.getInstance().insert(ccp);
                                    d.write(path);

                                } else {
                                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                                    out.print("No se permite emitir un certificado de una solicitud rechazada.");
                                    out.flush();
                                }

                            } else {
                                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                                out.print("Ya se ha emitido un certificado para esta solicitud.");
                                out.flush();
                            }
                        } else {
                            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            out.print("La solicitud no esta relacionada a una partida presupuestaria.");
                            out.flush();
                        }

                    } else {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        out.print("Debe seleccionar una solicitud válida.");
                        out.flush();
                    }

                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print("Debe seleccionar una solicitud.");
                    out.flush();
                }

            } else {
                response.sendRedirect("/home/sigep/common/nouser.jsp");
            }

        } catch (IOException | ParseException | ServletException ex) {
            try {
                out = response.getWriter();
                Logger.getLogger(NewCSPCertificate.class.getName()).log(Level.SEVERE, null, ex);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("Ocurrió un error interno, contacte al departamento de TI.");
                out.flush();
            } catch (IOException ex1) {
                Logger.getLogger(NewCSPCertificate.class.getName()).log(Level.SEVERE, null, ex1);
            } finally {
                out.close();
            }
        }

    }

}
