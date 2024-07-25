package com.example.simple_board.board.controller;

import com.example.simple_board.board.db.BoardDto;
import com.example.simple_board.board.model.BoardRequest;
import com.example.simple_board.board.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;

    @PostMapping("")
    public void create(@Valid @RequestBody BoardRequest boardRequest){
        boardService.create(boardRequest);

    }

    @GetMapping("/id/{id}")
    public BoardDto view(@PathVariable  Long id){
        return boardService.view(id);


    }
}
