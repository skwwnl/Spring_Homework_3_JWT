package com.personal.homework_third.service;

import com.personal.homework_third.dto.BoardDeleteDto;
import com.personal.homework_third.dto.BoardRequestDto;
import com.personal.homework_third.dto.BoardResponseDto;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final CommentRepository commentRepository;

    /*
    게시판 전체 조회
    */
    @Transactional (readOnly = true)
    public List<BoardResponseDto> allBoard(){
        // 수정 시간 내림차순으로 모든 게시판 조회
        List<Board> boards = boardRepository.findAllByOrderByCreatedAtAsc();
        // 응답 게시판 목록
        List<BoardResponseDto> responseDtos = new ArrayList<>();

        // 각 게시판에 달린 댓글들을 등록일 기준으로 내림차순 정렬하여 해당 게시판에 담는다.
        for (Board board : boards) {
            List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
            for (Comment comment : board.getComments()) {
                commentResponseDtoList.add(new CommentResponseDto(comment));
            }
            responseDtos.add(new BoardResponseDto(board, commentResponseDtoList));
        }
        return responseDtos;
    }
        
    @Transactional
    // DB에 연결을 해서 저장을 하려면 @Entity가 걸려있는 Board 클래스를 인스턴스로 만들어서
    // 그 값을 사용해서 저장을 해야한다.
    public BoardResponseDto createBoard(BoardRequestDto requestDto, HttpServletRequest request) {
        // Request 에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 관심상품 추가 가능
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

            // 요청받은 DTO 로 DB에 저장할 객체 만들기
            Board board = boardRepository.saveAndFlush(new Board(requestDto, user));

            return new BoardResponseDto(board);
        } else {
            return null;
        }
    }

    @Transactional
    public BoardResponseDto selectBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 번호의 게시글이 존재하지 않습니다.")
        );
        return new BoardResponseDto(board);
    }

    @Transactional
    public BoardResponseDto updateBoard(BoardRequestDto requestDto, HttpServletRequest request) {
        // Request 에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 게시판 변경 가능
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
            Board board = boardRepository.findById(user.getId()).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않는 게시글입니다.")
            );

            board.update(requestDto);
            return new BoardResponseDto(board);
        } else {
            return null;
        }
    }

    @Transactional
    public BoardDeleteDto deleteBoard(Long id, HttpServletRequest request) {
        // Request 에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 게시판 삭제
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
            Board board = boardRepository.findById(user.getId()).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않는 게시글입니다.")
            );

            commentRepository.deleteAllByBoard(board);

            boardRepository.deleteById(id);
            return new BoardDeleteDto();
        } else {
            return null;
        }
    }
}