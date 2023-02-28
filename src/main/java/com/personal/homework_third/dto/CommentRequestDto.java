package com.personal.homework_third.dto;


public class CommentRequestDto {

    private String content;

    public String getContent(){ // Getter
        return content;
    }

    public CommentRequestDto(){}

    public CommentRequestDto(String content) {
        this.content = content;
    }
}
