/**
 * @author GONCAR4
 * @author ArnoldG6
 * RiskServlet class is in charge of handling requests from the clients 
 * in order to search or retrieve 'Risk' entries from the DB.
 */
package risk.services;
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
import sfr.dao.RiskDAO;
@WebServlet(name = "RiskServlet", urlPatterns = {
    "/API/RiskServlet",
    "/API/RiskSearch",
    "/API/RetrieveRisks",
    "/API/RetrieveRisk"
    }
)
public class RiskServlet extends HttpServlet {
         /**
     * Processes requests for <code>GET</code>, <code>POST</code> ,<code>OPTIONS</code>,
     *   <code>PUT</code>, <code>DELETE</code> HTTP methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Exception {
        try {
            request.setCharacterEncoding("UTF-8");
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD");
            switch (request.getServletPath()) {
                case "/API/RiskServlet": listAllRisks(request,response); break;
                case "/API/RiskSearch": searchRisk(request,response); break;
                case "/API/RetrieveRisks": retrieveRisks(request,response); break;
                case "/API/RetrieveRisk": retrieveRisk(request,response); break;
            }
        } catch (Exception ex) {
            System.err.println(ex);
            Logger.getLogger(RiskServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Risk Management methods.">
     /**
     * @param request contains the JSON data that is sent by the client and other useful information from the client request.
     * @param response is the object that obtains the requested JSON data by the client.
     * listAllRisks returns a JSON formatted list of Risk objects that are sorted by default parameters.
     */
    private void listAllRisks(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String responseJSON;
        responseJSON = new Gson().toJson(RiskDAO.getInstance().listByColumn("PK_ID","DESC"));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseJSON);
        response.getWriter().flush();
        response.getWriter().close();
    }
     /**
     * @param request contains the JSON data that is sent by the client and other useful information from the client request.
     * @param response is the object that obtains the requested JSON formatted 'Risk' object.
     * searchRisk method verifies the user's identity and returns a JSON formatted 'Risk' object that matches with the search
     * data sent by the client with any of the 'Risk' class attributes.. 
     */
    private void searchRisk(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String responseJSON;
        JSONObject requestJSON;
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
        responseJSON = new Gson().toJson(RiskDAO.getInstance().searchInAllColumns(requestJSON.getString("searchRisk")));
        response.getWriter().write(responseJSON);
        response.getWriter().flush();
        response.getWriter().close();
    }
     /**
     * @param request contains the JSON data that is sent by the client and other useful information from the client request.
     * @param response is the object that obtains the requested JSON formatted 'Risk' object.
     * retrieveRisk search for an specific 'Risk' ID in order to match with a 'Risk' entry and returns it as an JSON.
     */
    private void retrieveRisk(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String responseJSON;
        JSONObject requestJSON;
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
        responseJSON = new Gson().toJson(RiskDAO.getInstance().searchById(Integer.parseInt(requestJSON.getString("riskID"))));
        response.getWriter().write(responseJSON);
        response.getWriter().flush();
        response.getWriter().close();  
    }
    /**
    * @param request contains the JSON data that is sent by the client and other useful information from the client request.
    * @param response is the object that obtains the requested JSON formatted List 'Risk object.
    * retrieveRisks returns a JSON list that contains the requested 'Risk' list defined by a sorting criteria.
    */
    private void retrieveRisks(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String responseJSON;
        JSONObject requestJSON;
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
        responseJSON = new Gson().toJson(RiskDAO.getInstance()
        .listByColumn(requestJSON.getString("sortingValue"), requestJSON.getString("sortingWay")));
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
            Logger.getLogger(RiskServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(RiskServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(RiskServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(RiskServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(RiskServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
