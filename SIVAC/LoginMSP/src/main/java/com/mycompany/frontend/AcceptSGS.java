/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.frontend;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.LicenseSGS;
import model.Notifications;
import service.Service;

/**
 *
 * @author jegon
 */
public class AcceptSGS extends HttpServlet {

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
        LicenseSGS sgs = new LicenseSGS();
        Service service = new Service();
        Notifications notification = new Notifications();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        sgs.setNumber_License(Integer.parseInt(request.getParameter("num")));
        sgs.setCategory(request.getParameter("categoriaSGS"));
        sgs.setStart_Date(sdf.parse(request.getParameter("start_Date")));
        sgs.setFinal_Date(sdf.parse(request.getParameter("final_Date")));
        sgs.setJustification(request.getParameter("justification"));
        sgs.setStatus(request.getParameter("cambiarStatus"));
        if (sgs.getStatus().equals("Denegada")) {
            notification.setDescription("Permiso Denegado");
            notification.setIdReceiver(service.getCedulaFuncionario(
            Integer.parseInt(request.getSession(true).getAttribute("id").toString())
            ));
            notification.setId_Transmitter(Integer.parseInt(request.getSession(true).getAttribute("id").toString()));
            notification.setIsRead(false);
            service.addNotification(notification);
            service.updateLicenseSGS(sgs);
            request.getSession(true).setAttribute("numSolicitud", sgs.getNumber_License());
            response.sendRedirect("moduloSivac/listSGS.jsp");
        } else {
            notification.setDescription("Permiso Aprobado");
            notification.setIdReceiver(service.getCedulaFuncionario(
            Integer.parseInt(request.getSession(true).getAttribute("id").toString())
            ));
            notification.setId_Transmitter(Integer.parseInt(request.getSession(true).getAttribute("id").toString()));
            notification.setIsRead(false);
            service.addNotification(notification);
            service.updateLicenseSGS(sgs);
            response.sendRedirect("moduloSivac/listSGS.jsp");
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
            Logger.getLogger(AcceptSGS.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AcceptSGS.class.getName()).log(Level.SEVERE, null, ex);
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
