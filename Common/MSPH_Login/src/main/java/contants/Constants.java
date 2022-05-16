/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author jegon
 */
public class Constants {
    private static final String PATRON_IMAGEN
            = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";
    private static final Pattern PATRON = Pattern.compile(PATRON_IMAGEN);
    
    private static final String PATRON_PDF = "([^\\s]+(\\.(?i)(pdf))$)";
    private static final Pattern PATRONPDF = Pattern.compile(PATRON_PDF);
    
    public static boolean validar(final String nombreArchivo) {
        Matcher matcher = PATRON.matcher(nombreArchivo);
        return matcher.matches();
    }
    
    public static boolean validarPDF(final String nombreArchivo) {
        Matcher matcher = PATRONPDF.matcher(nombreArchivo);
        return matcher.matches();
    }
}
