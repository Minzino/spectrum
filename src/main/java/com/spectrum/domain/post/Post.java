package com.spectrum.domain.post;

import com.spectrum.common.domain.BaseTimeEntity;
import com.spectrum.service.dto.PostUpdateDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 40, nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;
    private Long memberId;

    public Post(String title, String content, Long memberId) {
        this.title = title;
        this.content = content;
        this.memberId = memberId;
    }

    public void update(PostUpdateDto postUpdateDto, Long memberId) {
        this.title = postUpdateDto.getTitle();
        this.content = postUpdateDto.getContent();
        this.memberId = memberId;
    }
}
