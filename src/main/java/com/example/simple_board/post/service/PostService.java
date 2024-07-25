package com.example.simple_board.post.service;

import com.example.simple_board.board.db.BoardRepository;
import com.example.simple_board.common.Api;
import com.example.simple_board.common.Pagination;
import com.example.simple_board.post.db.PostEntity;
import com.example.simple_board.post.db.PostRepository;
import com.example.simple_board.post.model.PostRequest;
import com.example.simple_board.post.model.PostViewRequest;
import com.example.simple_board.reply.service.ReplyService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ReplyService replyService;
    private final BoardRepository boardRepository;

    public PostEntity create(PostRequest postRequest) {

        var boardEntity = boardRepository.findById(postRequest.getBoardId())
            .orElseThrow(() -> new RuntimeException("Board not found"));
        var entity = PostEntity.builder()
            .board(boardEntity)
            .userName(postRequest.getUserName())
            .password(postRequest.getPassword())
            .email(postRequest.getEmail())
            .status("REGISTERED")
            .title(postRequest.getTitle())
            .content(postRequest.getContent())
            .postedAt(LocalDateTime.now())
            .build();

        return postRepository.save(entity);
    }

    public PostEntity view(PostViewRequest postViewRequest) {
        return postRepository.findFirstByIdAndStatusOrderByIdDesc(postViewRequest.getPostId(), "REGISTERED")
            .map(item -> {
                if (!item.getPassword().equals(postViewRequest.getPassword())) {
                    var format = "패스워드가 불일치합니다. %s vs %s";
                    throw new RuntimeException(
                        String.format(format, item.getPassword(), postViewRequest.getPassword()));
                }

//                var replyList = replyService.findAllByPostId(item.getId());
//                item.setReplyList(replyList);
                return item;
            }).orElseThrow(() -> {
                return new RuntimeException("해당 게시글이 존재하지 않습니다." + postViewRequest.getPostId());
            });
    }


    public Api<List<PostEntity>> all(Pageable pageable) {
        var list =  postRepository.findAll(pageable);

        var pagination = Pagination.builder()
            .page(list.getNumber())
            .size(list.getSize())
            .currentElements(list.getNumberOfElements())
            .totalElements(list.getTotalElements())
            .totalPage(list.getTotalPages())
            .build();

        var response = Api.<List<PostEntity>>builder()
            .body(list.toList())
            .pagination(pagination)
            .build();

        return response;
    }

    public void delete(PostViewRequest postViewRequest) {
         postRepository.findById(postViewRequest.getPostId())
            .map(item -> {
                if (!item.getPassword().equals(postViewRequest.getPassword())) {
                    var format = "패스워드가 불일치합니다. %s vs %s";
                    throw new RuntimeException(
                        String.format(format, item.getPassword(), postViewRequest.getPassword()));
                }

                item.setStatus("UNREGISTERED");
                postRepository.save(item);
                return item;
            }).orElseThrow(() -> {
                return new RuntimeException("해당 게시글이 존재하지 않습니다." + postViewRequest.getPostId());
            });
    }
}
