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
import sigep.dao.BudgetBalanceCertificateDAO;
import sigep.dao.BudgetBalanceCertificateRequestDAO;
import sigep.dao.BudgetLockDAO;
import sigep.model.BudgetBalanceCertificate;
import sigep.model.BudgetBalanceCertificateId;
import sigep.model.BudgetBalanceCertificateRequest;
import sigep.model.BudgetBalanceCertificateRequestId;
import sigep.model.BudgetLock;
import sigep.model.RequestStatus;

@WebServlet(name = "NewCSPCertificate", urlPatterns = {"/NewCSPCertificate"})
@MultipartConfig()
public class NewCSPCertificate extends HttpServlet {

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

                    BudgetBalanceCertificateRequest scsp = new BudgetBalanceCertificateRequest();
                    scsp.setId(new BudgetBalanceCertificateRequestId(Integer.parseInt(idRequest.substring(8, idRequest.length())), new SimpleDateFormat("yyyyMMdd").parse(idRequest.substring(0, 8))));
                    scsp = BudgetBalanceCertificateRequestDAO.getInstance().searchById(scsp);

                    if (!Objects.isNull(scsp)) {

                        if (!Objects.isNull(scsp.getBudgetItem())) {

                            if (!(scsp.getStatus().getIdRequestStatus() == 2)) {

                                if (!(scsp.getStatus().getIdRequestStatus() == 3)) {

                                    BudgetBalanceCertificate csp = new BudgetBalanceCertificate();
                                    csp.setTransmitter(currentUser.getOfficial());
                                    csp.setBeneficiary(scsp.getApplicant());
                                    csp.setBudgetItem(scsp.getBudgetItem());
                                    csp.setRequest(scsp);
                                    csp.setAmount(scsp.getAmount());
                                    csp.setId(new BudgetBalanceCertificateId(new Date(System.currentTimeMillis())));

                                    String pattern = "yyyyMMddHHmmss"; // Change the pattern occording to your need
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                                    String path = "C:\\upload\\certificados-saldo\\" + simpleDateFormat.format(csp.getId().getDate()) + scsp.getId().getConsecutive() + ".pdf";

                                    csp.setDocumentPath(path);
                                    scsp.setStatus(new RequestStatus(2));

                                    csp.setBudgetLock(scsp.getLock());

                                    BudgetBalanceCertificateRequestDAO.getInstance().update(scsp);
                                    BudgetBalanceCertificateDAO.getInstance().insert(csp);
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
