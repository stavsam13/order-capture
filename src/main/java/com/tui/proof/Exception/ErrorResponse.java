package com.tui.proof.Exception;

import java.util.List;

public class ErrorResponse {

    private  String message;
    private List<String> errors;

    public ErrorResponse() {

    }

    public ErrorResponse(String message) {this.message = message;}

    public ErrorResponse(String message, List<String> details) {
        this.message = message;
        this.errors = details;
    }

    public String getMessage() {return  message;}
    public void  setMessage(String message) {this.message = message;}
    public List<String> getErrors() {return  errors;}
    public void  setErrors(List<String> errors) {this.errors= errors;}
}
