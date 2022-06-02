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
import sigep.dao.BiddingActDAO;
import sigep.dao.InitialBiddingActRequestDAO;
import sigep.model.BiddingAct;
import sigep.model.BiddingActId;
import sigep.model.InitialBiddingActRequest;
import sigep.model.InitialBiddingActRequestId;
import sigep.model.RequestStatus;

@WebServlet(name = "NewBiddingActService", urlPatterns = {"/NewBiddingActService"})
@MultipartConfig()
public class NewBiddingActService extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            User currentUser = (User) request.getSession(true).getAttribute("user");

            if (!Objects.isNull(currentUser)) {

                String ail = request.getParameter("ail");
                Part p = request.getPart("pdf");
                String fileName = p.getSubmittedFileName();

                if (!Objects.isNull(ail) && ail.length() > 0 && !fileName.isEmpty()) {

                    InitialBiddingActRequest iba = new InitialBiddingActRequest();

                    iba.setId(new InitialBiddingActRequestId(Integer.parseInt(ail.substring(8, ail.length())), new SimpleDateFormat("yyyyMMdd").parse(ail.substring(0, 8))));

                    iba = InitialBiddingActRequestDAO.getInstance().searchById(iba);

                    if (!Objects.isNull(iba)) {

                        if (!(iba.getStatus().getIdRequestStatus() == 2)) {

                            BiddingAct inrequest = new BiddingAct();

                            inrequest.setTransmitter(currentUser.getOfficial());
                            inrequest.setBeneficiary(iba.getApplicant());
                            inrequest.setInitialAct(iba);
                            inrequest.setStatus(new RequestStatus(1));
                            inrequest.setId(new BiddingActId(new Date(System.currentTimeMillis())));

                            String pattern = "yyyyMMddHHmmss"; // Change the pattern occording to your need
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                            String path = "C:\\upload\\actos-licitacion\\" + simpleDateFormat.format(inrequest.getId().getDate()) + inrequest.getInitialAct().getId().getConsecutive() + ".pdf";

                            inrequest.setDocumentPath(path);

                            p.write(path);

                            BiddingActDAO.getInstance().insert(inrequest);
                            iba.setStatus(new RequestStatus(2));
                            InitialBiddingActRequestDAO.getInstance().update(iba);

                        } else {
                            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            out.print("Ya se ha emitido un acto de litación para este acto inicial.");
                            out.flush();
                        }
                    } else {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        out.print("Debe ingresar un acto inicial válido.");
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
                Logger.getLogger(NewBiddingActService.class.getName()).log(Level.SEVERE, null, ex);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("Ocurrió un error interno, contacte al departamento de TI.");
                out.flush();
            } catch (IOException ex1) {
                Logger.getLogger(NewBiddingActService.class.getName()).log(Level.SEVERE, null, ex1);
            } finally {
                out.close();
            }
        }
    }
}
