/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk.services;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sfr.dao.RiskDAO;
import sfr.model.Risk;

/**
 *
 * @author GONCAR4
 */
@WebServlet(name = "RiskManager", urlPatterns = {"/API/RiskManager/insert","/API/RiskManager/delete"})
public class RiskManager extends HttpServlet {

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
