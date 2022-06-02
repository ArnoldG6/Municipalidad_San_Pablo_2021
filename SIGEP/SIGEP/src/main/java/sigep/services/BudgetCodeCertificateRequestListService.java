package sigep.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import common.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
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
import sigep.model.BudgetCodeCertificateRequests;

@WebServlet(name = "BudgetCodeCertificateRequestListService", urlPatterns = {"/BudgetCodeCertificateRequestListService"})
public class BudgetCodeCertificateRequestListService extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = null;
        try {

            User currentUser = (User) request.getSession(true).getAttribute("user");

            if (!Objects.isNull(currentUser)) {

                out = response.getWriter();

                List<BudgetCodeCertificateRequests> list = new ArrayList<>();
                BudgetCodeCertificateRequestsDAO dao = new BudgetCodeCertificateRequestsDAO();

                if (User.validateSuperAdminRol(currentUser) || User.validateBudgetAdminRol(currentUser)) {

                    list = dao.listAll();

                } else {

                    list = dao.listAllByOfficial(currentUser.getIdUser());

                }

                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();

                Gson gson = builder.setDateFormat("MM-dd-yyyy").create();
                String json = gson.toJson(list);

                out.println(json);

            } else {
                response.sendRedirect("/home/sigep/common/nouser.jsp");
            }

        } catch (IOException ex) {
            try {
                out = response.getWriter();
                Logger.getLogger(BudgetCodeCertificateRequestListService.class.getName()).log(Level.SEVERE, null, ex);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("Ocurrió un error interno, contacte al departamento de TI.");
                out.flush();
            } catch (IOException ex1) {
                Logger.getLogger(BudgetCodeCertificateRequestListService.class.getName()).log(Level.SEVERE, null, ex1);
            } finally {
                out.close();
            }
        }

    }

}
