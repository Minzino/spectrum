package com.spectrum.domain.commentlike;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long commentId;
    private Boolean likeStatus;

    public CommentLike(Long userId, Long commentId, Boolean likeStatus) {
        this.userId = userId;
        this.commentId = commentId;
        this.likeStatus = likeStatus;
    }
}
