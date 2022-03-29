/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;
import com.google.gson.Gson;
import java.util.Date;
import org.json.JSONObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import sfr.model.Incidence;

/**
 *
 * @author Pc
 */
public class Tests {

    @Test
    public void main() {
        try {
            JSONObject requestJSON = new JSONObject();
            requestJSON.append("name", "Juan Valdez");
            requestJSON.append("description", "xd");
            requestJSON.append("entryDate", "2022-02-02");
            requestJSON.append("affectation", 50);
            requestJSON.append("cause", "xd");
            requestJSON.append("risk", 1);
            requestJSON.append("planID", 1);
            //requestJSON.append("pkID", 1);
            Incidence[] i = new Gson().fromJson(requestJSON.toString(), Incidence[].class);
            System.out.println(i.toString());
            //System.out.println(IncidenceDAO.getInstance().listByColumn("date", "asc"));
        } catch (Exception ex) {
            Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
