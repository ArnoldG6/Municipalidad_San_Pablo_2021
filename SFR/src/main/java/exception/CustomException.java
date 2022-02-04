/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

import com.google.gson.Gson;

/**
 *
 * @author GONCAR
 */
abstract class CustomException extends Exception {
    int code;
    String message;
    abstract String jsonify();
}

class RiskNotFoundEx extends CustomException{
    RiskNotFoundEx(){
        this.code = 404;
        this.message = "El riesgo no ha sido encontrado.";
    }
    String jsonify() {
       String answer = new Gson().toJson(this);
       return answer;
    }  
}

