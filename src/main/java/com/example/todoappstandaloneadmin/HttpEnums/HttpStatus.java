package com.example.todoappstandaloneadmin.HttpEnums;

public enum HttpStatus {

    HTTP_STATUS_200("Ok"),
    HTTP_STATUS_404("Entity Not Found"),
    HTTP_STATUS_502("Bad Server Request");


    public final String label;
    HttpStatus(String label) {
        this.label = label;
    }
}
