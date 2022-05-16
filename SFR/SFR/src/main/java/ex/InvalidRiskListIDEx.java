    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ex;

import com.google.gson.Gson;

/**
 *
 * @author GONCAR
 */
public class InvalidRiskListIDEx extends CustomException{
   public InvalidRiskListIDEx(){
        this.code = 417;
        this.message = "El ID de la lista de riesgos no es el esperado.";
    }
   public String jsonify() {
       String answer = new Gson().toJson(this);
       return answer;
    }  
}
