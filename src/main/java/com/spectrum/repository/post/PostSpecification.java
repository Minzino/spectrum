package com.spectrum.repository.post;

import com.spectrum.domain.post.Post;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class PostSpecification {

    public static Specification<Post> searchByType(String searchType, String searchValue) {
        return (Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (searchType == null || searchValue == null) {
                return criteriaBuilder.conjunction();
            }

            Predicate predicate;
            switch (searchType.toLowerCase()) {
                case "title":
                    predicate = criteriaBuilder.like(
                        root.get("title"),
                        "%" + searchValue.toLowerCase() + "%"
                    );
                    break;
                case "content":
                    predicate = criteriaBuilder.like(
                        root.get("content"),
                        "%" + searchValue.toLowerCase() + "%"
                    );
                    break;
                default:
                    throw new IllegalArgumentException("Invalid search type: " + searchType);
            }

            return predicate;
        };
    }
}

