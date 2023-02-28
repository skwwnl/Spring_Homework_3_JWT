package com.personal.homework_third.dto;


public class BoardDeleteDto {

    private String msg = "게시글 삭제 성공";
    private int statusCode = 200;

    public String getMsg(){
        return msg;
    }
    public int getStatusCode(){
        return statusCode;
    }
}
