/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

/**
 *
 * @author GONCAR4
 */
@WebServlet(name = "RiskManager", urlPatterns = {"/API/RiskManager/insert", "/API/RiskManager/delete"})
public class RiskManager extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD");
        try {
            switch (request.getServletPath()) {
                case "/API/RiskManager/insert":
                    String algo = request.getReader().lines().collect(Collectors.joining());
                    Gson gson = new Gson();
                    Risk newRisk = gson.fromJson(algo, Risk.class);
                    Risk riskExist = RiskDAO.getInstance().searchByIdSmall(newRisk.getId());
                    if (riskExist != null) {
                        throw new IOException();
                    }
                    RiskDAO.getInstance().add(newRisk);
                    break;
                case "/API/RiskManager/delete":
                    String obj = request.getReader().lines().collect(Collectors.joining());
                    Gson json = new Gson();
                    Risk newR = json.fromJson(obj, Risk.class);
                    Risk riskE = RiskDAO.getInstance().searchByIdSmall(newR.getId());
                    if (riskE == null) {
                        throw new IOException();
                    }
                    RiskDAO.getInstance().delete(riskE);
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
