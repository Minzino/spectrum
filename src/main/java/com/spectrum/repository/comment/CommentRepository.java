package com.spectrum.repository.comment;

import com.spectrum.domain.comment.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByPostIdAndParentIdIsNull(Long postId);
}
