package sigep.services;

import common.model.User;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sigep.dao.BudgetDAO;
import sigep.model.Budget;

@WebServlet(name = "XLSCostCenterRegisterService", urlPatterns = {"/XLSCostCenterRegisterService"})
@MultipartConfig()
public class XLSCostCenterRegisterService extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;
        try {

            out = response.getWriter();
            User currentUser = (User) request.getSession(true).getAttribute("user");

            if (!Objects.isNull(currentUser)) {

                if (User.validateSuperAdminRol(currentUser) || User.validateBudgetAdminRol(currentUser)) {

                    Part part = request.getPart("excel");

                    List<Budget> budgetItems = new ArrayList();
                    try (InputStream filecontent = part.getInputStream()) {

                        XSSFWorkbook workbook = new XSSFWorkbook(filecontent);
                        XSSFSheet sheet = workbook.getSheetAt(1);
                        Iterator<org.apache.poi.ss.usermodel.Row> rowIterator = sheet.iterator();

                        if (rowIterator.hasNext()) {
                            rowIterator.next();
                        }

                        while (rowIterator.hasNext()) {
                            Row row = rowIterator.next();

                            if (!Objects.isNull(row)) {

                                if (!Objects.isNull(row.getCell(0)) && !Objects.isNull(row.getCell(1)) && !Objects.isNull(row.getCell(2))) {

                                    Budget item = new Budget();

                                    item.setIdItem(row.getCell(0).getStringCellValue());

                                    item.setDescription(row.getCell(1).getStringCellValue());

                                    row.getCell(2).setCellType(CellType.STRING);

                                    double amount = 0;
                                    if (!row.getCell(2).getRichStringCellValue().toString().isEmpty()) {

                                        try {
                                            amount = Double.parseDouble(row.getCell(2).getRichStringCellValue().toString());
                                        } catch (NumberFormatException ex) {
                                            amount = 0;
                                        }
                                    }
                                    
                                    int receives = (int) row.getCell(3).getNumericCellValue();

                                    item.setOrdinaryAmount(amount);
                                    item.setAvailableAmount(amount);
                                    item.setExtraordinaryAmount(0);
                                    item.setBlockedAmount(0);
                                    item.setPettyCash(0);
                                    item.setTenders(0);
                                    item.setIncreased(0);
                                    item.setDecreased(0);
                                    item.setReceives(receives == 1);

                                    budgetItems.add(item);

                                }
                            }
                        }
                    }

                    addBudgetItems(budgetItems);
                } else {
                    response.sendRedirect("/home/sigep/common/invaliduser.jsp");
                }

            } else {
                response.sendRedirect("/home/sigep/common/nouser.jsp");
            }

        } catch (IOException ex) {
            try {
                out = response.getWriter();
                Logger.getLogger(NewCostCenterService.class.getName()).log(Level.SEVERE, null, ex);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("Ocurrió un error interno, contacte al departamento de TI.");
                out.flush();
            } catch (IOException ex1) {
                Logger.getLogger(NewCostCenterService.class.getName()).log(Level.SEVERE, null, ex1);
            } finally {
                out.close();
            }
        } catch (ServletException ex) {
            Logger.getLogger(XLSCostCenterRegisterService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void addBudgetItems(List<Budget> list) throws IllegalArgumentException {

        list.forEach((Budget item) -> {
            Budget alreadyAdded = BudgetDAO.getInstance().searchById(item);
            
            if (Objects.isNull(alreadyAdded)) {
                
                String idFather = "";
                
                String[] parts = item.getIdItem().split("\\.");
                
                if (parts.length > 1) {
                    for (int i = 0; i < parts.length - 1; i++) {
                        
                        if (i < parts.length - 2) {
                            idFather += parts[i] + ".";
                        } else {
                            idFather += parts[i];
                        }
                    }
                    
                    Budget father = new Budget(idFather);
                    father = BudgetDAO.getInstance().searchById(father);
                    
                    if (!Objects.isNull(father)) {
                        item.setFather(father);
                    } else {
                        throw new IllegalArgumentException();
                    }
                    
                }
                
                BudgetDAO.getInstance().insert(item);
                BudgetDAO.getInstance().getItems().put(item.getIdItem(), item);
                
            }
        });
    }

}
