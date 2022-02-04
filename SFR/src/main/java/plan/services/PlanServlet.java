/**
 * @author GONCAR4
 * @author ArnoldG6
 * PlanServlet class is in charge of handling requests from the clients 
 * in order to search or retrieve 'Plan' entries from the DB.
 */
package plan.services;
import com.google.gson.Gson;
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
@WebServlet(name = "PlanServlet", urlPatterns = {
    "/API/PlanServlet",
    "/API/PlanSearch",
    "/API/RetrievePlans",
    "/API/RetrievePlan"}
)
public class PlanServlet extends HttpServlet {
    /**
    * Processes requests for <code>GET</code>, <code>POST</code> ,<code>OPTIONS</code>,
    *   <code>PUT</code>, <code>DELETE</code> HTTP methods.
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
                case "/API/PlanServlet": listAllPlans(request, response); break;
                case "/API/PlanSearch": searchPlans(request,response); break;
                case "/API/RetrievePlans": retrievePlans(request,response); break;
                case "/API/RetrievePlan": searchPlanByID(request,response);break;
            }
        } catch (Exception ex) {
            System.err.println(ex);
            Logger.getLogger(PlanServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Plan Management methods.">
     /**
     * @param request contains the JSON data that is sent by the client and other useful information from the client request.
     * @param response sends the information back to the client with the server's response.
     * listAllPlans method answers to the client request with a JSON formatted List<Plan> object.
     */
    private void listAllPlans(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException, Exception {
        String responseJSON;
        responseJSON = new Gson().toJson(PlanDAO.getInstance().listByColumn("ENTRYDATE","DESC"));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseJSON);
        response.getWriter().flush();
        response.getWriter().close();
    }
     /**
     * @param request contains the JSON data that is sent by the client and other useful information from the client request.
     * @param response sends the information back to the client with the server's response.
     * searchPlans method answers to the client request with a JSON formatted List<Plan> object,
     * determined by a search in all 'Plan' class attributes.
     */
    private void searchPlans(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, Exception {
        String responseJSON;
        JSONObject requestJSON;
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
        responseJSON = new Gson().toJson(PlanDAO.getInstance().searchInAllColumns(requestJSON.getString("searchPlan")));
        response.getWriter().write(responseJSON);
        response.getWriter().flush();
        response.getWriter().close();
    }
     /**
     * @param request contains the JSON data that is sent by the client and other useful information from the client request.
     * @param response sends the information back to the client with the server's response.
     * retrievePlans method answers to the client request with a JSON formatted List<Plan> object,
     * determined by a sorting criteria.
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
     * @param request contains the JSON data that is sent by the client and other useful information from the client request.
     * @param response sends the information back to the client with the server's response.
     * searchPlanByID method answers to the client request with a JSON formatted Plan object
     * searched by an specific ID.
     */
    private void searchPlanByID(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, Exception {
        String responseJSON;
        JSONObject requestJSON;
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
        responseJSON = new Gson().toJson(PlanDAO.getInstance().searchById(requestJSON.getString("planID")));
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
