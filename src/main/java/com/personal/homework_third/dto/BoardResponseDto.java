package com.personal.homework_third.dto;

import com.personal.homework_third.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class BoardResponseDto {
    private Long id;
    private String title;
    private String contents;
    private String username;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;
    private List<CommentResponseDto> comments = new ArrayList<>();

    // 생성자
    public BoardResponseDto(Board board){
        this(board, new ArrayList<>());
    }

    public BoardResponseDto(Board board, List<CommentResponseDto> comments) {
    this.id = board.getId();
    this.username = board.getUser().getUsername();
    this.title = board.getTitle();
    this.contents = board.getContents();
    this.modifiedAt = board.getModifiedAt();
    this.createdAt = board.getCreatedAt();
    this.comments = comments;
    }
}


