/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comment.services;

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
import sfr.dao.CommentDAO;
import sfr.model.Comment;

/**
 *
 * @author GONCAR
 */
@WebServlet(name = "CommentManager", urlPatterns = {
    "/API/CommentManager/Insert",
    "/API/CommentManager/Edit",
    "/API/CommentManager/Delete",})
public class CommentManager extends HttpServlet {

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
        try {
            request.setCharacterEncoding("UTF-8");
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD");
            switch (request.getServletPath()) {
                case "/API/CommentManager/Insert":
                    insertComment(request, response);
                    break;
                case "/API/CommentManager/Edit":
                    editComment(request, response);
                    break;
                case "/API/CommentManager/Delete":
                    deleteComment(request, response);
                    break;
            }
            //response.setContentType("text/html");
            //response.setCharacterEncoding("UTF-8");
            //response.getWriter().write(request.getServletPath());
        } catch (Exception ex) {
            System.err.println(ex);
            Logger.getLogger(CommentManager.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Comment Management methods.">
    /**
     * @param request contains the JSON data that is sent by the client and
     * other useful information from the client request.
     * @param response sends the information back to the client with the
     * server's response. insertComment creates a new Comment entry in the
     * DB if it does not exists.
     */
    private void insertComment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JSONObject requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
        String comment = requestJSON.getString("comment");
        String author = requestJSON.getString("author");
        Comment c = new Comment(comment, author);
//        Comment newComment = c;
        CommentDAO.getInstance().add(c);
    }
    
    /**
     * @param request contains the JSON data that is sent by the client and
     * other useful information from the client request.
     * @param response sends the information back to the client with the
     * server's response. editComment edits a Comment entry in the DB if it
     * exists according to the requestJSON data.
     */
    private void editComment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String requestJSON = request.getReader().lines().collect(Collectors.joining());
        Comment editComment = new Gson().fromJson(requestJSON, Comment.class);
        if (CommentDAO.getInstance().searchById(editComment.getPkID()) != null) {
            CommentDAO.getInstance().update(editComment);
        } else {
            //Custom exception
//            response.getWriter().write(new CommentNotFoundEx().jsonify());
        }

    }
    
    /**
     * @param request contains the JSON data that is sent by the client and
     * other useful information from the client request.
     * @param response sends the information back to the client with the
     * server's response. deleteComment deletes a Comment entry in the DB if it exists
     * according to the requestJSON data.
     */
    private void deleteComment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JSONObject requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
        Comment toDelete = CommentDAO.getInstance().searchById(requestJSON.getInt("CommentPkID"));
        if (toDelete != null) 
            CommentDAO.getInstance().delete(toDelete);
         else //Custom exception
            throw new IOException("Comment not found.");
//            response.getWriter().write(new CommentNotFoundEx().jsonify());
        
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
