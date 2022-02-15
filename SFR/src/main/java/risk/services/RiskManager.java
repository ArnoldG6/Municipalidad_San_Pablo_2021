/**
 * @author GONCAR4
 * @author ArnoldG6 RiskManager class is in charge of handling requests from the
 * clients in order to create, delete or edit 'Risk' entries from the DB.
 */
package risk.services;

import com.google.gson.Gson;
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
import sfr.dao.RiskDAO;
import sfr.model.Risk;

@WebServlet(name = "RiskManager", urlPatterns = {
    "/API/RiskManager/insert",
    "/API/RiskManager/delete",
    "/API/RiskManager/edit"
}
)
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
                case "/API/RiskManager/insert":
                    insertRisk(request, response);
                    break;
                case "/API/RiskManager/delete":
                    deleteRisk(request, response);
                    break;
                case "/API/RiskManager/edit":
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
        String requestJSON = request.getReader().lines().collect(Collectors.joining());
        Risk newR = new Gson().fromJson(requestJSON, Risk.class);
        Risk riskE = RiskDAO.getInstance().searchById(newR.getId());
        if (riskE == null) {
            //Custom exception
            response.getWriter().write(new RiskNotFoundEx().jsonify());
//            throw new IOException();
        }
        RiskDAO.getInstance().delete(riskE);
    }

    /**
     * @param request contains the JSON data that is sent by the client and
     * other useful information from the client request. insertRisk verifies
     * that the Risk ID that wants to be inserted does not correspond to an
     * existing Risk entry.
     */
    private void insertRisk(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestJSON = request.getReader().lines().collect(Collectors.joining());
        Risk newRisk = new Gson().fromJson(requestJSON, Risk.class);
        newRisk.updateMagnitude();
        if (RiskDAO.getInstance().searchById(newRisk.getId()) != null) {
            //Custom exception
            response.getWriter().write(new RiskAlreadyExistEx().jsonify());
//            throw new IOException("El riesgo que se insertó ya existe");
        }
        RiskDAO.getInstance().add(newRisk);
    }

    /**
     * @param request contains the JSON data that is sent by the client and
     * other useful information from the client request. editRisk verifies that
     * the Risk ID that wants to be inserted does not correspond to an existing
     * Risk entry and also verifies that the user is able to do it in terms of
     * permissions.
     */
    private void editRisk(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestJSON = request.getReader().lines().collect(Collectors.joining());
        Risk riskEdit = new Gson().fromJson(requestJSON, Risk.class);
        if (RiskDAO.getInstance().searchById(riskEdit.getId()) != null) {
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
