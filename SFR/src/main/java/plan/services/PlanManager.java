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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import sfr.dao.PlanDAO;
import sfr.model.Plan;
import sfr.model.Risk;

/**
 *
 * @author arnol
 */
@WebServlet(name = "PlanManager", urlPatterns = {
    "/API/PlanManager/insert",
    "/API/PlanManager/edit",
    "/API/PlanManager/delete",
    "/API/PlanManager/deleteRisk",
    "/API/PlanManager/associateRiskToPlan",
    "/API/PlanManager/getRiskListByPlanNoRep"})
public class PlanManager extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD");
        String requestData,json;
        JSONObject jsonObj;
        try {
            switch (request.getServletPath()) {
                case "/API/PlanManager/insert":
                    String algo = request.getReader().lines().collect(Collectors.joining());
                    Gson gson = new Gson();
                    Plan newPlan = gson.fromJson(algo, Plan.class);
                    newPlan.setEntryDate(new Date());
                    Plan planExist = PlanDAO.getInstance().searchByIdSmall(newPlan.getId());
                    if (planExist != null) {
                        throw new IOException("El plan que se insertó ");
                    }
                    PlanDAO.getInstance().add(newPlan);
                    break;
                case "/API/PlanManager/edit":
                    String objetoEditado = request.getReader().lines().collect(Collectors.joining());
                    Gson gsonEdit = new Gson();
                    Plan editPlan = gsonEdit.fromJson(objetoEditado, Plan.class);
                    PlanDAO.getInstance().update(editPlan);
                    break;
                case "/API/PlanManager/delete":
                    String idObject = request.getReader().lines().collect(Collectors.joining());
                    JSONObject jsonObjDelete = new JSONObject(idObject);
                    String id = jsonObjDelete.getString("id");
                    Plan toDelete = new Plan(id);
                    PlanDAO.getInstance().delete(toDelete);
                    break;
                case "/API/PlanManager/deleteRisk":
                    HttpSession session = request.getSession(true);
                    if (session.getAttribute("userRol").equals("SUPER_ADMIN") || session.getAttribute("userRol").equals("ADMIN")) {
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        requestData = request.getReader().lines().collect(Collectors.joining());
                        jsonObj = new JSONObject(requestData);
                        String planId = jsonObj.getString("planID");
                        String riskId = jsonObj.getString("riskID");
                        Plan p = PlanDAO.getInstance().searchByIdSmall(planId);
                        List<Risk> riskList = p.getRiskList();
                        riskList.removeIf(r -> (String.valueOf(r.getId()).equals(riskId)));
                        p.setRiskList(riskList);
                        PlanDAO.getInstance().update(p);
                    }
                    break;
                case "/API/PlanManager/associateRiskToPlan":
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    requestData = request.getReader().lines().collect(Collectors.joining());
                    jsonObj = new JSONObject(requestData);
                    PlanDAO.getInstance().associatePlanToRisk(jsonObj.getString("planID"), jsonObj.getString("riskID"));
                    break;
                case "/API/PlanManager/getRiskListByPlanNoRep":
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    requestData = request.getReader().lines().collect(Collectors.joining());
                    jsonObj = new JSONObject(requestData);
                    json = new Gson().toJson(PlanDAO.getInstance().getRiskListByPlanNoRep(jsonObj.getString("planID")));
                    response.getWriter().write(json);
                    response.getWriter().flush();
                    response.getWriter().close();
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
            processRequest(request, response);
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
