/**
 * @author GONCAR4
 * @author ArnoldG6
 * RiskManager class is in charge of handling requests from the clients 
 * in order to create, delete or edit 'Risk' entries in the DB.
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
import plan.services.PlanServlet;
import sfr.dao.RiskDAO;
import sfr.model.Risk;
@WebServlet(name = "RiskManager", urlPatterns = {
    "/API/RiskManager/insert", 
    "/API/RiskManager/delete", 
    "/API/RiskManager/edit"
    }
)
public class RiskManager extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Exception {
        try {
            request.setCharacterEncoding("UTF-8");
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD");
            switch (request.getServletPath()) {
                case "/API/RiskManager/insert": insertRisk(request); break;
                case "/API/RiskManager/delete": deleteRisk(request); break;
                case "/API/RiskManager/edit": editRisk(request); break;
            }
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(request.getServletPath());
        } catch (Exception e) {
            System.err.println(e.toString());
            throw e;
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Risk Management methods.">
     /**
     * @param request contains the JSON data that is sent by the client and other useful information from the client request.
     * deleteRisk method verifies the user's credentials and verifies that the Risk ID that wants to be deleted corresponds to an existing Risk.
     */
    private void deleteRisk(HttpServletRequest request) throws IOException, Exception{
                    String obj = request.getReader().lines().collect(Collectors.joining());
                    Gson json = new Gson();
                    Risk newR = json.fromJson(obj, Risk.class);
                    Risk riskE = RiskDAO.getInstance().searchById(newR.getId());
                    if (riskE == null)  throw new IOException();
                    RiskDAO.getInstance().delete(riskE);
    }
     /**
     * @param request contains the JSON data that is sent by the client and other useful information from the client request.
     * insertRisk verifies that the Risk ID that wants to be inserted does not correspond to an existing Risk entry.
     */
    private void insertRisk(HttpServletRequest request) throws IOException{
        String riskData = request.getReader().lines().collect(Collectors.joining());
        Gson gson = new Gson();
        //System.out.println(riskData);
        Risk newRisk = gson.fromJson(riskData, Risk.class);
        newRisk.updateMagnitude();
        if (RiskDAO.getInstance().searchById(newRisk.getId()) != null) 
            throw new IOException("El riesgo que se insert� ya existe");
        
        RiskDAO.getInstance().add(newRisk); 
    }
    /**
     * @param request contains the JSON data that is sent by the client and other useful information from the client request.
     * editRisk verifies that the Risk ID that wants to be inserted does not correspond to an existing Risk entry and also
     * verifies that the user is able to do it in terms of permissions.
     */
    private void editRisk(HttpServletRequest request) throws IOException{
        String objetoEditado = request.getReader().lines().collect(Collectors.joining());
        Gson gsonEdit = new Gson();
        Risk riskEdit = gsonEdit.fromJson(objetoEditado, Risk.class);
        if (RiskDAO.getInstance().searchById(riskEdit.getId()) != null)
            RiskDAO.getInstance().update(riskEdit);
          else 
            throw new IOException("Este riesgo no esta registrado en el sistema");       
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
