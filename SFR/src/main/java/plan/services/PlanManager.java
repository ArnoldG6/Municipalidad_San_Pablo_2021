/**
 * @author GONCAR4
 * @author ArnoldG6 PlanManager class is in charge of handling requests from the
 * clients in order to create, delete or edit 'Plan' entries from the DB.
 */
package plan.services;

import com.google.gson.Gson;
import common.dao.UserDAO;
import common.model.User;
import ex.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import sfr.dao.PlanDAO;
import sfr.dao.PlanTypeDAO;
import sfr.dao.RiskDAO;
import sfr.model.Comment;
import sfr.model.Incidence;
import sfr.model.Plan;
import sfr.model.Risk;

@WebServlet(name = "PlanManager", urlPatterns = {
    "/API/PlanManager/Insert",
    "/API/PlanManager/Edit",
    "/API/PlanManager/Delete",
    "/API/PlanManager/Insert/Risk",
    "/API/PlanManager/Delete/Risk",
    "/API/PlanManager/Insert/Incidence",
    "/API/PlanManager/Delete/Incidence",
    "/API/PlanManager/Insert/Involved",
    "/API/PlanManager/Delete/Involved",
    "/API/PlanManager/Insert/Comment",
    "/API/PlanManager/Delete/Comment"
})
public class PlanManager extends HttpServlet {

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
                case "/API/PlanManager/Insert":
                    insertPlan(request, response);
                    break;
                case "/API/PlanManager/Edit":
                    editPlan(request, response);
                    break;
                case "/API/PlanManager/Delete":
                    deletePlan(request, response);
                    break;
                case "/API/PlanManager/Delete/Risk":
                    deleteRiskFromPlan(request, response);
                    break;
                case "/API/PlanManager/Delete/Incidence":
                    deleteIncidenceFromPlan(request, response);
                    break;
                case "/API/PlanManager/Insert/Risk":
                    associateRiskToPlan(request, response);
                    break;
                case "/API/PlanManager/Insert/Incidence":
                    associateIncidenceToPlan(request, response);
                    break;
                case "/API/PlanManager/Insert/Comment":
                    associateCommentToPlan(request, response);
                    break;
                case "/API/PlanManager/Delete/Comment":
                    deleteCommentFromPlan(request, response);
                    break;
                case "/API/PlanManager/Insert/Involved":
                    insertInvolved(request, response);
                    break;
                case "/API/PlanManager/Delete/Involved":
                    deleteInvolved(request, response);
                    break;
            }
            //response.setContentType("text/html");
            //response.setCharacterEncoding("UTF-8");
            //response.getWriter().write(request.getServletPath());
        } catch (Exception ex) {
            System.err.println(ex);
            Logger.getLogger(PlanManager.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }

    }

    // <editor-fold defaultstate="collapsed" desc="Plan Management methods.">
    /**
     * @param request contains the JSON data that is sent by the client and
     * other useful information from the client request.
     * @param response sends the information back to the client with the
     * server's response. insertPlan creates a new Plan entry in the DB if it
     * does not exists.
     */
    private void insertPlan(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            JSONObject requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
            User user = UserDAO.getInstance().searchById(requestJSON.getInt("userID"));
            requestJSON.remove("userID");

            if (user == null) {
                throw new IllegalAccessError("No se encontró el usuario que intentó realizar la transacción.");
            }

            Date d = new Date();
            LocalDate localDate = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            Plan newPlan = new Gson().fromJson(requestJSON.toString(), Plan.class);
            newPlan.setEntryDate(d);
            newPlan.addInvolucrado(user);

            int idCount = PlanTypeDAO.getInstance().handleIDAmount(newPlan.getId());
            String year = Integer.toString(localDate.getYear());
            String month = String.format("%02d", localDate.getMonthValue());
            String day = String.format("%02d", localDate.getDayOfMonth());
            String id = String.format("%05d", idCount);
            String newID = newPlan.getId() + "-" + year + month + day + "-" + id;
            newPlan.setId(newID);

            PlanDAO.getInstance().add(newPlan);

        } catch (IllegalAccessError e) {
            response.sendError(401, e.getMessage());
        } catch (Exception e) {
            response.sendError(500, e.getMessage());
        }
    }

    /**
     * @param request contains the JSON data that is sent by the client and
     * other useful information from the client request.
     * @param response sends the information back to the client with the
     * server's response. editPlan edits a Plan entry in the DB if it exists
     * according to the requestJSON data.
     */
    private void editPlan(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            JSONObject requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
            User user = UserDAO.getInstance().searchById(requestJSON.getInt("userID"));

            if (user == null) {
                throw new IllegalAccessError("No se encontró el usuario que intentó realizar la transacción.");
            }

            requestJSON.remove("userID");
            Plan editPlan = new Gson().fromJson(requestJSON.toString(), Plan.class);

            if (PlanDAO.getInstance().searchById(editPlan.getPkId()) == null) {
                throw new NullPointerException("No se encontró el plan que se desea editar.");
            }

            if ((!user.hasRol("SUPER_ADMIN") && !user.hasRol("ADMIN") && !editPlan.containsInvolved(user)) || editPlan.getStatus().equals("Completo")) {
                throw new IllegalAccessError("Este usuario no cuenta con los permisos para realizar esta acción.");
            }

            PlanDAO.getInstance().update(editPlan);

        } catch (NullPointerException e) {
            response.sendError(400, e.getMessage());
        } catch (IllegalAccessError e) {
            response.sendError(401, e.getMessage());
        } catch (Exception e) {
            response.sendError(500, e.getMessage());
        }
    }

    /**
     * @param request contains the JSON data that is sent by the client and
     * other useful information from the client request.
     * @param response sends the information back to the client with the
     * server's response. deletePlan deletes a Plan entry in the DB if it exists
     * according to the requestJSON data.
     */
    private void deletePlan(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            JSONObject requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
            Plan toDelete = PlanDAO.getInstance().searchById(requestJSON.getInt("pkID"));
            User user = UserDAO.getInstance().searchById(requestJSON.getInt("userID"));

            if (user == null) {
                throw new IllegalAccessError("No se encontró el usuario que intentó realizar la transacción.");
            }
            if (PlanDAO.getInstance().searchById(toDelete.getPkId()) == null) {
                throw new NullPointerException("No se encontró el plan que se desea eliminar.");
            }

            if ((!user.hasRol("SUPER_ADMIN")) || toDelete.getStatus().equals("Completo")) {
                throw new IllegalAccessError("Este usuario no cuenta con los permisos para realizar esta acción.");
            }

            PlanDAO.getInstance().delete(toDelete);
        } catch (NullPointerException e) {
            response.sendError(400, e.getMessage());
        } catch (IllegalAccessError e) {
            response.sendError(401, e.getMessage());
        } catch (Exception e) {
            response.sendError(500, e.getMessage());
        }
    }

    /**
     * @param request contains the JSON data that is sent by the client and
     * other useful information from the client request.
     * @param response sends the information back to the client with the
     * server's response. deleteRiskFromPlan deletes a Plan list of Risks
     * determined by the requestJSON.
     */
    private void deleteRiskFromPlan(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            JSONObject requestJSON;
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
            Plan plan = PlanDAO.getInstance().searchById(requestJSON.getInt("planPkID"));
            User user = UserDAO.getInstance().searchById(requestJSON.getInt("userID"));

            if (user == null) {
                throw new IllegalAccessError("No se encontró el usuario que intentó realizar la transacción.");
            }

            if (plan == null) {
                throw new NullPointerException("No se encontró el plan del cual desea eliminar riesgos.");
            }

            if ((!user.hasRol("SUPER_ADMIN") && !user.hasRol("ADMIN") && !plan.containsInvolved(user)) || plan.getStatus().equals("Completo")) {
                throw new IllegalAccessError("Este usuario no cuenta con los permisos para realizar esta acción.");
            }

            List<Risk> riskList = plan.getRiskList();

            if (riskList == null) {
                throw new NullPointerException("El Plan no cuenta con riesgos por eliminar.");
            }
            riskList.removeIf(r -> r.getPkId() == requestJSON.getInt("riskPkID"));
            plan.setRiskList(riskList);
            PlanDAO.getInstance().update(plan);

        } catch (NullPointerException e) {
            response.sendError(400, e.getMessage());
        } catch (IllegalAccessError e) {
            response.sendError(401, e.getMessage());
        } catch (Exception e) {
            response.sendError(500, e.getMessage());
        }
    }

    /**
     * @param request contains the JSON data that is sent by the client and
     * other useful information from the client request.
     * @param response sends the information back to the client with the
     * server's response. deleteIncidenceFromPlan deletes a Plan list of
     * Incidences determined by the requestJSON.
     */
    private void deleteIncidenceFromPlan(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            JSONObject requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
            Plan plan = PlanDAO.getInstance().searchById(requestJSON.getInt("planPkID"));
            User user = UserDAO.getInstance().searchById(requestJSON.getInt("userID"));

            if (user == null) {
                throw new IllegalAccessError("No se encontró el usuario que intentó realizar la transacción.");
            }

            if (plan == null) {
                throw new NullPointerException("No se encontró el plan del cual desea eliminar incidencias.");
            }

            if ((!user.hasRol("SUPER_ADMIN") && !user.hasRol("ADMIN") && !plan.containsInvolved(user)) || plan.getStatus().equals("Completo")) {
                throw new IllegalAccessError("Este usuario no cuenta con los permisos para realizar esta acción.");
            }

            List<Incidence> l = plan.getIncidenceList();

            l.removeIf(incidence -> incidence.getPkID() == requestJSON.getInt("incidencePkID"));

            plan.setIncidenceList(l);
            PlanDAO.getInstance().update(plan);

        } catch (NullPointerException e) {
            response.sendError(400, e.getMessage());
        } catch (IllegalAccessError e) {
            response.sendError(401, e.getMessage());
        } catch (Exception e) {
            response.sendError(500, e.getMessage());
        }
    }

    /**
     * @param request contains the JSON data that is sent by the client and
     * other useful information from the client request.
     * @param response sends the information back to the client with the
     * server's response. associateRiskToPlan updates the DB entries of 'Plan'
     * associating it to a list of 'Plan' entries sent by the client.
     */
    private void associateRiskToPlan(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            JSONObject requestJSON;
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
            JSONArray riskIdJSONArray = requestJSON.getJSONArray("riskIDs");
            Plan plan = PlanDAO.getInstance().searchById(requestJSON.getInt("planPKID"));
            User user = UserDAO.getInstance().searchById(requestJSON.getInt("userID"));

            if (user == null) {
                throw new IllegalAccessError("No se encontró el usuario que intentó realizar la transacción.");
            }

            if (plan == null) {
                throw new NullPointerException("No se encontró el plan del cual desea agregar riesgos.");
            }

            if ((!user.hasRol("SUPER_ADMIN") && !user.hasRol("ADMIN") && !plan.containsInvolved(user)) || plan.getStatus().equals("Completo")) {
                throw new IllegalAccessError("Este usuario no cuenta con los permisos para realizar esta acción.");
            }

            if (riskIdJSONArray == null) {
                throw new NullPointerException("No se enviaron riesgos por agregar a este plan.");
            }
            List<Integer> riskIds = new ArrayList<>();
            for (int i = 0; i < riskIdJSONArray.length(); i++) {
                riskIds.add((Integer) riskIdJSONArray.get(i));
            }
            PlanDAO.getInstance().associateRisksToPlan(requestJSON.getInt("planPKID"), riskIds);

        } catch (NullPointerException e) {
            response.sendError(400, e.getMessage());
        } catch (IllegalAccessError e) {
            response.sendError(401, e.getMessage());
        } catch (Exception e) {
            response.sendError(500, e.getMessage());
        }
    }

    /**
     * @param request contains the JSON data that is sent by the client and
     * other useful information from the client request.
     * @param response sends the information back to the client with the
     * server's response. associateIncidenceToPlan updates the DB entries of
     * 'Plan' associating it to a list of 'Plan' entries sent by the client.
     */
    private void associateIncidenceToPlan(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            JSONObject requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));

            Plan plan = PlanDAO.getInstance().searchById(requestJSON.getInt("planID"));
            User user = UserDAO.getInstance().searchById(requestJSON.getInt("userID"));
            Risk risk = RiskDAO.getInstance().searchById(requestJSON.getInt("risk"));
            Date d = new Date(requestJSON.getLong("entryDate"));

            if (user == null) {
                throw new IllegalAccessError("No se encontró el usuario que intentó realizar la transacción.");
            }

            if (plan == null) {
                throw new NullPointerException("No se encontró el plan del cual desea agregar incidencias.");
            }

            if ((!user.hasRol("SUPER_ADMIN") && !user.hasRol("ADMIN") && !plan.containsInvolved(user)) || plan.getStatus().equals("Completo")) {
                throw new IllegalAccessError("Este usuario no cuenta con los permisos para realizar esta acción.");
            }

            Incidence newIncidence = new Incidence(requestJSON.getString("name"), requestJSON.getString("description"), d, requestJSON.getInt("affectation"), requestJSON.getString("cause"), risk);
            newIncidence.setEntryDate(d);

            plan.addIncidence(newIncidence);
            PlanDAO.getInstance().update(plan);

        } catch (NullPointerException e) {
            response.sendError(400, e.getMessage());
        } catch (IllegalAccessError e) {
            response.sendError(401, e.getMessage());
        } catch (Exception e) {
            response.sendError(500, e.getMessage());
        }

    }

    private void associateCommentToPlan(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            JSONObject requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));

            Plan plan = PlanDAO.getInstance().searchById(requestJSON.getInt("planID"));
            User user = UserDAO.getInstance().searchById(requestJSON.getInt("userID"));

            if (user == null) {
                throw new IllegalAccessError("No se encontró el usuario que intentó realizar la transacción.");
            }

            if (plan == null) {
                throw new NullPointerException("No se encontró el plan del cual desea agregar comentarios.");
            }

            if ((!user.hasRol("SUPER_ADMIN") && !user.hasRol("ADMIN") && !plan.containsInvolved(user)) || plan.getStatus().equals("Completo")) {
                throw new IllegalAccessError("Este usuario no cuenta con los permisos para realizar esta acción.");
            }

            Comment c = new Comment(requestJSON.getString("comentario"), (user.getOfficial().getName() + " " + user.getOfficial().getSurname()), requestJSON.getString("url"), new Date());
            plan.addComment(c);
            PlanDAO.getInstance().update(plan);
        } catch (NullPointerException e) {
            response.sendError(400, e.getMessage());
        } catch (IllegalAccessError e) {
            response.sendError(401, e.getMessage());
        } catch (Exception e) {
            response.sendError(500, e.getMessage());
        }
    }

    private void deleteCommentFromPlan(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            JSONObject requestJSON;
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
            Plan p = PlanDAO.getInstance().searchById(requestJSON.getInt("planPkID"));
            User user = UserDAO.getInstance().searchById(requestJSON.getInt("userID"));

            if (user == null) {
                throw new IllegalAccessError("No se encontró el usuario que intentó realizar la transacción.");
            }

            if (p == null) {
                throw new NullPointerException("No se encontró el plan del cual desea eliminar comentarios.");
            }

            if ((!user.hasRol("SUPER_ADMIN") && !user.hasRol("ADMIN") && !p.containsInvolved(user)) || p.getStatus().equals("Completo")) {
                throw new IllegalAccessError("Este usuario no cuenta con los permisos para realizar esta acción.");
            }

            List<Comment> commentList = p.getCommentList();
            if (commentList == null) {
                throw new NullPointerException("Este Plan no cuenta con comentarios para ser eliminados.");
            }
            commentList.removeIf(comment -> comment.getPkID() == requestJSON.getInt("commentID"));
            p.setCommentList(commentList);
            PlanDAO.getInstance().update(p);
        } catch (NullPointerException e) {
            response.sendError(400, e.getMessage());
        } catch (IllegalAccessError e) {
            response.sendError(401, e.getMessage());
        } catch (Exception e) {
            response.sendError(500, e.getMessage());
        }
    }

    /**
     * @param request contains the JSON data that is sent by the client and
     * other useful information from the client request.
     * @param response sends the information back to the client with the
     * server's response.
     */
    private void insertInvolved(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            JSONObject requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));

            Plan plan = PlanDAO.getInstance().searchById(requestJSON.getInt("planID"));
            JSONArray involvedIdJSONArray = requestJSON.getJSONArray("userIDs");
            User user = UserDAO.getInstance().searchById(requestJSON.getInt("userID"));

            if (user == null) {
                throw new IllegalAccessError("No se encontró el usuario que intentó realizar la transacción.");
            }

            if (plan == null) {
                throw new NullPointerException("No se encontró el plan del cual desea insertar involucrados.");
            }

            if ((!user.hasRol("SUPER_ADMIN") && !user.hasRol("ADMIN") && !plan.containsInvolved(user)) || plan.getStatus().equals("Completo")) {
                throw new IllegalAccessError("Este usuario no cuenta con los permisos para realizar esta acción.");
            }

            if (involvedIdJSONArray == null) {
                throw new NullPointerException("No se recibieron involucrados por agregar a este plan.");
            }
            for (int i = 0; i < involvedIdJSONArray.length(); i++) {
                User u = UserDAO.getInstance().searchById((Integer) involvedIdJSONArray.get(i));
                if (u != null) {
                    plan.addInvolucrado(u);
                }
            }
            PlanDAO.getInstance().update(plan);

        } catch (NullPointerException e) {
            response.sendError(400, e.getMessage());
        } catch (IllegalAccessError e) {
            response.sendError(401, e.getMessage());
        } catch (Exception e) {
            response.sendError(500, e.getMessage());
        }
    }

    private void deleteInvolved(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            JSONObject requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
            User involved = UserDAO.getInstance().searchById(requestJSON.getInt("involvedID"));
            Plan plan = PlanDAO.getInstance().searchById(requestJSON.getInt("planID"));
            User user = UserDAO.getInstance().searchById(requestJSON.getInt("userID"));

            if (user == null) {
                throw new IllegalAccessError("No se encontró el usuario que intentó realizar la transacción.");
            }

            if (plan == null) {
                throw new NullPointerException("No se encontró el plan del cual desea eliminar involucrados.");
            }

            if ((!user.hasRol("SUPER_ADMIN") && !user.hasRol("ADMIN") && !plan.containsInvolved(user)) || plan.getStatus().equals("Completo")) {
                throw new IllegalAccessError("Este usuario no cuenta con los permisos para realizar esta acción.");
            }

            plan.removeInvolucrado(involved);
            PlanDAO.getInstance().update(plan);

        } catch (NullPointerException e) {
            response.sendError(400, e.getMessage());
        } catch (IllegalAccessError e) {
            response.sendError(401, e.getMessage());
        } catch (Exception e) {
            response.sendError(500, e.getMessage());
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(PlanManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(PlanManager.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(PlanManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(PlanManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(PlanManager.class.getName()).log(Level.SEVERE, null, ex);
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
