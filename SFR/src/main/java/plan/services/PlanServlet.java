/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

/**
 *
 * @author arnol
 */
@WebServlet(name = "PlanServlet", urlPatterns = {
    "/API/PlanServlet", 
    "/API/PlanSearch", 
    "/API/RetrievePlans", 
    "/API/RetrievePlan"})
public class PlanServlet extends HttpServlet {

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
                case "/API/PlanServlet":
                    json = new Gson().toJson(PlanDAO.getInstance().listAll());
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(json);
                    response.getWriter().flush();
                    response.getWriter().close();
                    break;
                case "/API/PlanSearch":
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    requestData = request.getReader().lines().collect(Collectors.joining());
                    jsonObj = new JSONObject(requestData);
                    String value = jsonObj.getString("searchPlan");
                    json = new Gson().toJson(PlanDAO.getInstance().searchInAllColumns(value));
                    response.getWriter().write(json);
                    response.getWriter().flush();
                    response.getWriter().close();
                    break;
                case "/API/RetrievePlans":
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    requestData = request.getReader().lines().collect(Collectors.joining());
                    jsonObj = new JSONObject(requestData);
                    String sortingValue = jsonObj.getString("sortingValue");
                    String sortingWay = jsonObj.getString("sortingWay");
                    json = new Gson().toJson(PlanDAO.getInstance().listByColumn(sortingValue, sortingWay));
                    response.getWriter().write(json);
                    response.getWriter().flush();
                    response.getWriter().close();
                    break;
                case "/API/RetrievePlan":
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    requestData = request.getReader().lines().collect(Collectors.joining());
                    jsonObj = new JSONObject(requestData);
                    String planID = jsonObj.getString("planID");
                    json = new Gson().toJson(PlanDAO.getInstance().searchById(planID));
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
            Logger.getLogger(PlanServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(PlanServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
