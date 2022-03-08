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
 public class UserNotFoundEx extends CustomException{
    public UserNotFoundEx(){
        this.code = 404;
        this.message = "El plan no ha sido encontrado.";
    }
    public String jsonify() {
       String answer = new Gson().toJson(this);
       return answer;
    }  
}