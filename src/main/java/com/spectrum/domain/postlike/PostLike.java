package com.spectrum.domain.postlike;

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
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long postId;
    private Boolean likeStatus;

    public PostLike(Long userId, Long postId, Boolean likeStatus) {
        this.userId = userId;
        this.postId = postId;
        this.likeStatus = likeStatus;
    }
}
