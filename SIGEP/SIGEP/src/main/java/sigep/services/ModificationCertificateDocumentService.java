package sigep.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ModificationCertificateDocumentService", urlPatterns = {"/ModificationCertificateDocumentService"})
public class ModificationCertificateDocumentService extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/pdf;charset=UTF-8");

        try {
            String path = "C:\\upload\\modificaciones-presupuestarias\\";
            String pdfFileName = request.getParameter("nombre-mc") + ".pdf";
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
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BiddingActDocumentService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BiddingActDocumentService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
