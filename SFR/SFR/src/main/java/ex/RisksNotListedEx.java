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
public class RisksNotListedEx extends CustomException{
   public RisksNotListedEx(){
        this.code = 503;
        this.message = "La lista de riesgos no est� disponible";
    }
   public String jsonify() {
       String answer = new Gson().toJson(this);
       return answer;
    }  
}
