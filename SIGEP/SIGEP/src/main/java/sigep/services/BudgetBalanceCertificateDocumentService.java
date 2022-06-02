/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sigep.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "BudgetBalanceCertificateDocumentService", urlPatterns = {"/BudgetBalanceCertificateDocumentService"})
public class BudgetBalanceCertificateDocumentService extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;
        try {
            String path = "C:\\upload\\certificados-saldo\\";
            String pdfFileName = request.getParameter("nombre-csp") + ".pdf";
            File pdfFile = new File(path + pdfFileName);

            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "attachment; filename=" + pdfFileName);
            response.setContentLength((int) pdfFile.length());

            FileInputStream fileInputStream;

            fileInputStream = new FileInputStream(pdfFile);

            OutputStream responseOutputStream = response.getOutputStream();
            int bytes;
            while ((bytes = fileInputStream.read()) != -1) {
                responseOutputStream.write(bytes);
            }
        } catch (IOException ex) {
            try {
                out = response.getWriter();
                Logger.getLogger(BudgetBalanceCertificateDocumentService.class.getName()).log(Level.SEVERE, null, ex);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("Ocurrió un error interno, contacte al departamento de TI.");
                out.flush();
            } catch (IOException ex1) {
                Logger.getLogger(BudgetBalanceCertificateDocumentService.class.getName()).log(Level.SEVERE, null, ex1);
            } finally {
                out.close();
            }
        }

    }

}
