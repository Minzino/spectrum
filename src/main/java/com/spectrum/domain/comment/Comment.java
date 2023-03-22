package com.spectrum.domain.comment;

import com.spectrum.common.domain.BaseTimeEntity;
import com.spectrum.exception.post.NotAuthorException;
import javax.persistence.Column;
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
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private Long postId;
    private Long userId;

    @Column(length = 500, nullable = false)
    private String content;

    public Comment(Long userId, Long postId, String content) {
        this.userId = userId;
        this.postId = postId;
        this.content = content;
    }

    public void update(String content) {
        this.content = content;
    }

    public void authorCheck(Long authorId) {
        if (!this.userId.equals(authorId)) {
            throw new NotAuthorException();
        }
    }
}
