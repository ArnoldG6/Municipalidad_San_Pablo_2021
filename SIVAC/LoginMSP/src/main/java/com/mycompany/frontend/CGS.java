/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.frontend;

import contants.Constants;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import model.AdminFile;
import model.LicenseCGS;
import model.Notifications;
import org.apache.commons.io.IOUtils;
import service.Service;

/**
 *
 * @author jegon
 */
@MultipartConfig()
public class CGS extends HttpServlet {

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
            LicenseCGS lic = new LicenseCGS();
            AdminFile file = new AdminFile();
            Notifications notification = new Notifications();
            notification.setIsRead(false);
            notification.setDescription("Solicitud de licencia con goce salarial");
            String id = request.getParameter("cedula");
            file.setID(Integer.parseInt(id));
            Part part = request.getPart("archivo");
            String campo = part.getName();
            System.out.printf("Nombre del campo (formulario): '%s'%n", campo);
            String nombreArchivo = part.getSubmittedFileName();
            if (nombreArchivo.isEmpty()) {
                lic.setPDF(null);
                notification.setId_Transmitter(Integer.parseInt(id));
                notification.setIdReceiver(service.cedulaJefe(Integer.parseInt(id)));
            } else {
                if (Constants.validarPDF(nombreArchivo)) {
                    InputStream is = part.getInputStream();
                    byte[] archivo = IOUtils.toByteArray(is);
                    lic.setPDF(archivo);
                    notification.setId_Transmitter(Integer.parseInt(id));
                    notification.setIdReceiver(service.getCedulaAlcalde());
                } else {
                    throw new NumberFormatException();
                }
            }
            String categoria = request.getParameter("categoriaCGS");
            lic.setCategory(categoria);
            lic.setStart_Date(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("start_Date")));
            lic.setFinal_Date(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("final_Date")));
            lic.setJustification(request.getParameter("justification"));
            lic.setStatus("Pendiente");
            lic.setFile(file);
            service.addCGS(lic);
            service.addNotification(notification);
            request.getSession(true).setAttribute("redireccionamiento", "licCGSControl.jsp");
            response.sendRedirect("moduloSivac/success.jsp");
        } catch (NumberFormatException e) {
            response.sendRedirect("moduloSivac/error.jsp");
            request.getSession(true).setAttribute("redireccionamiento", "licCGSControl.jsp");
        } catch (NullPointerException e) {
            response.sendRedirect("moduloSivac/DateError.jsp");
            request.getSession(true).setAttribute("redireccionamiento", "licCGSControl.jsp");
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
            Logger.getLogger(CGS.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CGS.class.getName()).log(Level.SEVERE, null, ex);
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
