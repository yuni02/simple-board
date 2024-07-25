package com.example.simple_board.post.service;

import com.example.simple_board.post.db.PostDto;
import com.example.simple_board.post.db.PostEntity;
import org.springframework.stereotype.Service;

@Service

public class PostConverter {

    public PostDto toDto(PostEntity postEntity){

        return PostDto.builder()
            .id(postEntity.getId())
            .content(postEntity.getContent())
            .email(postEntity.getEmail())
            .title(postEntity.getTitle())
            .status(postEntity.getStatus())
            .password(postEntity.getPassword())
            .userName(postEntity.getUserName())
            .boardId(postEntity.getBoard().getId())
            .postedAt(postEntity.getPostedAt())
            .build();

    }

}
