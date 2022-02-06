package ex;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.google.gson.Gson;

/**
 *
 * @author GONCAR
 */
public abstract class CustomException extends Exception {
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

class PlanNotFoundEx extends CustomException{
    PlanNotFoundEx(){
        this.code = 404;
        this.message = "El plan no ha sido encontrado.";
    }
    String jsonify() {
       String answer = new Gson().toJson(this);
       return answer;
    }  
}

class PlanAlreadyExistEx extends CustomException{
    PlanAlreadyExistEx(){
        this.code = 409;
        this.message = "El plan que se insertó ya existe.";
    }
    String jsonify() {
       String answer = new Gson().toJson(this);
       return answer;
    }  
}

class RiskAlreadyExistEx extends CustomException{
    RiskAlreadyExistEx(){
        this.code = 409;
        this.message = "El riesgo que se insertó ya existe.";
    }
    String jsonify() {
       String answer = new Gson().toJson(this);
       return answer;
    }  
}

class InvalidRiskIDEx extends CustomException{
    InvalidRiskIDEx(){
        this.code = 417;
        this.message = "El ID del riesgo no es el esperado.";
    }
    String jsonify() {
       String answer = new Gson().toJson(this);
       return answer;
    }  
}