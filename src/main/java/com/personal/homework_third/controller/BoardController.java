package com.personal.homework_third.controller;

import com.personal.homework_third.dto.BoardDeleteDto;
import com.personal.homework_third.dto.BoardRequestDto;
import com.personal.homework_third.dto.BoardResponseDto;
import com.personal.homework_third.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

// 모든 들어오는 나가는 Json으로 나간다.
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    // DI, Service 객체 불러오기
    private final BoardService boardService;

    // 모든 게시판 조회 API
    @GetMapping("/boards")
    public List<BoardResponseDto> allBoard() {
        return boardService.allBoard();
    }

    // Post 방식으로 게시판 추가 API
    @PostMapping("/board")
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto,
                                        HttpServletRequest request) {
        return boardService.createBoard(requestDto, request);
    }

    // Get 방식으로 선택한 게시글 조회 API
    @GetMapping("/board/{id}")
    public BoardResponseDto selectBoard(@PathVariable Long id) {
        return boardService.selectBoard(id);
    }

    // Put 방식으로 선택한 게시글 수정 API
    @PutMapping("/board/{id}")
    public BoardResponseDto updateBoard(@PathVariable Long id,
                                        @RequestBody BoardRequestDto requestDto,
                                        HttpServletRequest request) {
        return boardService.updateBoard(requestDto, request);
    }

    // Delete 방식으로 선택한 게시글 삭제 API
    @DeleteMapping("/board/{id}")
    public BoardDeleteDto deleteBoard(@PathVariable Long id, HttpServletRequest request) {
        return boardService.deleteBoard(id, request);
    }

}