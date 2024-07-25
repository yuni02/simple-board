package com.example.simple_board.reply.service;

import com.example.simple_board.post.db.PostRepository;
import com.example.simple_board.reply.db.ReplyEntity;
import com.example.simple_board.reply.db.ReplyRepository;
import com.example.simple_board.reply.model.ReplyRequest;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;

    public ReplyEntity create(ReplyRequest replyRequest) {

        var optionalPostEntity = postRepository.findById(replyRequest.getPostId());

        if(optionalPostEntity.isEmpty()) {
            throw new RuntimeException("게시물이 전혀 존재하지 않습니다");
        }
        var entity = ReplyEntity.builder()
            .post(optionalPostEntity.get())
            .content(replyRequest.getContent())
            .status("REGISTERED")
            .title(replyRequest.getTitle())
            .userName(replyRequest.getUserName())
            .repliedAt(LocalDateTime.now())
            .password(replyRequest.getPassword())
            .build();
        return replyRepository.save(entity);
    }

    public List<ReplyEntity> findAllByPostId(Long postId) {
        return replyRepository.findAllByPostIdAndStatusOrderByIdDesc(postId,"REGISTERED");
    }
}
