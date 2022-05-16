package ex;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GONCAR
 */

public abstract class CustomException extends Exception {
    int code;
    String message;
    abstract String jsonify();
}