package com.spectrum.service.comment.dto;

import com.spectrum.domain.comment.Comment;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentDto {

    private Long commentId;
    private Long userId;
    private Long postId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private CommentDto(Long commentId, Long userId, Long postId, String content,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt) {
        this.commentId = commentId;
        this.userId = userId;
        this.postId = postId;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static CommentDto ofEntity(Comment comment) {
        return new CommentDto(
            comment.getId()
            , comment.getUserId()
            , comment.getPostId()
            , comment.getContent()
            , comment.getCreatedAt()
            , comment.getModifiedAt()
        );
    }
}
