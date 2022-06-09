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
import model.AdminFile;
import model.LicenseSGS;
import model.Notifications;
import service.Service;

/**
 *
 * @author jegon
 */
public class SGS extends HttpServlet {

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
        try {
            Service service = new Service();
            AdminFile file = new AdminFile();
            LicenseSGS lic = new LicenseSGS();
            Notifications notification = new Notifications();
            notification.setIsRead(false);
            notification.setDescription("Solicitud de licencia sin goce salarial");
            String id = request.getParameter("cedula");
            file.setID(Integer.parseInt(id));
            String categoria = request.getParameter("categoriaSGS");
            lic.setCategory(categoria);
            lic.setStart_Date(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("start_Date")));
            lic.setFinal_Date(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("final_Date")));
            lic.setJustification(request.getParameter("justification"));
            lic.setStatus("Pendiente");
            notification.setId_Transmitter(Integer.parseInt(id));
            notification.setIdReceiver(service.cedulaJefe(Integer.parseInt(id)));
            lic.setFile(file);
            service.addSGS(lic);
            service.addNotification(notification);
            request.getSession(true).setAttribute("redireccionamiento", "licSGSControl.jsp");
            response.sendRedirect("moduloSivac/success.jsp");
        } catch (NumberFormatException ex) {
            response.sendRedirect("moduloSivac/error.jsp");
            request.getSession(true).setAttribute("redireccionamiento", "licSGSControl.jsp");
        } catch (NullPointerException e) {
            response.sendRedirect("moduloSivac/DateError.jsp");
            request.getSession(true).setAttribute("redireccionamiento", "licSGSControl.jsp");
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
            Logger.getLogger(SGS.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(SGS.class.getName()).log(Level.SEVERE, null, ex);
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
