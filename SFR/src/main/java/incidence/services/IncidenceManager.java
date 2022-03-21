/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package incidence.services;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import sfr.dao.IncidenceDAO;
import sfr.model.Incidence;

/**
 *
 * @author GONCAR
 */
@WebServlet(name = "IncidenceManager", urlPatterns = {
    "/API/IncidenceManager/Insert",
    "/API/IncidenceManager/Edit",
    "/API/IncidenceManager/Delete",})
public class IncidenceManager extends HttpServlet {

    /**
     * Processes requests for <code>GET</code>, <code>POST</code>
     * ,<code>OPTIONS</code>, <code>PUT</code>, <code>DELETE</code> HTTP
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD");
            switch (request.getServletPath()) {
                case "/API/IncidenceManager/Insert":
                    insertIncidence(request, response);
                    break;
                case "/API/IncidenceManager/Edit":
                    editIncidence(request, response);
                    break;
                case "/API/IncidenceManager/Delete":
                    deleteIncidence(request, response);
                    break;
            }
            //response.setContentType("text/html");
            //response.setCharacterEncoding("UTF-8");
            //response.getWriter().write(request.getServletPath());
        } catch (Exception ex) {
            System.err.println(ex);
            Logger.getLogger(IncidenceManager.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

// <editor-fold defaultstate="collapsed" desc="Incidence Management methods.">
    /**
     * @param request contains the JSON data that is sent by the client and
     * other useful information from the client request.
     * @param response sends the information back to the client with the
     * server's response. insertIncidence creates a new Incidence entry in the
     * DB if it does not exists.
     */
    private void insertIncidence(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String requestJSON = request.getReader().lines().collect(Collectors.joining());

        Date d = new Date();

        Incidence newIncidence = new Gson().fromJson(requestJSON, Incidence.class);
        newIncidence.setDate(d);

//        if (IncidenceDAO.getInstance().searchById(newIncidence.getPkId()) != null) {
//            //Custom exception
//            response.getWriter().write(new IncidenceAlreadyExistEx().jsonify());
//        }
        IncidenceDAO.getInstance().add(newIncidence);
    }

    /**
     * @param request contains the JSON data that is sent by the client and
     * other useful information from the client request.
     * @param response sends the information back to the client with the
     * server's response. editIncidence edits a Incidence entry in the DB if it
     * exists according to the requestJSON data.
     */
    private void editIncidence(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String requestJSON = request.getReader().lines().collect(Collectors.joining());
        Incidence editIncidence = new Gson().fromJson(requestJSON, Incidence.class);
        if (IncidenceDAO.getInstance().searchById(editIncidence.getPkID()) != null) {
            IncidenceDAO.getInstance().update(editIncidence);
        } else {
            //Custom exception
//            response.getWriter().write(new IncidenceNotFoundEx().jsonify());
        }

    }

    /**
     * @param request contains the JSON data that is sent by the client and
     * other useful information from the client request.
     * @param response sends the information back to the client with the
     * server's response. deletePlan deletes a Plan entry in the DB if it exists
     * according to the requestJSON data.
     */
    private void deleteIncidence(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JSONObject requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
        Incidence toDelete = new Incidence();
        toDelete.setPkID(requestJSON.getInt("pkID"));
        if (IncidenceDAO.getInstance().searchById(toDelete.getPkID()) != null) {
            IncidenceDAO.getInstance().delete(toDelete);
        } else //Custom exception
        {
//            response.getWriter().write(new IncidenceNotFoundEx().jsonify());
        }
    }
// </editor-fold>
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
