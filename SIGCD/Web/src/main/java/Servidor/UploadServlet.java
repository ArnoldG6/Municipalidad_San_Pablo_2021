package Servidor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@MultipartConfig
public class UploadServlet extends HttpServlet {

    Connection con = null;
    PreparedStatement ps = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        if (ServletFileUpload.isMultipartContent(request)) {
            try (PrintWriter out = response.getWriter()) {
                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                        String name = new File(item.getName()).getName();
                        String upload = "C:/Users/sebas/Desktop/files" + File.separator + name;
                        item.write(new File(upload));
                        Timestamp added_date = new Timestamp(System.currentTimeMillis());
                        try {
                            con = Logica.Data.Database.instance().getConnection();

                            System.out.println("connection done");
                            String sql = "insert into employee(firstname,lastname,filename,path,added_date) values(?,?,?,?,?)";
                            ps = con.prepareStatement(sql);
                            ps.setString(1, "1");
                            ps.setString(2, "1");
                            ps.setString(3, name);
                            ps.setString(4, upload);
                            ps.setTimestamp(5, added_date);
                            int status = ps.executeUpdate();
                            if (status > 0) {
                                String msg = " File uploaded successfully...";
                            }
                        } catch (SQLException e) {
                            out.println("Exception: " + e);
                            System.out.println("Exception1: " + e);
                        } finally {
                            try {
                                if (ps != null) {
                                    ps.close();
                                }
                                if (con != null) {
                                    con.close();
                                }
                            } catch (SQLException e) {
                                out.println(e);
                            }
                        }
                    }
                }
                request.setAttribute("message", "File uploaded successfully");
            } catch (Exception ex) {
                request.setAttribute("message", "File uploaded failed" + ex);
            }
        } else {
            request.setAttribute("message", "Error");
        }
        request.getRequestDispatcher("/result.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    // </editor-fold>
}
