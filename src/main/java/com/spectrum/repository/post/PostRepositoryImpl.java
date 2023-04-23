package com.spectrum.repository.post;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spectrum.domain.post.Post;
import com.spectrum.domain.post.QPost;
import com.spectrum.exception.post.InvalidSearchTypeException;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@Repository
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QPost post;

    public PostRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
        this.post = QPost.post;
    }

    @Override
    public Slice<Post> findPostsAfterId(Long lastPostId, Pageable pageable) {
        List<Post> posts = queryFactory
            .selectFrom(post)
            .where(post.id.loe(lastPostId))
            .orderBy(post.id.desc())
            .limit(pageable.getPageSize())
            .fetch();

        return new SliceImpl<>(posts);
    }

    @Override
    public Slice<Post> searchPosts(String searchType, String searchValue, Long lastPostId,
        Pageable pageable) {
        BooleanExpression searchPredicate = searchPredicate(searchType, searchValue);

        List<Post> posts = queryFactory
            .selectFrom(post)
            .where(post.id.loe(lastPostId).and(searchPredicate))
            .orderBy(post.id.desc())
            .limit(pageable.getPageSize())
            .fetch();

        return new SliceImpl<>(posts);
    }

    private BooleanExpression searchPredicate(String searchType, String searchValue) {
        switch (searchType.toUpperCase()) {
            case "TITLE":
                return post.title.containsIgnoreCase(searchValue);
            case "CONTENT":
                return post.content.containsIgnoreCase(searchValue);
            default:
                throw new InvalidSearchTypeException();
        }
    }
}
