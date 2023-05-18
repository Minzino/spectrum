package com.spectrum.repository.post;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
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
    public Slice<Post> findPostsAfterId(Long cursor, Pageable pageable) {
        JPAQuery<Post> query = queryFactory.selectFrom(post);
        if (cursor != null) {
            query = query.where(post.id.lt(cursor));
        }
        List<Post> posts = query
            .orderBy(post.id.desc())
            .limit(pageable.getPageSize())
            .fetch();

        return new SliceImpl<>(posts);
    }

    @Override
    public Slice<Post> searchPosts(Long cursor, String searchType, String searchValue,
        Pageable pageable) {
        BooleanExpression searchPredicate = searchPredicate(searchType, searchValue);
        JPAQuery<Post> query = queryFactory.selectFrom(post);

        if (cursor != null) {
            query = query.where(post.id.lt(cursor).and(searchPredicate));
        } else {
            query = query.where(searchPredicate);
        }
        List<Post> posts = query
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
