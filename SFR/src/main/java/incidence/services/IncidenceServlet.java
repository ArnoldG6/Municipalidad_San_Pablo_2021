/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package incidence.services;

import com.google.gson.Gson;
import java.io.IOException;
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

/**
 *
 * @author GONCAR
 */
@WebServlet(name = "IncidenceServlet", urlPatterns = {
    "/API/IncidenceServlet/Retrieve/Incidencias",
    "/API/IncidenceServlet/Search"
})
public class IncidenceServlet extends HttpServlet {

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
            throws ServletException, IOException, Exception {
        try {
            request.setCharacterEncoding("UTF-8");
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD");
            switch (request.getServletPath()) {
                case "/API/IncidenceServlet/Retrieve/Incidencias":
                    retrieveIncidences(request, response);
                    break;
                case "/API/IncidenceServlet/Search":
                    searchIncidences(request, response);
                    break;
            }
        } catch (Exception ex) {
            System.err.println(ex);
            Logger.getLogger(IncidenceServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Incidence Management methods.">
    /**
     * @param request contains the JSON data that is sent by the client and
     * other useful information from the client request.
     * @param response sends the information back to the client with the
     * server's response. searchIncidences method answers to the client request with
     * a JSON formatted List<Incidence> object, determined by a search in all 'Incidence'
     * class attributes.
     */
    private void searchIncidences(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        String responseJSON;
        JSONObject requestJSON;
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
        responseJSON = new Gson().toJson(IncidenceDAO.getInstance().searchInAllColumns(requestJSON.getString("searchIncidence")));
        if (responseJSON == null) {
             //Custom exception
//            response.getWriter().write(new InvalidIncidenceListIDEx().jsonify());
        } else {
            response.getWriter().write(responseJSON);
        }
        response.getWriter().flush();
        response.getWriter().close();
    }
    
    /**
     * @param request contains the JSON data that is sent by the client and
     * other useful information from the client request.
     * @param response sends the information back to the client with the
     * server's response. retrieveIncidences method answers to the client request
     * with a JSON formatted List<Incidence> object, determined by a sorting
     * criteria.
     */
    private void retrieveIncidences(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        try {
            String responseJSON;
            JSONObject requestJSON;
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
            responseJSON = new Gson().toJson(IncidenceDAO.getInstance().
                    listByColumn(requestJSON.getString("sortingValue"), requestJSON.getString("sortingWay")));
            response.getWriter().write(responseJSON);

        } catch (Exception e) {
            response.getWriter().write(e.toString());
            throw e;
        } finally {
            response.getWriter().flush();
            response.getWriter().close();
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(IncidenceServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (Exception ex) {
            Logger.getLogger(IncidenceServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD");
        } catch (Exception ex) {
            Logger.getLogger(IncidenceServlet.class.getName()).log(Level.SEVERE, null, ex);
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
