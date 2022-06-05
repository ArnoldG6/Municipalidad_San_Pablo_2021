/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.frontend;

import contants.Constants;
import javax.servlet.annotation.MultipartConfig;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import model.AdminFile;
import model.LaboraInhability;
import model.Notifications;
import org.apache.commons.io.IOUtils;
import service.Service;

/**
 *
 * @author jegon
 */
@MultipartConfig()
public class disabilityProof extends HttpServlet {

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Service service = new Service();
        AdminFile file = new AdminFile();
        LaboraInhability proof = new LaboraInhability();
        Notifications notification = new Notifications();

        try {
            String id = request.getParameter("ced");
            file.setID(Integer.parseInt(id));
            proof.setFile(file);
            
            Part part = request.getPart("fileBrowser");
            String campo = part.getName();
            System.out.printf("Nombre del campo (formulario): '%s'%n", campo);

            String nombreArchivo = part.getSubmittedFileName();
            notification.setIsRead(false);
            notification.setDescription("Envío de comprobante de incapacidad");

            if (nombreArchivo.isEmpty()) {
                request.getSession(true).setAttribute("redireccionamiento",
                        "disabilityProof.jsp");
                response.sendRedirect("moduloSivac/error.jsp");
            }

            if (Constants.validar(nombreArchivo)) {
                InputStream is = part.getInputStream();
                byte[] imagen = IOUtils.toByteArray(is);
                proof.setVoucher(imagen);
                Calendar calendar = Calendar.getInstance();
                Date dateObj = calendar.getTime();
                proof.setMainDate(dateObj);
                notification.setId_Transmitter(Integer.parseInt(id));
                notification.setIdReceiver(service.cedulaJefe(Integer.parseInt(id)));
                service.addDisabilityProof(proof);
                service.addNotification(notification);
                request.getSession(true).setAttribute("redireccionamiento", "disabilityProof.jsp");
                response.sendRedirect("moduloSivac/success.jsp");
            } else {
                request.getSession(true).setAttribute("redireccionamiento","disabilityProof.jsp");
                response.sendRedirect("moduloSivac/ErrorArchivo.jsp");
            }

        } catch (IOException | NumberFormatException ex) {
            request.getSession(true).setAttribute("redireccionamiento","disabilityProof.jsp");
            response.sendRedirect("moduloSivac/error.jsp");
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
