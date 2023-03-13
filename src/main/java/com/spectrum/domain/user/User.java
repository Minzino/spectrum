package com.spectrum.domain.user;

import com.spectrum.common.domain.BaseTimeEntity;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String oauthId;

    private String name;

    private String email;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Builder
    public User(Long id, String oauthId, String name, String email, String imageUrl, Authority authority) {
        this.id = id;
        this.oauthId = oauthId;
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
        this.authority = authority;
    }

    public User update(String name, String email, String imageUrl) {
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
        return this;
    }
}
