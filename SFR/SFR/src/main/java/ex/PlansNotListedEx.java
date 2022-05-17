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
public class PlansNotListedEx extends CustomException{
   public PlansNotListedEx(){
        this.code = 503;
        this.message = "La lista de planes no está disponible";
    }
   public String jsonify() {
       String answer = new Gson().toJson(this);
       return answer;
    }  
}
