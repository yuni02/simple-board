package com.example.simple_board.post.db;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

   Optional<PostEntity> findFirstByIdAndStatusOrderByIdDesc(Long id, String status);
}
