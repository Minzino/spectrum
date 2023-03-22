package com.spectrum.service.comment;

import com.spectrum.auth.aop.UserValidation;
import com.spectrum.controller.comment.dto.CommentCreateResponse;
import com.spectrum.domain.comment.Comment;
import com.spectrum.domain.post.Post;
import com.spectrum.exception.post.PostNotFoundException;
import com.spectrum.repository.comment.CommentRepository;
import com.spectrum.repository.post.PostRepository;
import com.spectrum.service.comment.dto.CommentCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    @UserValidation
    public CommentCreateResponse save(Long userId, Long postId, CommentCreateDto commentCreateDto) {
        Post post = postRepository.findById(postId)
            .orElseThrow(PostNotFoundException::new);

        Comment comment = commentCreateDto.convertToEntity(userId, post.getId());
        return CommentCreateResponse.ofEntity(commentRepository.save(comment));
    }

}
