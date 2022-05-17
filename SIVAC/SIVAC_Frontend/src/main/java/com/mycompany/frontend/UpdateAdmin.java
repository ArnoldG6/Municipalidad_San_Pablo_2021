/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.frontend;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.AdminFile;
import model.Role;
import service.Service;

/**
 *
 * @author jegon
 */
public class UpdateAdmin extends HttpServlet {

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
        Service service = new Service();
        Role role = new Role();
        AdminFile admin = new AdminFile();

        admin.setID(Integer.parseInt(request.getParameter("cedula")));
        admin.setName(request.getParameter("name"));
        admin.setLastname_1(request.getParameter("lastname_1"));
        admin.setLastname_2(request.getParameter("lastname_2"));
                admin.setAreaMuni(request.getParameter("area"));
        admin.setEmployment(request.getParameter("employment"));
        admin.setSalary(Double.parseDouble(request.getParameter("salary")));
        admin.setAdmission_Date(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("admission_Date")));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = format.format(admin.getAdmission_Date());
        admin.setAdmission_Date(new SimpleDateFormat("yyyy-MM-dd").parse(fecha));
        admin.setPublic_years(Integer.parseInt(request.getParameter("public_years")));
        admin.setEmail(request.getParameter("email"));
        admin.setPhone(Integer.parseInt(request.getParameter("phone")));
        admin.setHolidays(Integer.parseInt(request.getParameter("holidays")));
        role.setId_role(Integer.parseInt(request.getParameter("Role_id")));
        admin.setRole(role);
        if (Integer.parseInt(request.getParameter("early")) == 0) {
            admin.setIsEarlyVacations(false);
        } else {
            admin.setIsEarlyVacations(true);
        }
        admin.setDaysEarlyVacations(0);
        admin.setTotalEarlyVacations(0);
        service.updateFile(admin);
        response.sendRedirect("moduloSivac/listUser.jsp");
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
            Logger.getLogger(UpdateAdmin.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(UpdateAdmin.class.getName()).log(Level.SEVERE, null, ex);
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
