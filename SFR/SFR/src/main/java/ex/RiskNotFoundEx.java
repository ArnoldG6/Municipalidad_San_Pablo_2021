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
public class RiskNotFoundEx extends CustomException{
   public RiskNotFoundEx(){
        this.code = 404;
        this.message = "El riesgo no ha sido encontrado.";
    }
   public String jsonify() {
       String answer = new Gson().toJson(this);
       return answer;
    }  
}
