/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.frontend;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.LicenseCGS;
import model.Notifications;
import service.Service;

/**
 *
 * @author jegon
 */
public class AcceptCGS extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        LicenseCGS cgs = new LicenseCGS();
        Service service = new Service();
        Notifications notification = new Notifications();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        cgs.setNumber_License(Integer.parseInt(request.getParameter("num")));
        cgs.setCategory(request.getParameter("categoriaCGS"));
        cgs.setStart_Date(sdf.parse(request.getParameter("start_Date")));
        cgs.setFinal_Date(sdf.parse(request.getParameter("final_Date")));
        cgs.setJustification(request.getParameter("justification"));
        cgs.setStatus(request.getParameter("cambiarStatus"));
        if (cgs.getStatus().equals("Denegada")) {
            notification.setDescription("Permiso Denegado");
            notification.setIdReceiver(service.getCedulaFuncionario(
            Integer.parseInt(request.getSession(true).getAttribute("id").toString())
            ));
            notification.setId_Transmitter(Integer.parseInt(request.getSession(true).getAttribute("id").toString()));
            notification.setIsRead(false);
            service.addNotification(notification);
            service.updateLicenseCGS(cgs);
            request.getSession(true).setAttribute("numSolicitud", cgs.getNumber_License());
            response.sendRedirect("moduloSivac/listCGS.jsp");
        } else {
            notification.setDescription("Permiso Aprobado");
            notification.setIdReceiver(service.getCedulaFuncionario(
            Integer.parseInt(request.getSession(true).getAttribute("id").toString())
            ));
            notification.setId_Transmitter(Integer.parseInt(request.getSession(true).getAttribute("id").toString()));
            notification.setIsRead(false);
            service.addNotification(notification);
            service.updateLicenseCGS(cgs);
            response.sendRedirect("moduloSivac/listCGS.jsp");
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
        } catch (ParseException ex) {
            Logger.getLogger(AcceptCGS.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (ParseException ex) {
            Logger.getLogger(AcceptCGS.class.getName()).log(Level.SEVERE, null, ex);
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
