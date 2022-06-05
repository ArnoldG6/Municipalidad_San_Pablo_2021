/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import javax.servlet.http.HttpServletResponse;
import ioutilities.IOUtilities;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import model.LaboraInhability;

/**
 *
 * @author jegon
 */
public class Receipt {
    private Service service;
    
    public Receipt() {
        this.service = new Service();
    }
    
    public LaboraInhability uploadImage(HttpServletResponse response, int numSolicitud) throws IOException {
        LaboraInhability inha = this.service.searchByRequestLabora(numSolicitud);
        InputStream image = new ByteArrayInputStream(inha.getVoucher());
        try (OutputStream out = response.getOutputStream()) {
            IOUtilities.copy(image, out);
        } catch (IOException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
            throw ex;
        }
        return inha;
    }
}
