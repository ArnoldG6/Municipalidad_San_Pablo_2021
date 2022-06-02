package sigep.services;

import common.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import sigep.dao.InitialBiddingActRequestDAO;
import sigep.model.BudgetBalanceCertificate;
import sigep.model.BudgetBalanceCertificateId;
import sigep.model.InitialBiddingActRequest;
import sigep.model.InitialBiddingActRequestId;
import sigep.model.RequestStatus;

@WebServlet(name = "NewInitialBiddingActRequest", urlPatterns = {"/NewInitialBiddingActRequest"})
@MultipartConfig()
public class NewInitialBiddingActRequest extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();

            User currentUser = (User) request.getSession(true).getAttribute("user");

            if (!Objects.isNull(currentUser)) {

                String csp = request.getParameter("csp");
                Part p = request.getPart("doc2");
                String fileName = p.getSubmittedFileName();

                if (!Objects.isNull(csp) && csp.length() > 0 && !fileName.isEmpty()) {

                    BudgetBalanceCertificate certificate = new BudgetBalanceCertificate();

                    certificate.setId(new BudgetBalanceCertificateId(Integer.parseInt(csp.substring(8, csp.length())), new SimpleDateFormat("yyyyMMdd").parse(csp.substring(0, 8))));

                    certificate = BudgetBalanceCertificateDAO.getInstance().searchById(certificate);

                    if (!Objects.isNull(certificate)) {

                        InitialBiddingActRequest inrequest = new InitialBiddingActRequest();

                        inrequest.setApplicant(currentUser.getOfficial());
                        inrequest.setCertificate(certificate);
                        inrequest.setStatus(new RequestStatus(1));
                        inrequest.setId(new InitialBiddingActRequestId(new Date(System.currentTimeMillis())));

                        String pattern = "yyyyMMddHHmmss"; // Change the pattern occording to your need
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                        String path = "C:\\upload\\actos-iniciales-licitacion\\" + simpleDateFormat.format(inrequest.getId().getDate()) + inrequest.getCertificate().getId().getConsecutive() + ".pdf";

                        inrequest.setDocumentPath(path);

                        p.write(path);

                        InitialBiddingActRequestDAO.getInstance().insert(inrequest);
                    } else {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        out.print("Debe ingresar un certificado válido.");
                        out.flush();
                    }

                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print("Debe ingresar correctamente todos los datos.");
                    out.flush();
                }

            } else {
                response.sendRedirect("/home/sigep/common/nouser.jsp");
            }

        } catch (IOException | NumberFormatException | ParseException | ServletException ex) {
            try {
                out = response.getWriter();
                Logger.getLogger(NewInitialBiddingActRequest.class.getName()).log(Level.SEVERE, null, ex);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("Ocurrio un error interno, contacte al departamento de TI.");
                out.flush();
            } catch (IOException ex1) {
                Logger.getLogger(NewInitialBiddingActRequest.class.getName()).log(Level.SEVERE, null, ex1);
            } finally {
                out.close();
            }
        }
    }

}
