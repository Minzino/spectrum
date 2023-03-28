package com.spectrum.service.commentlike;

import com.spectrum.common.aop.UserValidation;
import com.spectrum.controller.commentlike.dto.CommentLikeResponse;
import com.spectrum.domain.comment.Comment;
import com.spectrum.domain.commentlike.CommentLike;
import com.spectrum.exception.comment.CommentNotFoundException;
import com.spectrum.repository.comment.CommentRepository;
import com.spectrum.repository.commentlike.CommentLikeRepository;
import com.spectrum.service.commentlike.dto.CommentLikeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;

    @Transactional
    @UserValidation
    public CommentLikeResponse save(Long userId, Long commentId,
        CommentLikeDto commentLikeDto) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(CommentNotFoundException::new);

        CommentLike commentLike = commentLikeDto.convertToEntity(userId, comment.getId());
        return CommentLikeResponse.ofEntity(commentLikeRepository.save(commentLike));
    }
}
