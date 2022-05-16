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
 * Edited by: ArnoldG6
 */
 public class AuthException extends CustomException{
    public AuthException(){
        this.code = 403;
        this.message = "Authorization failed";
    }

    @Override
    public String jsonify() {
       return new Gson().toJson(this);
    }  
}