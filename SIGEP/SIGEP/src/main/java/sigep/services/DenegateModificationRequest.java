package sigep.services;

import common.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sigep.dao.BudgetModificationRequestDAO;
import sigep.model.BudgetModificationRequest;
import sigep.model.BudgetModificationRequestId;
import sigep.model.RequestStatus;

@WebServlet(name = "DenegateModificationRequest", urlPatterns = {"/DenegateModificationRequest"})
public class DenegateModificationRequest extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = null;
        try {
            out = response.getWriter();

            User currentUser = (User) request.getSession(true).getAttribute("user");

            if (!Objects.isNull(currentUser)) {

                String secDate = request.getParameter("secDate");
                String secConsecutive = request.getParameter("secConsecutive");

                String accion = request.getParameter("action");

                Date dateId = new SimpleDateFormat("MM dd yyyy").parse(secDate);

                String consecutiveRequest = secConsecutive.substring(8, secConsecutive.length());

                int consecutiveId = Integer.parseInt(consecutiveRequest);

                BudgetModificationRequest msp = new BudgetModificationRequest();
                msp.setId(new BudgetModificationRequestId(consecutiveId, dateId));

                msp = BudgetModificationRequestDAO.getInstance().searchById(msp);

                if (accion.equals("deny")) {
                    RequestStatus estado = new RequestStatus(3, "RECHAZADA");
                    msp.setStatus(estado);
                } else {
                    RequestStatus estado = new RequestStatus(2, "APROBADA");
                    msp.setStatus(estado);
                }

                BudgetModificationRequestDAO.getInstance().update(msp);
            } else {
                response.sendRedirect("/home/sigep/common/nouser.jsp");
            }
        } catch (IOException | NumberFormatException | ParseException ex) {
            try {
                out = response.getWriter();
                Logger.getLogger(DenegateModificationRequest.class.getName()).log(Level.SEVERE, null, ex);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("Ocurrio un error interno, contacte al departamento de TI.");
                out.flush();
            } catch (IOException ex1) {
                Logger.getLogger(DenegateModificationRequest.class.getName()).log(Level.SEVERE, null, ex1);
            } finally {
                out.close();
            }
        }
    }

}
