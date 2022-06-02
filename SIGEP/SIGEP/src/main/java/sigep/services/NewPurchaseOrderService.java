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
import sigep.dao.PurchaseOrderDAO;
import sigep.model.BiddingAct;
import sigep.model.BiddingActId;
import sigep.model.PurchaseOrder;
import sigep.model.PurchaseOrderId;
import sigep.model.RequestStatus;

@WebServlet(name = "NewPurchaseOrderService", urlPatterns = {"/NewPurchaseOrderService"})
@MultipartConfig()
public class NewPurchaseOrderService extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            User currentUser = (User) request.getSession(true).getAttribute("user");

            if (!Objects.isNull(currentUser)) {

                String al = request.getParameter("al");
                Part p = request.getPart("pdf");
                String fileName = p.getSubmittedFileName();

                if (!Objects.isNull(al) && al.length() > 0 && !fileName.isEmpty()) {

                    BiddingAct ba = new BiddingAct();

                    ba.setId(new BiddingActId(Integer.parseInt(al.substring(8, al.length())), new SimpleDateFormat("yyyyMMdd").parse(al.substring(0, 8))));

                    ba = BiddingActDAO.getInstance().searchById(ba);

                    if (!Objects.isNull(ba)) {

                        if (!(ba.getStatus().getIdRequestStatus() == 2)) {

                            PurchaseOrder inrequest = new PurchaseOrder();

                            inrequest.setTransmitter(currentUser.getOfficial());
                            inrequest.setBiddingAct(ba);
                            inrequest.setStatus(new RequestStatus(1));
                            inrequest.setId(new PurchaseOrderId(new Date(System.currentTimeMillis())));

                            String pattern = "yyyyMMddHHmmss"; // Change the pattern occording to your need
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                            String path = "C:\\upload\\ordenes-compra\\" + simpleDateFormat.format(inrequest.getId().getDate()) + inrequest.getBiddingAct().getId().getConsecutive() + ".pdf";

                            inrequest.setDocumentPath(path);

                            p.write(path);

                            PurchaseOrderDAO.getInstance().insert(inrequest);
                            ba.setStatus(new RequestStatus(2));
                            BiddingActDAO.getInstance().update(ba);

                        } else {
                            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            out.print("Ya se ha emitido una orden de compra para este acto de licitación.");
                            out.flush();
                        }

                    } else {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        out.print("Debe ingresar un acto de licitación válido.");
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

        } catch (IOException | ParseException | ServletException ex) {
            try {
                out = response.getWriter();
                Logger.getLogger(NewPurchaseOrderService.class.getName()).log(Level.SEVERE, null, ex);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("Ocurrió un error interno, contacte al departamento de TI.");
                out.flush();
            } catch (IOException ex1) {
                Logger.getLogger(NewPurchaseOrderService.class.getName()).log(Level.SEVERE, null, ex1);
            } finally {
                out.close();
            }
        }
    }

}
