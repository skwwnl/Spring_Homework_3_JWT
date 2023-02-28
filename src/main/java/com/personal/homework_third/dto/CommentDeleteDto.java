package com.personal.homework_third.dto;


import com.personal.homework_third.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentDeleteDto {
    private String msg = "댓글 삭제 성공";
    private int statusCode = 200;
}
