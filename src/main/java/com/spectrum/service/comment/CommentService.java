package com.spectrum.service.comment;

import com.spectrum.auth.aop.UserValidation;
import com.spectrum.controller.comment.dto.CommentCreateResponse;
import com.spectrum.controller.comment.dto.CommentListResponse;
import com.spectrum.controller.comment.dto.CommentUpdateResponse;
import com.spectrum.domain.comment.Comment;
import com.spectrum.domain.post.Post;
import com.spectrum.exception.comment.CommentNotFoundException;
import com.spectrum.exception.post.PostNotFoundException;
import com.spectrum.repository.comment.CommentRepository;
import com.spectrum.repository.post.PostRepository;
import com.spectrum.service.comment.dto.CommentCreateDto;
import com.spectrum.service.comment.dto.CommentDto;
import com.spectrum.service.comment.dto.CommentUpdateDto;
import java.util.List;
import java.util.stream.Collectors;
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

    @Transactional
    @UserValidation
    public CommentUpdateResponse update(Long authorId, Long postId, Long commentId,
        CommentUpdateDto commentUpdateDto) {
        postRepository.findById(postId)
            .orElseThrow(PostNotFoundException::new);

        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(CommentNotFoundException::new);

        comment.authorCheck(authorId);

        comment.update(commentUpdateDto.getContent());
        return CommentUpdateResponse.ofEntity(comment);
    }

    @Transactional
    @UserValidation
    public void delete(Long authorId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(CommentNotFoundException::new);

        comment.authorCheck(authorId);

        commentRepository.delete(comment);
    }

    public CommentListResponse findAll(Long postId) {
        List<Comment> comments = commentRepository.findByPostIdAndParentIdIsNull(postId);
        List<CommentDto> commentDtos = comments.stream()
            .map(CommentDto::ofEntity)
            .collect(Collectors.toUnmodifiableList());
        return new CommentListResponse(commentDtos);
    }
}
