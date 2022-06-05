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
import model.Role;
import service.Service;

/**
 *
 * @author jegon
 */
public class AdminServ extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.text.ParseException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            Service service = new Service();
            Role role = new Role();
            AdminFile admin = new AdminFile();
            admin.setID(Integer.parseInt(request.getParameter("ID")));
            admin.setName(request.getParameter("name"));
            admin.setLastname_1(request.getParameter("lastname_1"));
            admin.setLastname_2(request.getParameter("lastname_2"));
            admin.setEmployment(request.getParameter("employment"));
            String admission_Date = request.getParameter("admission_Date");
            admin.setAdmission_Date(new SimpleDateFormat("yyyy-MM-dd").parse(admission_Date));
            admin.setEmail(request.getParameter("email"));
            String phone = request.getParameter("phone");
            admin.setPublic_years(Integer.parseInt(request.getParameter("public_years")));
            admin.setHolidays(Integer.parseInt(request.getParameter("holidays")));
            admin.setSalary(Double.parseDouble(request.getParameter("salary")));
            admin.setPhone(Integer.parseInt(phone));
            admin.setIsEarlyVacations(false);
            admin.setDaysEarlyVacations(0);
            admin.setTotalEarlyVacations(0);
            role.setId_role(Integer.parseInt(request.getParameter("Role_id")));
            admin.setRole(role);
            admin.setAreaMuni(request.getParameter("area"));
            service.addAdmin(admin);
            request.getSession(true).setAttribute("redireccionamiento", "userRegister.jsp");
            response.sendRedirect("moduloSivac/success.jsp");
        } catch (NullPointerException | NumberFormatException ex) {
            response.sendRedirect("moduloSivac/error.jsp");
            request.getSession(true).setAttribute("redireccionamiento", "userRegister.jsp");
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
            Logger.getLogger(AdminServ.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AdminServ.class.getName()).log(Level.SEVERE, null, ex);
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
