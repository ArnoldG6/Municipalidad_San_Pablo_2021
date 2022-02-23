/**
 * @author GONCAR4
 * @author ArnoldG6 PlanServlet class is in charge of handling requests from the
 * clients in order to search or retrieve 'Plan' entries from the DB.
 */
package plan.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ex.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import sfr.dao.PlanDAO;
import sfr.dao.PlanTypeDAO;

@WebServlet(name = "PlanServlet", urlPatterns = {
    "/API/PlanServlet/Retrieve/Planes",
    "/API/PlanServlet/Retrieve/Plan",
    "/API/PlanServlet/Retrieve/Plan/RemainingRisks",
    "/API/PlanServlet/Retrieve/PlanTypes",
    "/API/PlanServlet/Search"
})
public class PlanServlet extends HttpServlet {

    /**
     * Processes requests for <code>GET</code>, <code>POST</code>
     * ,<code>OPTIONS</code>, <code>PUT</code>, <code>DELETE</code> HTTP
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        try {
            request.setCharacterEncoding("UTF-8");
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD");
            switch (request.getServletPath()) {
                case "/API/PlanServlet/Retrieve/Planes":
                    retrievePlans(request, response);
                    break;
                case "/API/PlanServlet/Retrieve/Plan":
                    searchPlanByID(request, response);
                    break;
                case "/API/PlanServlet/Retrieve/Plan/RemainingRisks":
                    retrievePlanRiskList(request, response);
                    break;
                case "/API/PlanServlet/Retrieve/PlanTypes":
                    retrievePlanTypes(request, response);
                    break;
                case "/API/PlanServlet/Search":
                    searchPlans(request, response);
                    break;
            }
        } catch (Exception ex) {
            System.err.println(ex);
            Logger.getLogger(PlanServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Plan Management methods.">

    /**
     * @param request contains the JSON data that is sent by the client and
     * other useful information from the client request.
     * @param response sends the information back to the client with the
     * server's response. searchPlans method answers to the client request with
     * a JSON formatted List<Plan> object, determined by a search in all 'Plan'
     * class attributes.
     */
    private void searchPlans(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        String responseJSON;
        JSONObject requestJSON;
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
        responseJSON = new Gson().toJson(PlanDAO.getInstance().searchInAllColumns(requestJSON.getString("searchPlan")));
        if (responseJSON == null) {
            response.getWriter().write(new InvalidPlanListIDEx().jsonify());
        } else {
            response.getWriter().write(responseJSON);
        }
        response.getWriter().flush();
        response.getWriter().close();
    }

    /**
     * @param request contains the JSON data that is sent by the client and
     * other useful information from the client request.
     * @param response sends the information back to the client with the
     * server's response. retrievePlans method answers to the client request
     * with a JSON formatted List<Plan> object, determined by a sorting
     * criteria.
     */
    private void retrievePlans(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        String responseJSON;
        JSONObject requestJSON;
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
        responseJSON = new Gson().toJson(PlanDAO.getInstance().
                listByColumn(requestJSON.getString("sortingValue"), requestJSON.getString("sortingWay")));
        response.getWriter().write(responseJSON);
        response.getWriter().flush();
        response.getWriter().close();
    }

    /**
     * @param request contains the JSON data that is sent by the client and
     * other useful information from the client request.
     * @param response sends the information back to the client with the
     * server's response. searchPlanByID method answers to the client request
     * with a JSON formatted Plan object searched by an specific ID.
     */
    private void searchPlanByID(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        String responseJSON;
        JSONObject requestJSON;
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
        responseJSON = new Gson().toJson(PlanDAO.getInstance().searchByIdString(requestJSON.getString("planID")));
        if (responseJSON == null) {
            //Custom exception
            response.getWriter().write(new PlanAlreadyExistEx().jsonify());
        } else {
            response.getWriter().write(responseJSON);
        }
        response.getWriter().flush();
        response.getWriter().close();
    }

    /**
     * @param request contains the JSON data that is sent by the client and
     * other useful information from the client request.
     * @param response sends the information back to the client with the
     * server's response. getRiskListByPlanNoRep answers to the client with a
     * List<Risk> object formatted as JSON which contains the complement of the
     * List<Risk> of the user that sent the request.
     */
    private void retrievePlanRiskList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        String responseJSON;
        JSONObject requestJSON;
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
        responseJSON = new Gson().toJson(PlanDAO.getInstance().getRiskListByPlanNoRep(requestJSON.getInt("planPkID")));
        if (responseJSON == null) {
            //Custom exception
            response.getWriter().write(new EmptyRiskListEx().jsonify());
        } else {
            response.getWriter().write(responseJSON);
        }
        response.getWriter().flush();
        response.getWriter().close();
    }

    /**
     * @param request contains the JSON data that is sent by the client and
     * other useful information from the client request.
     * @param response sends the information back to the client with the
     * server's response. retrievePlanTypes method answers to the client request
     * with a JSON formatted Map<String,List<PlanType>> object
     */
    private void retrievePlanTypes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        String responseJSON;
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson g = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        responseJSON = g.toJson(PlanTypeDAO.getInstance().listAllPlanTypeHM());
        response.getWriter().write(responseJSON);
        response.getWriter().flush();
        response.getWriter().close();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(PlanServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(PlanServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD");
            //processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(PlanServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(PlanServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(PlanServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
