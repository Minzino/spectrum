package com.spectrum.repository.post;

import com.spectrum.domain.post.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PostRepositoryCustom {
    Slice<Post> findPostsAfterId(Long cursor, Pageable pageable);
    Slice<Post> searchPosts(Long cursor, String searchType, String searchValue, Pageable pageable);
}
