/**
 *
 * @author ArnoldG6
 * PlanDAO class is responsible of establishing connection with Hibernate framework
 * in order to cast Java objects from HQL queries.
 */
package sfr.dao;

import common.dao.UserDAO;
import common.model.User;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import javax.persistence.Query;
import sfr.model.Comment;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import static org.eclipse.persistence.jpa.jpql.JPAVersion.value;
import sfr.model.Incidence;
import sfr.model.Plan;
import sfr.model.Risk;

public class PlanDAO extends GenericDAO {

    private static PlanDAO uniqueInstance; //Singleton Pattern Object

    /**
     * @return the Singleton Pattern Object of PlanDAO class.
     */
    public static PlanDAO getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new PlanDAO();
        }
        return uniqueInstance;

    }

    /**
     * @return a translated column name into an attribute's name (of Plan class)
     * in order to use Hibernate's queries defined in this file.
     * @param column specifies the desired column name to check if it can be
     * translated.
     * @param order specifies the 'ascendent' or 'descendent' order keyword to
     * be translated.
     * @throws java.io.IOException
     */
    public String translateColumnName(String column, String order) throws IOException {
        order = order.toUpperCase();
        if (!(order.equals("ASC") || order.equals("DESC"))) {
            throw new IOException("Invalid order parameter");
        }
        switch (column.toUpperCase()) {
            case "PK_ID":
                return "pkID";
            case "ID":
                return "id";
            case "NAME":
                return "name";
            case "DESCRIPTION":
                return "description";
            case "AUTHORNAME":
                return "authorName";
            case "ENTRYDATE":
                return "entryDate";
            case "STATUS":
                return "status";
            case "TYPE":
                return "type";
            case "SUBTYPE":
                return "subtype";
            default:
                throw new IOException("Invalid column");
        }
    }

    /**
     * @return a sorted list of Plan objects depending on the next parameters:
     * @param column specifies the sorting criteria from the selected column.
     * Default value for this project is: "ENTRYDATE".
     * @param order specifies the 'ascendent' or 'descendent' sorting criteria.
     * Default value for this project is: "DESC".
     * @throws java.lang.Exception
     */
    public List<Plan> listByColumn(String column, String order) throws Exception {
        try {
            order = order.toUpperCase();
            column = this.translateColumnName(column, order);
            String cmd = new StringBuilder().append("SELECT p from Plan p order by p.")
                    .append(column).append(" ").append(order).toString();
            em = getEntityManager();
            Query query = em.createQuery(cmd);
            return (List<Plan>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.err.println(e.getMessage());
            throw e;
        }
    }

    public String generateRiskTableXLSXFileName(Plan p) throws IOException {
        if (p == null) {
            throw new IOException("Invalid parameter p");
        }
        return new StringBuilder().append("Matriz_de_riesgos_").append(p.getId()).append("_").append(Timestamp.from(Instant.now()).toString().
                replace(":", "-").replace("/", "-")).append(".xlsx").toString();
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
        setBordersToMergedCells(worksheet, m1, "MEDIUM");
        //----2nd title----
        worksheet.createRow(1).createCell(1).setCellValue("MATRIZ DE IDENTIFICACIÓN DEL RIESGO");
        CellRangeAddress m2 = new CellRangeAddress(1, 1, 1, 15);
        worksheet.addMergedRegion(m2);
        setBordersToMergedCells(worksheet, m2, "MEDIUM");
        //----3rd row (2 titles)----
        XSSFRow thirdRow = worksheet.createRow(2);
        thirdRow.createCell(1).setCellValue("ESTRUCTURA DE RIESGOS");
        CellRangeAddress m3 = new CellRangeAddress(2, 2, 1, 5);
        worksheet.addMergedRegion(m3);
        setBordersToMergedCells(worksheet, m3, "MEDIUM");
        thirdRow.createCell(6).setCellValue("IDENTIFICACION DEL RIESGO");
        CellRangeAddress m4 = new CellRangeAddress(2, 2, 6, 9);
        worksheet.addMergedRegion(m4);
        setBordersToMergedCells(worksheet, m4, "MEDIUM");
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
        setBordersToMergedCells(worksheet, new CellRangeAddress(3, 3, 1, 9), "MEDIUM");
        //Returns the workbook with no risk info if it is the case.
        if (p.getRiskList() == null || p.getRiskList().isEmpty()) {
            return workbook;
        }
        //----5th row(s)+, column data----
        Integer rowCount = 4;
        XSSFRow dataRow;
        for (Risk r : p.getRiskList()) {
            dataRow = worksheet.createRow(rowCount);
            //dataRow.createCell(1).setCellValue("NIVEL 1");
            //dataRow.createCell(2).setCellValue("NIVEL 2");
            //dataRow.createCell(3).setCellValue("NIVEL 3");
            //dataRow.createCell(4).setCellValue("PROCESO");
            //dataRow.createCell(5).setCellValue("FALTA EL CAMPO EN LA DB");
            dataRow.createCell(6).setCellValue(r.getId() + " - " + r.getName());
            dataRow.createCell(7).setCellValue(r.getDescription());
            dataRow.createCell(8).setCellValue(r.getFactors());
            dataRow.createCell(9).setCellValue(r.getConsequences());
            rowCount += 1;
        }

        //XSSFCellStyle cellStyle = workbook.createCellStyle();
        //cellA1.setCellStyle(cellStyle);
        setBordersToMergedCells(worksheet, new CellRangeAddress(4, 4, 1, 15), "THIN");
        for (int i = 0; i<16; i++)
             worksheet.autoSizeColumn(i,true);
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

    /**
     * @return a casted HashMap of Plan objects from an HQL query sorted in
     * descending order by entryDate.
     * @throws java.lang.Exception
     */
    public HashMap<String, Plan> listAllHM() throws Exception {
        HashMap<String, Plan> plans = new HashMap<>();
        List<Plan> plansList = this.listByColumn("ENTRYDATE", "DESC");
        plansList.forEach(p -> {
            plans.put(p.getId(), p);
        });
        return plans;
    }

    /**
     * This method adds a Plan object into the DB record, using HQL.
     *
     * @param plan is the Plan Object to be added.
     */
    public void add(Plan plan) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(plan);
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
        } finally {
            closeEntityManager();
        }
    }

    /**
     * This method updates a Plan object into the DB record, using HQL.
     *
     * @param plan is the Plan Object to be updated.
     */
    public void update(Plan plan) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(plan);
            em.getTransaction().commit();

        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
        } finally {
            closeEntityManager();
        }
    }

    /**
     * This method deletes a Plan object into the DB record, using HQL.
     *
     * @param plan is the Plan Object to be deleted.
     */
    public void delete(Plan plan) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.remove(em.merge(plan));
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
        } finally {
            closeEntityManager();
        }
    }

    /**
     * This method associates a single Plan object to n Risk objects.
     *
     * @param planID the Plan object that will be associated to the Risk objects
     * @param riskIDs List of riskIDs to associate with planID parameter.
     * @throws java.lang.Exception
     */
    public void associateRisksToPlan(int planID, List<Integer> riskIDs) throws Exception {
        try {
            if (riskIDs == null) {
                throw new IOException("Invalid RiskIDs field");
            }
            Plan p = PlanDAO.getInstance().searchById(planID);
            if (p == null) {
                throw new IOException("Invalid planID field");
            }
            List<Risk> riskList = p.getRiskList();
            if (riskList == null) {
                throw new IOException("Empty riskList exception");
            }
            if (riskIDs.isEmpty()) {
                throw new IOException("Empty riskIDs field exception");
            }
            Risk r;
            for (int i = 0; i < riskIDs.size(); i++) {
                r = RiskDAO.getInstance().searchById(riskIDs.get(i));
                if (!riskList.contains(r)) {
                    riskList.add(r);
                } else {
                    throw new IOException("This plan already contains this risk");
                }
            }
            p.setRiskList(riskList);
            PlanDAO.getInstance().update(p);

        } catch (IOException ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
            throw ex;
        } finally {
            closeEntityManager();
        }
    }

    /**
     * This method associates a single Plan object to n Incidence objects.
     *
     * @param planID the Plan object that will be associated to the Incidences
     * objects
     * @param incidenceIDs List of riskIDs to associate with planID parameter.
     * @throws java.lang.Exception
     */
    public void associateIncidencesToPlan(int planID, List<Integer> incidenceIDs) throws Exception {
        try {
            if (incidenceIDs == null) {
                throw new IOException("Invalid IncidenceIDs field");
            }
            Plan p = PlanDAO.getInstance().searchById(planID);
            if (p == null) {
                throw new IOException("Invalid planID field");
            }
            List<Incidence> incidenceList = p.getIncidenceList();
            if (incidenceList == null) {
                throw new IOException("Empty incidenceList exception");
            }
            if (incidenceIDs.isEmpty()) {
                throw new IOException("Empty incidenceIDs field exception");
            }
            Incidence ins;
            for (int i = 0; i < incidenceIDs.size(); i++) {
                ins = IncidenceDAO.getInstance().searchById(incidenceIDs.get(i));
                if (!incidenceList.contains(ins)) {
                    incidenceList.add(ins);
                } else {
                    throw new IOException("This plan already contains this incidence");
                }
            }
            p.setIncidenceList(incidenceList);
            PlanDAO.getInstance().update(p);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
            throw ex;
        } finally {
            closeEntityManager();
        }
    }

    /**
     * This method associates a single Plan object to n Comment objects.
     *
     * @param planID the Plan object that will be associated to the Comments
     * objects
     * @param commentIDs List of commentIDs to associate with planID parameter.
     * @throws java.lang.Exception
     */
    public void associateCommentsToPlan(int planID, List<Integer> commentIDs) throws Exception {
        try {
            if (commentIDs == null) {
                throw new IOException("Invalid commentIDs field");
            }
            Plan p = PlanDAO.getInstance().searchById(planID);
            if (p == null) {
                throw new IOException("Invalid planID field");
            }
            List<Comment> commentList = p.getCommentList();
            if (commentList == null) {
                throw new IOException("Empty commentList exception");
            }
            if (commentIDs.isEmpty()) {
                throw new IOException("Empty commentIDs field exception");
            }
            Comment com;
            for (int i = 0; i < commentIDs.size(); i++) {
                com = CommentDAO.getInstance().searchById(commentIDs.get(i));
                if (!commentList.contains(com)) {
                    commentList.add(com);
                } else {
                    throw new IOException("This plan already contains this comment");
                }
            }
            p.setCommentList(commentList);
            PlanDAO.getInstance().update(p);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
            throw ex;
        } finally {
            closeEntityManager();
        }
    }

    /**
     *
     * @return a list of risks, including all risks, except for the ones in the
     * Plan identified by
     * @param planID
     * @throws java.lang.Exception
     */
    public List<Risk> getRiskListByPlanNoRep(String planID) throws Exception {
        try {
            Plan p = PlanDAO.getInstance().searchByIdString(planID);
            List<Risk> pRiskList = p.getRiskList(); //risks of an specific Plan.
            List<Risk> riskList = RiskDAO.getInstance().listByColumn("PK_ID", "DESC");
            if (pRiskList == null || riskList == null) {
                throw new IOException("Empty riskList exception");
            }
            for (int i = 0; i < pRiskList.size(); i++) {
                if (riskList.contains(pRiskList.get(i))) {
                    riskList.remove(pRiskList.get(i));
                }
            }
            return riskList;
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
            throw ex;
        } finally {
            closeEntityManager();
        }
    }

    /**
     *
     * @return a list of users, including all users, except for the ones in the
     * Plan identified by
     * @param planID
     * @throws java.lang.Exception
     */
    public List<User> getUserListByPlanNoRep(String planID) throws Exception {
        try {
            Plan p = PlanDAO.getInstance().searchByIdString(planID);
            List<User> pUserList = p.getInvolvedList(); //users of an specific Plan.
            List<User> userList = UserDAO.getInstance().listAll();
            if (pUserList == null || userList == null) {
                throw new IOException("Empty userList exception");
            }
            for (int i = 0; i < pUserList.size(); i++) {
                if (userList.contains(pUserList.get(i))) {
                    userList.remove(pUserList.get(i));
                }
            }
            return userList;
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
            throw ex;
        } finally {
            closeEntityManager();
        }
    }

    /**
     *
     * @return a list of incidences, including all incidences, except for the
     * ones in the Plan identified by
     * @param planID
     * @throws java.lang.Exception
     */
    public List<Incidence> getIncidenceListByPlanNoRep(String planID) throws Exception {
        try {
            Plan p = PlanDAO.getInstance().searchByIdString(planID);
            List<Incidence> pIncidenceList = p.getIncidenceList(); //incidences of an specific Plan.
            List<Incidence> incidenceList = IncidenceDAO.getInstance().listByColumn("PK_ID", "DESC");
            if (pIncidenceList == null || incidenceList == null) {
                throw new IOException("Empty riskList exception");
            }
            for (int i = 0; i < pIncidenceList.size(); i++) {
                if (incidenceList.contains(pIncidenceList.get(i))) {
                    incidenceList.remove(pIncidenceList.get(i));
                }
            }
            return incidenceList;
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
            throw ex;
        } finally {
            closeEntityManager();
        }
    }

    /**
     *
     * @return a list of comments, including all comments, except for the ones
     * in the Plan identified by
     * @param planID
     * @throws java.lang.Exception
     */
    public List<Comment> getCommentListByPlanNoRep(String planID) throws Exception {
        try {
            Plan p = PlanDAO.getInstance().searchByIdString(planID);
            List<Comment> pCommentList = p.getCommentList(); //comments of an specific Plan.
            List<Comment> commentList = CommentDAO.getInstance().listByColumn("PK_ID", "DESC");
            if (pCommentList == null || commentList == null) {
                throw new IOException("Empty commentList exception");
            }
            for (int i = 0; i < pCommentList.size(); i++) {
                if (commentList.contains(pCommentList.get(i))) {
                    commentList.remove(pCommentList.get(i));
                }
            }
            return commentList;
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
            throw ex;
        } finally {
            closeEntityManager();
        }
    }

    /**
     *
     * @return a Plan object that matches with
     * @param id
     */
    public Plan searchById(int id) {
        em = getEntityManager();
        return (Plan) em.find(Plan.class, id);
    }

    /**
     *
     * @return a Plan object that matches with
     * @param id
     */
    public Plan searchByIdString(String id) {
        try {
            HashMap<String, Plan> planes = this.listAllHM();
            return planes.get(id);
        } catch (Exception e) {

        }
        return null;
    }

    /**
     *
     *
     * @return a Plan object list which contains matched attribute toString()
     * values with
     * @param value
     * @throws java.lang.Exception
     */
    public List<Plan> searchInAllColumns(String value) throws Exception {
        try {
            HashMap<String, Plan> resultHM = this.listAllHM();
            SimpleDateFormat dateFor = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            Pattern p = Pattern.compile(new StringBuilder().append("(.*)").append(value).append("(.*)").toString(),
                    Pattern.CASE_INSENSITIVE);
            ArrayList<Plan> result = new ArrayList<>();
            for (HashMap.Entry<String, Plan> plan : resultHM.entrySet()) {
                Plan pl = plan.getValue();

                //
                if (p.matcher(pl.getId()).find() || p.matcher(pl.getAuthorName()).find()
                        || p.matcher(pl.getName()).find() || p.matcher(pl.getDesc()).find()
                        || p.matcher(dateFor.format(pl.getEntryDate())).find() || p.matcher(pl.getStatus()).find()
                        || p.matcher(String.format("%tB", pl.getEntryDate())).find()
                        || p.matcher(pl.getType()).find()) {
                    result.add(pl);
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.err.println(e.getMessage());
            throw e;
        }
    }

    public List<Risk> searchInRiskListNonRep(String planID, String value) throws Exception {
        try {
            Plan pl = PlanDAO.getInstance().searchByIdString(planID);
            List<Risk> pRiskList = this.getRiskListByPlanNoRep(planID); //risks of an specific Plan.
            ArrayList<Risk> result = new ArrayList<>();
            if (pl == null) {
                throw new IOException("Invalid PlanID exception");
            }
            if (pRiskList == null) {
                throw new IOException("Empty riskList exception");
            }
            Pattern p = Pattern.compile(value, Pattern.CASE_INSENSITIVE);
            for (Risk r : pRiskList) {
                if (p.matcher(String.valueOf(r.getId())).find()
                        || p.matcher(r.getName()).find()
                        || p.matcher(r.getFactors()).find()
                        || p.matcher(r.getGeneralType()).find()
                        || p.matcher(r.getAreaType()).find()
                        || p.matcher(r.getDescription()).find()
                        || p.matcher(String.valueOf(r.getProbability())).find()
                        || p.matcher(String.valueOf(r.getImpact())).find()
                        || p.matcher(String.valueOf(r.getMagnitude())).find()
                        || p.matcher(String.valueOf(r.getAuthor().toString())).find() 
                        || p.matcher(r.getMitigationMeasures()).find()) {
                    result.add(r);
                }
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
            throw ex;
        } finally {
            closeEntityManager();
        }
    }

    public List<User> searchInUserListNonRep(String planID, String value) throws Exception {
        try {
            Plan pl = PlanDAO.getInstance().searchByIdString(planID);
            if (pl == null) {
                throw new IOException("Invalid PlanID exception");
            }
            List<User> pInvilvedList = this.getUserListByPlanNoRep(planID); //user of an specific Plan.
            ArrayList<User> result = new ArrayList<>();
            if (pInvilvedList == null) {
                throw new IOException("Empty riskList exception");
            }
            Pattern p = Pattern.compile(value, Pattern.CASE_INSENSITIVE);
            for (User r : pInvilvedList) {
                if (p.matcher(String.valueOf(r.getOfficial().getIdOfficial())).find()
                        || p.matcher(r.getOfficial().getName()).find()
                        || p.matcher(r.getOfficial().getSurname()).find()
                        || p.matcher(r.getOfficial().getEmail()).find()
                        || p.matcher(String.valueOf(r.getOfficial().getDepartment().getIdDepartment())).find()) {
                    result.add(r);
                }
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
            throw ex;
        } finally {
            closeEntityManager();
        }
    }
}
