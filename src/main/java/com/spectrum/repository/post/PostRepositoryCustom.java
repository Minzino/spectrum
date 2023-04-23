package com.spectrum.repository.post;

import com.spectrum.domain.post.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PostRepositoryCustom {
    Slice<Post> findPostsAfterId(Long lastPostId, Pageable pageable);
    Slice<Post> searchPosts(String searchType, String searchValue, Long lastPostId, Pageable pageable);
}
