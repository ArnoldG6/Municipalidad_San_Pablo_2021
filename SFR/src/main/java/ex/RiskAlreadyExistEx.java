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
public class RiskAlreadyExistEx extends CustomException{
   public RiskAlreadyExistEx(){
        this.code = 409;
        this.message = "El riesgo que se insertó ya existe.";
    }
   public String jsonify() {
       String answer = new Gson().toJson(this);
       return answer;
    }  
}
