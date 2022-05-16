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
 public class RiskTypesEx extends CustomException{
    public RiskTypesEx(){
        this.code = 500;
        this.message = "Los tipos de riesgos no han sido encontrados.";
    }
    public String jsonify() {
       String answer = new Gson().toJson(this);
       return answer;
    }  
}
