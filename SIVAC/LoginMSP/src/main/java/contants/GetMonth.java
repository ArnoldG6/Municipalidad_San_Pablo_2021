/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contants;

/**
 *
 * @author jegon
 */
public class GetMonth {
    private static String[] months = {"Enero", "Febrero", "Marzo", "Abril", "Mayo",
    "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
    
    
    public static String getMonth(int month) {
        for (int i = 0; i < months.length; i++) {
            if (i == (month - 1)) {
                return months[i];
            }
        }
        return "";
    }
}
