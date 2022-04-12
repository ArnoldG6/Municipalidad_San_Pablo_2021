/**
 * @author GONCAR4
 * @author ArnoldG6 RiskManager class is in charge of handling requests from the
 * clients in order to create, delete or edit 'Risk' entries from the DB.
 */
package risk.services;

import com.google.gson.Gson;
import common.dao.UserDAO;
import common.model.User;
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
import sfr.dao.RiskDAO;
import sfr.dao.RiskTypeDAO;
import sfr.model.Risk;

@WebServlet(name = "RiskManager", urlPatterns = {
    "/API/RiskManager/Insert",
    "/API/RiskManager/Delete",
    "/API/RiskManager/Edit"
})
public class RiskManager extends HttpServlet {

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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Exception {
        try {
            request.setCharacterEncoding("UTF-8");
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD");
            switch (request.getServletPath()) {
                case "/API/RiskManager/Insert":
                    insertRisk(request, response);
                    break;
                case "/API/RiskManager/Delete":
                    deleteRisk(request, response);
                    break;
                case "/API/RiskManager/Edit":
                    editRisk(request, response);
                    break;
            }
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(request.getServletPath());
        } catch (Exception ex) {
            System.err.println(ex);
            Logger.getLogger(RiskManager.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Risk Management methods.">
    /**
     * @param request contains the JSON data that is sent by the client and
     * other useful information from the client request. deleteRisk method
     * verifies the user's credentials and verifies that the Risk ID that wants
     * to be deleted corresponds to an existing Risk.
     */
    private void deleteRisk(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
        //String requestJSON = request.getReader().lines().collect(Collectors.joining());
        JSONObject requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
        User user = UserDAO.getInstance().searchById(requestJSON.getInt("userID"));
        requestJSON.remove("userID");
        Risk newR = new Gson().fromJson(requestJSON.toString(), Risk.class);
        Risk riskE = RiskDAO.getInstance().searchById(newR.getPkId());
        if (riskE == null) {
            //Custom exception
            response.getWriter().write(new RiskNotFoundEx().jsonify());
//            throw new IOException();
        }
        if (!user.hasRol("SUPER_ADMIN")) {
            throw new IOException();
        }
        RiskDAO.getInstance().delete(riskE);
    }

    /**
     * @param request contains the JSON data that is sent by the client and
     * other useful information from the client request. insertRisk verifies
     * that the Risk ID that wants to be inserted does not correspond to an
     * existing Risk entry. edited by: ArnoldG6.
     */
    private void insertRisk(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestJSON = request.getReader().lines().collect(Collectors.joining());
        Risk newRisk = new Gson().fromJson(requestJSON, Risk.class);
        newRisk.updateMagnitude();
        long idCount = RiskTypeDAO.getInstance().handleIDAmount(newRisk.getId());
        String id = String.format("%02d", idCount);
        String newID = newRisk.getId() + id;
        newRisk.setId(newID);
        if (RiskDAO.getInstance().searchById(newRisk.getPkId()) != null) {
            //Custom exception
            response.getWriter().write(new RiskAlreadyExistEx().jsonify());
//            throw new IOException("El riesgo que se insertó ya existe");
        }
        RiskDAO.getInstance().add(newRisk);
        //JSON Response is required in order to redirect to the new Risk Page from the client-side.
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONObject responseJSON = new JSONObject();
        responseJSON.append("id", newRisk.getId());
        response.getWriter().write(responseJSON.toString());
        response.getWriter().flush();
        response.getWriter().close();
    }

    /**
     * @param request contains the JSON data that is sent by the client and
     * other useful information from the client request. editRisk verifies that
     * the Risk ID that wants to be inserted does not correspond to an existing
     * Risk entry and also verifies that the user is able to do it in terms of
     * permissions.
     */
    private void editRisk(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
        User user = UserDAO.getInstance().searchById(requestJSON.getInt("userID"));
        requestJSON.remove("userID");
        Risk riskEdit = new Gson().fromJson(requestJSON.toString(), Risk.class);
        
        if (!user.hasRol("SUPER_ADMIN") && !user.hasRol("ADMIN") && !riskEdit.getAuthor().getIdUser().equals(user.getIdUser())) {
            throw new IOException();
        }
        
        if (RiskDAO.getInstance().searchById(riskEdit.getPkId()) != null) {
            RiskDAO.getInstance().update(riskEdit);
        } else {
            //Custom exception
            response.getWriter().write(new RiskNotFoundEx().jsonify());
//            throw new IOException("Este riesgo no esta registrado en el sistema");     
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(RiskManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(RiskManager.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(RiskManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(RiskManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(RiskManager.class.getName()).log(Level.SEVERE, null, ex);
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
