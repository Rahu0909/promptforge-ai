package com.rahulagarwal.promptforge.project.specification;

import com.rahulagarwal.promptforge.project.dto.request.ProjectSearchRequest;
import com.rahulagarwal.promptforge.project.entity.Project;
import com.rahulagarwal.promptforge.project.enums.ProjectStatus;
import com.rahulagarwal.promptforge.user.entity.UserProfile;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class ProjectSpecification {

    private ProjectSpecification() {
    }

    public static Specification<Project> search(UserProfile owner, ProjectSearchRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("owner"), owner));
            if (request.keyword() != null && !request.keyword().isBlank()) {
                String keyword = "%" + request.keyword().toLowerCase() + "%";
                predicates.add(cb.or(cb.like(cb.lower(root.get("name")), keyword),
                                cb.like(cb.lower(root.get("description")), keyword)
                        )
                );
            }
            if (request.status() != null) {
                predicates.add(cb.equal(root.get("status"), request.status()));
            } else {
                predicates.add(cb.notEqual(root.get("status"), ProjectStatus.DELETED));
            }
            if (request.visibility() != null) {
                predicates.add(cb.equal(root.get("visibility"), request.visibility()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}