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
import model.Notifications;
import model.Vacations;
import service.Service;

/**
 *
 * @author jegon
 */
public class AcceptVacation extends HttpServlet {

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
        Vacations vac = new Vacations();
        Service service = new Service();
        Notifications notification = new Notifications();
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //sdf.setLenient(false);
        vac.setVacations_ID(Integer.parseInt(request.getParameter("accept")));
        //vac.setStart_Date(sdf.parse(request.getParameter("start_Date")));
        //vac.setFinal_Date(sdf.parse(request.getParameter("final_Date")));
        vac.setStatus("Revisado");
        /*if (vac.getStatus().equals("Denegada")) {
            notification.setDescription("Vacaciones Desaprobadas");
            notification.setIdReceiver(service.getCedulaFuncionario(
                    Integer.parseInt(request.getSession(true).getAttribute("id").toString())
            ));
            notification.setId_Transmitter(Integer.parseInt(request.getSession(true).getAttribute("id").toString()));
            notification.setIsRead(false);
            service.addNotification(notification);
            service.updateVacation(vac);
            request.getSession(true).setAttribute("numSolicitud", vac.getVacations_ID());
            response.sendRedirect("moduloSivac/pendingVacation.jsp");
        } else {
            notification.setDescription("Vacaciones Aprobadas");
            notification.setIdReceiver(service.getCedulaFuncionario(
                    Integer.parseInt(request.getSession(true).getAttribute("id").toString())
            ));
            notification.setId_Transmitter(Integer.parseInt(request.getSession(true).getAttribute("id").toString()));
            notification.setIsRead(false);
            service.addNotification(notification);
            service.updateVacation(vac);
            response.sendRedirect("moduloSivac/pendingVacation.jsp");
        }*/
        notification.setDescription("Vacaciones Revisadas");
        notification.setIdReceiver(service.getCedulaFuncionario(
                Integer.parseInt(request.getSession(true).getAttribute("id").toString())
        ));
        notification.setId_Transmitter(Integer.parseInt(request.getSession(true).getAttribute("id").toString()));
        notification.setIsRead(false);
        service.addNotification(notification);
        service.updateVacation(vac);
        request.getSession(true).setAttribute("numSolicitud", vac.getVacations_ID());
        response.sendRedirect("moduloSivac/pendingVacation.jsp");
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
            Logger.getLogger(AcceptVacation.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AcceptVacation.class.getName()).log(Level.SEVERE, null, ex);
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
