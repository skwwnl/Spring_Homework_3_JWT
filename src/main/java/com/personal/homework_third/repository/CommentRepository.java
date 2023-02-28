package com.personal.homework_third.repository;

import com.personal.homework_third.entity.Board;
import com.personal.homework_third.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository <Comment, Long> {
//    List<Comment> findAllByBoardOrderByCreatedAtDesc(Board board);

    void deleteAllByBoard(Board board);
}
