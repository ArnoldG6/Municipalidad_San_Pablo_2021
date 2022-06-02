package sigep.services;

import common.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import sigep.dao.BudgetDAO;
import sigep.dao.BudgetModificationRequestDAO;
import sigep.dao.BudgetModificationResolutionDAO;
import sigep.dao.SelectedBudgetDAO;
import sigep.model.BudgetModificationRequest;
import sigep.model.BudgetModificationRequestId;
import sigep.model.BudgetModificationResolution;
import sigep.model.BudgetModificationResolutionId;
import sigep.model.RequestStatus;
import sigep.model.SelectedBudget;


@WebServlet(name = "NewBudgetModificationResolutionService", urlPatterns = {"/NewBudgetModificationResolutionService"})
@MultipartConfig()
public class NewBudgetModificationResolutionService extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();

            User currentUser = (User) request.getSession(true).getAttribute("user");

            if (!Objects.isNull(currentUser)) {

                String idRequest = request.getParameter("idRequest");
                String descripcion = request.getParameter("description");
                Part d = request.getPart("doc3");

                if (!Objects.isNull(idRequest) && idRequest.length() > 0) {

                    BudgetModificationRequest msp = new BudgetModificationRequest();
                    msp.setId(new BudgetModificationRequestId(Integer.parseInt(idRequest.substring(8, idRequest.length())), new SimpleDateFormat("yyyyMMdd").parse(idRequest.substring(0, 8))));
                    msp = BudgetModificationRequestDAO.getInstance().searchById(msp);

                    if (!Objects.isNull(msp)) {

                        if ((msp.getStatus().getIdRequestStatus() == 2)) {

                            if (!(msp.getStatus().getIdRequestStatus() == 3)) {

                                BudgetModificationResolution mc = new BudgetModificationResolution();

                                mc.setBudgetModificationRequest(msp);
                                
                                mc.setDescription(descripcion);
                                mc.setId(new BudgetModificationResolutionId(new Date(System.currentTimeMillis())));

                                String pattern = "yyyyMMddHHmmss"; // Change the pattern occording to your need
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                                String path = "C:\\upload\\modificaciones-presupuestarias\\" + simpleDateFormat.format(mc.getId().getDate()) + mc.getId().getConsecutive() + ".pdf";

                                mc.setResolutionPath(path);
                                msp.setStatus(new RequestStatus(2));

                                BudgetModificationRequestDAO.getInstance().update(msp);
                                BudgetModificationResolutionDAO.getInstance().insert(mc);
                                d.write(path);

                            } else {
                                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                                out.print("No se permite emitir una resolución de una solicitud rechazada.");
                                out.flush();
                            }

                        } else {
                            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            out.print("Ya se ha emitido una resolución para esta solicitud.");
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
                Logger.getLogger(NewBudgetModificationResolutionService.class.getName()).log(Level.SEVERE, null, ex);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("Ocurrió un error interno, contacte al departamento de TI.");
                out.flush();
            } catch (IOException ex1) {
                Logger.getLogger(NewBudgetModificationResolutionService.class.getName()).log(Level.SEVERE, null, ex1);
            } finally {
                out.close();
            }
        }
    }

}
