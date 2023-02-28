package com.personal.homework_third.entity;


import com.personal.homework_third.dto.CommentRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Id가 자동으로 생성 및 증가.
    private Long Id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false) // Camel Case
    private User user;

    @ManyToOne
    @JoinColumn(name = "boardId", nullable = false) // Camel Case
    private Board board;

    @Builder
    public Comment(CommentRequestDto commentRequestDto, User user, Board board){
        this.content = commentRequestDto.getContent();
        this.user = user;
        this.board = board;
    }

    public void update(CommentRequestDto commentRequestDto){
        this.content = commentRequestDto.getContent();
    }
}
