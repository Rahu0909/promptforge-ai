package com.rahulagarwal.promptforge.project.repository;

import com.rahulagarwal.promptforge.project.entity.Project;
import com.rahulagarwal.promptforge.project.enums.ProjectStatus;
import com.rahulagarwal.promptforge.user.entity.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID>,
        JpaSpecificationExecutor<Project> {

    Page<Project> findAllByOwnerAndStatusNot(UserProfile owner, ProjectStatus status, Pageable pageable);

    Optional<Project> findByIdAndOwnerIdAndStatusNot(UUID id, UUID ownerId, ProjectStatus status);

    boolean existsByOwnerAndNameIgnoreCase(UserProfile owner, String name);

}