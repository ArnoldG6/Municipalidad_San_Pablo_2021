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
import model.AdminFile;
import model.EarlyVacations;
import model.Notifications;
import model.Vacations;
import service.Service;

/**
 *
 * @author jegon
 */
public class Vacation extends HttpServlet {

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
            AdminFile verify = service.searchFileID(Integer.parseInt(request.getParameter("cedula")));
            Vacations vacat = new Vacations();
            Notifications notification = new Notifications();
            EarlyVacations early = new EarlyVacations();
            if (verify != null) {
                if (verify.getIsEarlyVacations()) {
                    notification.setIsRead(false);
                    notification.setDescription("Solicitud de vacaciones adelantadas");
                    String cedula = request.getParameter("cedula");
                    file.setID(Integer.parseInt(cedula));
                    early.setFile(file);
                    early.setStart_Date(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("start_Date")));
                    early.setFinal_Date(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("final_Date")));
                    notification.setId_Transmitter(Integer.parseInt(cedula));
                    notification.setIdReceiver(service.cedulaJefe(Integer.parseInt(cedula)));
                    service.addEarlyVacation(early);
                    service.addNotification(notification);
                    request.getSession(true).setAttribute("redireccionamiento", "requestVacation.jsp");
                    response.sendRedirect("moduloSivac/success.jsp");
                } else {
                    notification.setIsRead(false);
                    notification.setDescription("Solicitud de vacaciones");
                    String id = request.getParameter("cedula");
                    file.setID(Integer.parseInt(id));
                    vacat.setFile(file);
                    vacat.setStart_Date(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("start_Date")));
                    vacat.setFinal_Date(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("final_Date")));
                    vacat.setStatus("Pendiente");
                    notification.setId_Transmitter(Integer.parseInt(id));
                    notification.setIdReceiver(service.cedulaJefe(Integer.parseInt(id)));
                    service.addVacations(vacat);
                    service.addNotification(notification);
                    request.getSession(true).setAttribute("redireccionamiento", "requestVacation.jsp");
                    response.sendRedirect("moduloSivac/success.jsp");
                }
            } else {
                response.sendRedirect("moduloSivac/error.jsp");
                request.getSession(true).setAttribute("redireccionamiento", "requestVacation.jsp");
            }
        } catch (NumberFormatException ex) {
            response.sendRedirect("moduloSivac/error.jsp");
            request.getSession(true).setAttribute("redireccionamiento", "requestVacation.jsp");
        } catch (NullPointerException e) {
            response.sendRedirect("moduloSivac/VacationError.jsp");
            request.getSession(true).setAttribute("redireccionamiento", "requestVacation.jsp");
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
            Logger.getLogger(Vacation.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Vacation.class.getName()).log(Level.SEVERE, null, ex);
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
