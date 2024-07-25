package com.example.simple_board.reply.db;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {

    List<ReplyEntity> findAllByPostIdAndStatusOrderByIdDesc(Long id, String status);

}
