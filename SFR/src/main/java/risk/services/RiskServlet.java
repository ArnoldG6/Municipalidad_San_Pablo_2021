/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk.services;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
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

/**
 *
 * @author GONCAR4
 */
@WebServlet(name = "RiskServlet", urlPatterns = {
    "/API/RiskServlet",
    "/API/RiskSearch",
    "/API/RetrieveRisks",
    "/API/RetrieveRisk"})
public class RiskServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        request.setCharacterEncoding("UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        response.setHeader("Access-Control-Allow-Headers", "application/json, Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
        response.addHeader("Access-Control-Max-Age", "86400");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        try {
            String json, requestData;
            JSONObject jsonObj;
            switch (request.getServletPath()) {
                case "/API/RiskServlet":
                    json = new Gson().toJson(RiskDAO.getInstance().listAll());
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(json);
                    response.getWriter().flush();
                    response.getWriter().close();
                    break;
                case "/API/RiskSearch":
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    requestData = request.getReader().lines().collect(Collectors.joining());
                    jsonObj = new JSONObject(requestData);
                    String name = jsonObj.getString("searchRisk");
                    json = new Gson().toJson(RiskDAO.getInstance().listSearchBy("name", name));
                    response.getWriter().write(json);
                    response.getWriter().flush();
                    response.getWriter().close();
                    break;
                case "/API/RetrieveRisks":
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    requestData = request.getReader().lines().collect(Collectors.joining());
                    jsonObj = new JSONObject(requestData);
                    String sortingValue = jsonObj.getString("sortingValue");
                    String sortingWay = jsonObj.getString("sortingWay");
                    json = new Gson().toJson(RiskDAO.getInstance().listByColumn(sortingValue, sortingWay));
                    response.getWriter().write(json);
                    response.getWriter().flush();
                    response.getWriter().close();
                    break;
                case "/API/RetrieveRisk":
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    requestData = request.getReader().lines().collect(Collectors.joining());
                    jsonObj = new JSONObject(requestData);
                    String riskID = jsonObj.getString("riskID");
                    json = new Gson().toJson(RiskDAO.getInstance().searchById(riskID));
                    response.getWriter().write(json);
                    response.getWriter().flush();
                    response.getWriter().close();
                    break;
            }
        } catch (Exception e) {
            System.err.println(e);
            throw e;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(RiskServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
