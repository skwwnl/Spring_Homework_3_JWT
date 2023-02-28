package com.personal.homework_third.service;


import com.personal.homework_third.dto.BoardResponseDto;
import com.personal.homework_third.dto.CommentDeleteDto;
import com.personal.homework_third.dto.CommentRequestDto;
import com.personal.homework_third.dto.CommentResponseDto;
import com.personal.homework_third.entity.Board;
import com.personal.homework_third.entity.Comment;
import com.personal.homework_third.entity.User;
import com.personal.homework_third.jwt.JwtUtil;
import com.personal.homework_third.repository.BoardRepository;
import com.personal.homework_third.repository.CommentRepository;
import com.personal.homework_third.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public ResponseEntity<CommentResponseDto> createComment(Long id, CommentRequestDto commentRequestDto,
                                                           HttpServletRequest request){
        // Request 에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims; // token의 정보를 임시로 저장.

        // 토큰이 있는 경우에만 댓글 추가 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);  // 토큰에서 사용자 정보 가져오기
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            // 게시판 정보를 사용하여 DB 조회
            Board board = boardRepository.findById(id).orElseThrow(
                    () -> new NullPointerException("게시판이 존재하지 않습니다.")
            );

            // 댓글 생성하기
            Comment comment = commentRepository.save(Comment.builder()
                            .commentRequestDto(commentRequestDto)
                            .user(user)
                            .board(board)
                            .build());

            return ResponseEntity.ok()
                    .body(new CommentResponseDto(comment));
        } else {
            return null;
        }
    }

    // 댓글 수정하기
    public ResponseEntity<CommentResponseDto> updateComment(Long id, CommentRequestDto commentRequestDto,
                                            HttpServletRequest request) {

        // HttpServletRequest에 담긴 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 댓글 수정 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Board board = boardRepository.findById(id).orElseThrow(
                    () -> new NullPointerException("게시판이 존재하지 않습니다.")
            );

            Comment comment = commentRepository.findById(id).orElseThrow(
                    () -> new NullPointerException("댓글이 존재하지 않습니다.")
            );

            // 댓글 수정하기
            comment.update(commentRequestDto);
            return ResponseEntity.ok()
                    .body(new CommentResponseDto(comment));
        } else {
            return null;
        }
    }

    // 댓글 삭제하기
    public ResponseEntity<CommentDeleteDto> deleteComment(Long id, HttpServletRequest request){
        // HttpServletRequest에 담긴 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 댓글 수정 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Board board = boardRepository.findById(id).orElseThrow(
                    () -> new NullPointerException("게시판이 존재하지 않습니다.")
            );

            Comment comment = commentRepository.findById(id).orElseThrow(
                    () -> new NullPointerException("댓글이 존재하지 않습니다.")
            );

            commentRepository.deleteById(id);
            return ResponseEntity.ok()
                    .body(new CommentDeleteDto());
        } else {
            return null;
        }
    }
}
