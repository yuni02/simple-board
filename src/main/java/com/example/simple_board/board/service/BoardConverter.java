package com.example.simple_board.board.service;

import com.example.simple_board.board.db.BoardDto;
import com.example.simple_board.board.db.BoardEntity;
import com.example.simple_board.post.service.PostConverter;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardConverter {

    private final PostConverter postConverter;

    public BoardDto toDto(BoardEntity boardEntity) {

        var postList = boardEntity.getPostList().stream().map(postConverter::toDto).collect(Collectors.toList());
        return BoardDto.builder()
            .id(boardEntity.getId())
            .status(boardEntity.getStatus())
            .boardName(boardEntity.getBoardName())
            .postList(postList)
            .build();
    }
    }
