package com.personal.homework_third.controller;


import com.personal.homework_third.dto.CommentDeleteDto;
import com.personal.homework_third.dto.CommentRequestDto;
import com.personal.homework_third.dto.CommentResponseDto;
import com.personal.homework_third.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api") // 전체 경로 설정
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService; // DI 주입

    // 댓글 작성 API
    @PostMapping("/comment/{id}")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto,
                                                            HttpServletRequest request){
        return commentService.createComment(id, commentRequestDto, request);
    }

    // 댓글 수정 API
    @PutMapping("/comment/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto,
                                            HttpServletRequest request){
        return commentService.updateComment(id, requestDto, request);
    }

    // 댓글 삭제 API
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<CommentDeleteDto> deleteComment(@PathVariable Long id, HttpServletRequest request){
        return commentService.deleteComment(id ,request);
    }
}
