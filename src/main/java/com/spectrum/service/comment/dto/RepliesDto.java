package com.spectrum.service.comment.dto;

import com.spectrum.domain.comment.Comment;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RepliesDto {

    private Long commentId;
    private Long userId;
    private Long postId;
    private Long parentId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private RepliesDto(Long commentId, Long userId, Long postId, Long parentId, String content,
        LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.commentId = commentId;
        this.userId = userId;
        this.postId = postId;
        this.parentId = parentId;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static RepliesDto ofEntity(Comment comment) {
        return new RepliesDto(
            comment.getId()
            , comment.getUserId()
            , comment.getPostId()
            , comment.getParentId()
            , comment.getContent()
            , comment.getCreatedAt()
            , comment.getModifiedAt()
        );
    }
}
