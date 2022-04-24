/**
 * @author GONCAR4
 * @author ArnoldG6 PlanServlet class is in charge of handling requests from the
 * clients in order to search or retrieve 'Plan' entries from the DB.
 */
package plan.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ex.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.apache.poi.sl.usermodel.PaintStyle.GradientPaint.GradientType.shape;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.json.JSONObject;
import sfr.dao.PlanDAO;
import sfr.dao.PlanTypeDAO;
import sfr.model.Plan;
import sfr.model.Risk;

@WebServlet(name = "PlanServlet", urlPatterns = {
    "/API/PlanServlet/Retrieve/Planes",
    "/API/PlanServlet/Retrieve/Plan",
    "/API/PlanServlet/Retrieve/Plan/RemainingRisks",
    "/API/PlanServlet/Retrieve/Plan/RemainingUsers",
    "/API/PlanServlet/Retrieve/PlanTypes",
    "/API/PlanServlet/Search"
})
public class PlanServlet extends HttpServlet {

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
                case "/API/PlanServlet/Retrieve/Planes":
                    retrievePlans(request, response);
                    break;
                case "/API/PlanServlet/Retrieve/Plan":
                    searchPlanByID(request, response);
                    break;
                case "/API/PlanServlet/Retrieve/Plan/RemainingRisks":
                    retrievePlanRiskList(request, response);
                    break;
                case "/API/PlanServlet/Retrieve/Plan/RemainingIncidences":
                    retrievePlanIncidenceList(request, response);
                    break;
                case "/API/PlanServlet/Retrieve/Plan/RemainingUsers":
                    retrievePlanUserList(request, response);
                    break;
                case "/API/PlanServlet/Retrieve/PlanTypes":
                    retrievePlanTypes(request, response);
                    break;
                case "/API/PlanServlet/Search":
                    searchPlans(request, response);
                    break;
                case "/API/PlanServlet/riskTable":
                    generateRiskTable(request, response);
                    break;

            }
        } catch (Exception ex) {
            System.err.println(ex);
            Logger.getLogger(PlanServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    private void generateRiskTable(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        //JSONObject requestJSON;
        //requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
        //Integer planID = Integer.parseInt(requestJSON.getString("planID"));
        Plan p = PlanDAO.getInstance().searchById(1);
        System.out.println(p.toString());
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=" + PlanDAO.getInstance().generateRiskTableXLSXFileName(p));
        //PlanDAO.getInstance().generateRiskTableXLSXFile(p).write(response.getOutputStream());
        this.generateRiskTableXLSXFile(p).write(response.getOutputStream());
        response.getWriter().flush();
        response.getWriter().close();
    }

    public XSSFWorkbook generateRiskTableXLSXFile(Plan p) throws IOException {
        if (p == null) {
            throw new IOException("Invalid parameter p");
        }
        XSSFWorkbook workbook = new XSSFWorkbook();
        //----1st title----
        XSSFSheet worksheet = workbook.createSheet(p.getId());
        worksheet.setVerticallyCenter(true);
        worksheet.setHorizontallyCenter(true);
        worksheet.createRow(0).createCell(1).setCellValue("MUNICIPALIDAD DE SAN PABLO DE HEREDIA");
        CellRangeAddress m1 = new CellRangeAddress(0, 0, 1, 15);
        worksheet.addMergedRegion(m1);
        setBordersToMergedCells(worksheet, m1,"MEDIUM");
        //----2nd title----
        worksheet.createRow(1).createCell(1).setCellValue("MATRIZ DE IDENTIFICACIÓN DEL RIESGO");
        CellRangeAddress m2 = new CellRangeAddress(1, 1, 1, 15);
        worksheet.addMergedRegion(m2);
        setBordersToMergedCells(worksheet, m2,"MEDIUM");
        //----3rd row (2 titles)----
        XSSFRow thirdRow = worksheet.createRow(2);
        thirdRow.createCell(1).setCellValue("ESTRUCTURA DE RIESGOS");
        CellRangeAddress m3 = new CellRangeAddress(2, 2, 1, 5);
        worksheet.addMergedRegion(m3);
        setBordersToMergedCells(worksheet, m3,"MEDIUM");
        thirdRow.createCell(6).setCellValue("IDENTIFICACION DEL RIESGO");
        CellRangeAddress m4 = new CellRangeAddress(2, 2, 6, 9);
        worksheet.addMergedRegion(m4);
        setBordersToMergedCells(worksheet, m4,"MEDIUM");
        //----4th row, column headers----
        XSSFRow fourthRow = worksheet.createRow(3);
        fourthRow.createCell(1).setCellValue("NIVEL 1");
        fourthRow.createCell(2).setCellValue("NIVEL 2");
        fourthRow.createCell(3).setCellValue("NIVEL 3");
        fourthRow.createCell(4).setCellValue("PROCESO");
        fourthRow.createCell(5).setCellValue("OBJETIVO");
        fourthRow.createCell(6).setCellValue("RIESGO");
        fourthRow.createCell(7).setCellValue("DESCRIPCIÓN DEL RIESGO");
        fourthRow.createCell(8).setCellValue("FACTOR DEL RIESGO (CAUSA)");
        fourthRow.createCell(9).setCellValue("CONSECUENCIA");
        setBordersToMergedCells(worksheet,new CellRangeAddress(3, 3, 1, 9) ,"MEDIUM");
     
        //Returns the workbook with no risk info if it is the case.
        if (p.getRiskList() == null || p.getRiskList().isEmpty()) {
            return workbook;
        }
        //----5th row(s)+, column data----
        Integer rowCount = 4;
        XSSFRow dataRow;
        for(Risk r : p.getRiskList() ){
            dataRow = worksheet.createRow(rowCount);
            //dataRow.createCell(1).setCellValue("NIVEL 1");
            //dataRow.createCell(2).setCellValue("NIVEL 2");
            //dataRow.createCell(3).setCellValue("NIVEL 3");
            //dataRow.createCell(4).setCellValue("PROCESO");
            dataRow.createCell(5).setCellValue("FALTA EL CAMPO EN LA DB");
            dataRow.createCell(6).setCellValue(r.getId() + " " + r.getName());
            dataRow.createCell(7).setCellValue("FALTA EL CAMPO EN LA DB");
            dataRow.createCell(8).setCellValue(r.getFactors());
            dataRow.createCell(9).setCellValue("FALTA EL CAMPO EN LA DB");
            rowCount += 1;
        }
        
        //XSSFCellStyle cellStyle = workbook.createCellStyle();
        //cellA1.setCellStyle(cellStyle);
        setBordersToMergedCells(worksheet,new CellRangeAddress(4, 4, 1, 15),"THIN");
        return workbook;
    }

    protected void setBordersToMergedCells(Sheet sheet, CellRangeAddress rangeAddress, String style) throws IOException {
        if (style == null) {
            throw new IOException("Invalid parameter 'style'.");
        }
        switch (style) {
            case "MEDIUM":
                RegionUtil.setBorderTop(BorderStyle.MEDIUM, rangeAddress, sheet);
                RegionUtil.setBorderLeft(BorderStyle.MEDIUM, rangeAddress, sheet);
                RegionUtil.setBorderRight(BorderStyle.MEDIUM, rangeAddress, sheet);
                RegionUtil.setBorderBottom(BorderStyle.MEDIUM, rangeAddress, sheet);
                break;
            case "THIN":
                RegionUtil.setBorderTop(BorderStyle.THIN, rangeAddress, sheet);
                RegionUtil.setBorderLeft(BorderStyle.THIN, rangeAddress, sheet);
                RegionUtil.setBorderRight(BorderStyle.THIN, rangeAddress, sheet);
                RegionUtil.setBorderBottom(BorderStyle.THIN, rangeAddress, sheet);
                break;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Plan Management methods.">
    /**
     * @param request contains the JSON data that is sent by the client and
     * other useful information from the client request.
     * @param response sends the information back to the client with the
     * server's response. searchPlans method answers to the client request with
     * a JSON formatted List<Plan> object, determined by a search in all 'Plan'
     * class attributes.
     */
    private void searchPlans(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        String responseJSON;
        JSONObject requestJSON;
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
        responseJSON = new Gson().toJson(PlanDAO.getInstance().searchInAllColumns(requestJSON.getString("searchPlan")));
        if (responseJSON == null) {
            //Custom exception
            response.getWriter().write(new InvalidPlanListIDEx().jsonify());
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
     * server's response. retrievePlans method answers to the client request
     * with a JSON formatted List<Plan> object, determined by a sorting
     * criteria.
     */
    private void retrievePlans(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        String responseJSON;
        JSONObject requestJSON;
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
        responseJSON = new Gson().toJson(PlanDAO.getInstance().
                listByColumn(requestJSON.getString("sortingValue"), requestJSON.getString("sortingWay")));
        if (responseJSON == null) {
            //Custom exception
            response.getWriter().write(new PlansNotListedEx().jsonify());
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
     * server's response. searchPlanByID method answers to the client request
     * with a JSON formatted Plan object searched by an specific ID.
     */
    private void searchPlanByID(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        String responseJSON;
        JSONObject requestJSON;
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
        responseJSON = new Gson().toJson(PlanDAO.getInstance().searchByIdString(requestJSON.getString("planID")));
        if (responseJSON == null) {
            //Custom exception
            response.getWriter().write(new PlansNotListedEx().jsonify());
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
     * server's response. getRiskListByPlanNoRep answers to the client with a
     * List<Risk> object formatted as JSON which contains the complement of the
     * List<Risk> of the user that sent the request.
     */
    private void retrievePlanRiskList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        String responseJSON;
        JSONObject requestJSON;
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
        responseJSON = new Gson().toJson(PlanDAO.getInstance().getRiskListByPlanNoRep(requestJSON.getString("planID")));
        if (responseJSON == null) {
            //Custom exception
            response.getWriter().write(new EmptyRiskListEx().jsonify());
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
     * server's response. getIncidenceListByPlanNoRep answers to the client with
     * a List<Incidence> object formatted as JSON which contains the complement
     * of the List<Incidence> of the user that sent the request.
     */
    private void retrievePlanIncidenceList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        String responseJSON;
        JSONObject requestJSON;
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
        responseJSON = new Gson().toJson(PlanDAO.getInstance().getIncidenceListByPlanNoRep(requestJSON.getString("planID")));
        if (responseJSON == null) {
            //Custom exception
//            response.getWriter().write(new EmptyIncidenceListEx().jsonify());
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
     * server's response. retrievePlanUserList answers to the client with a
     * List<User> object formatted as JSON which contains the complement of the
     * List<User> of the user that sent the request.
     */
    private void retrievePlanUserList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        String responseJSON;
        //JSONObject requestJSON;
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONObject requestJSON = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
        responseJSON = new Gson().toJson(PlanDAO.getInstance().getUserListByPlanNoRep(requestJSON.getString("planID")));
        if (responseJSON == null) {
            //Custom exception
//            response.getWriter().write(new EmptyIncidenceListEx().jsonify());
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
     * server's response. retrievePlanTypes method answers to the client request
     * with a JSON formatted Map<String,List<PlanType>> object
     */
    private void retrievePlanTypes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        String responseJSON;
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson g = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        responseJSON = g.toJson(PlanTypeDAO.getInstance().listAllPlanTypeHM());
        if (responseJSON == null) {
            //Custom exception
            response.getWriter().write(new PlanTypesEx().jsonify());
        } else {
            response.getWriter().write(responseJSON);
        }
        response.getWriter().flush();
        response.getWriter().close();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(PlanServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(PlanServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            //processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(PlanServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(PlanServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(PlanServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
