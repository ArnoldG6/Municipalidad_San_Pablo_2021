/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plan.services;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import sfr.dao.PlanDAO;
import sfr.model.Plan;
import sfr.model.Risk;

/**
 *
 * @author arnol
 */
@WebServlet(name = "PlanManager", urlPatterns = {"/API/PlanManager/insert", "/API/PlanManager/edit", "/API/PlanManager/delete"})
public class PlanManager extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        response.setHeader("Access-Control-Allow-Headers", "application/json, Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
        response.addHeader("Access-Control-Max-Age", "86400");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        try {
            switch (request.getServletPath()) {
                case "/API/PlanManager/insert":
                    String algo = request.getReader().lines().collect(Collectors.joining());
                    Gson gson = new Gson();
                    Plan newPlan = gson.fromJson(algo, Plan.class);
                    newPlan.setEntryDate(new Date());
                    Plan planExist = PlanDAO.getInstance().searchByIdSmall(newPlan.getId());
                    if (planExist != null) {
                        throw new IOException();
                    }
                    PlanDAO.getInstance().add(newPlan);
                    break;
                case "/API/PlanManager/edit":
                    String objeto = request.getReader().lines().collect(Collectors.joining());
                    Gson json = new Gson();
                    Plan editPlan = json.fromJson(objeto, Plan.class);
                    PlanDAO.getInstance().update(editPlan);
                    break;
                case "/API/PlanManager/deleteRisk":
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    String requestData = request.getReader().lines().collect(Collectors.joining());
                    JSONObject jsonObj = new JSONObject(requestData);
                    String planId = jsonObj.getString("planID");
                    String riskId = jsonObj.getString("riskID");
                    System.out.println("WEA_CUANTICA: "+planId+riskId);
                    Plan p = PlanDAO.getInstance().searchByIdSmall(planId);
                    List<Risk> riskList = p.getRiskList();
                    riskList.removeIf(r -> (r.getId().equals(riskId)));
                    p.setRiskList(riskList);
                    PlanDAO.getInstance().update(p);
                    break;

            }
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(request.getServletPath());
        } catch (Exception e) {
            throw new IOException();
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
        processRequest(request, response);
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
        processRequest(request, response);
    }
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
