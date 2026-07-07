package com.rahulagarwal.promptforge.prompt.specification;

import com.rahulagarwal.promptforge.prompt.entity.Prompt;
import com.rahulagarwal.promptforge.prompt.enums.PromptStatus;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class PromptSpecification {

    private PromptSpecification() {
    }

    public static Specification<Prompt> search(UUID projectId, UUID categoryId, String keyword, PromptStatus status) {
        return (root, query, cb) -> {
            var predicate = cb.conjunction();
            predicate.getExpressions().add(cb.equal(root.get("status"), PromptStatus.ACTIVE));
            if (projectId != null) {
                predicate.getExpressions().add(cb.equal(root.get("project").get("id"), projectId));
            }
            if (categoryId != null) {
                predicate.getExpressions().add(cb.equal(root.get("category").get("id"), categoryId));
            }
            if (status != null) {
                predicate.getExpressions().add(cb.equal(root.get("status"), status));
            }
            if (keyword != null && !keyword.isBlank()) {
                String pattern = "%" + keyword.toLowerCase() + "%";
                predicate.getExpressions().add(cb.or(cb.like(cb.lower(root.get("title")), pattern), cb.like(cb.lower(root.get("description")), pattern)));
            }
            return predicate;
        };
    }
}