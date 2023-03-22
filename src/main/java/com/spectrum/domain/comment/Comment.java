package com.spectrum.domain.comment;

import com.spectrum.common.domain.BaseTimeEntity;
import com.spectrum.exception.comment.NotParentException;
import com.spectrum.exception.post.NotAuthorException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Table(name = "comments")
@SQLDelete(sql = "update comments set deleted_at = now() where comment_id = ?")
@Where(clause = "deleted_at is null")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private Long postId;
    private Long parentId;
    private Long userId;

    @Column(length = 500, nullable = false)
    private String content;

    public Comment(Long userId, Long postId, String content) {
        this.userId = userId;
        this.postId = postId;
        this.content = content;
    }

    public Comment(Long userId, Long postId, Long parentId, String content) {
        this.postId = postId;
        this.parentId = parentId;
        this.userId = userId;
        this.content = content;
    }

    public void update(String content) {
        this.content = content;
    }

    public void authorCheck(Long authorId) {
        if (!userId.equals(authorId)) {
            throw new NotAuthorException();
        }
    }

    public void parentCheck() {
        if(parentId != null){
            throw new NotParentException();
        }
    }
}
