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
public class InvalidPlanListIDEx extends CustomException{
   public InvalidPlanListIDEx(){
        this.code = 417;
        this.message = "El ID de la lista de planes no es el esperado.";
    }
   public String jsonify() {
       String answer = new Gson().toJson(this);
       return answer;
    }  
}