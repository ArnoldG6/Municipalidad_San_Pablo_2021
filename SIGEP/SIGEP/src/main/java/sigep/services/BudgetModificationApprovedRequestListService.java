package sigep.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import common.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sigep.dao.BudgetModificationRequestDAO;
import sigep.model.BudgetModificationRequest;


@WebServlet(name = "BudgetModificationApprovedRequestListService", urlPatterns = {"/BudgetModificationApprovedRequestListService"})
public class BudgetModificationApprovedRequestListService extends HttpServlet{
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            User currentUser = (User) request.getSession(true).getAttribute("user");

            if (!Objects.isNull(currentUser)) {

                BudgetModificationRequestDAO dao = new BudgetModificationRequestDAO();
                List<BudgetModificationRequest> list = new ArrayList<>();
                 List<BudgetModificationRequest> filterlist = new ArrayList<>();
                   if (User.validateSuperAdminRol(currentUser) || User.validateBudgetAdminRol(currentUser)) {

                    list = dao.listAll();
                    
                    for(BudgetModificationRequest m: list){
                            if(m.getStatus().getIdRequestStatus() == 2){
                                filterlist.add(m);
                            }
                        }

                } else {

                    list = dao.listAllByOfficial(currentUser.getIdUser());

                }
                
                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();

                Gson gson = builder.setDateFormat("MM dd yyyy HH:mm").create();
                String json = gson.toJson(filterlist);
                        
                out.println(json);

            } else {
                response.sendRedirect("/home/sigep/common/nouser.jsp");
            }
        } catch (IOException ex) {
            try {
                Logger.getLogger(BudgetModificationRequestListService.class.getName()).log(Level.SEVERE, null, ex);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ocurrió un error al recuperar la información");
            } catch (IOException ex1) {
                Logger.getLogger(BudgetModificationRequestListService.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }

    }
    
}