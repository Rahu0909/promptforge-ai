package com.rahulagarwal.promptforge.prompt.service.impl;

import com.rahulagarwal.promptforge.common.enums.ErrorCode;
import com.rahulagarwal.promptforge.common.exception.BadRequestException;
import com.rahulagarwal.promptforge.common.exception.ResourceNotFoundException;
import com.rahulagarwal.promptforge.common.response.PageResponse;
import com.rahulagarwal.promptforge.project.entity.Project;
import com.rahulagarwal.promptforge.prompt.dto.request.CreatePromptRequest;
import com.rahulagarwal.promptforge.prompt.dto.request.PromptSearchRequest;
import com.rahulagarwal.promptforge.prompt.dto.request.UpdatePromptRequest;
import com.rahulagarwal.promptforge.prompt.dto.response.PromptResponse;
import com.rahulagarwal.promptforge.prompt.dto.response.PromptSummaryResponse;
import com.rahulagarwal.promptforge.prompt.entity.Prompt;
import com.rahulagarwal.promptforge.prompt.entity.PromptCategory;
import com.rahulagarwal.promptforge.prompt.enums.PromptStatus;
import com.rahulagarwal.promptforge.prompt.mapper.PromptMapper;
import com.rahulagarwal.promptforge.prompt.repository.PromptCategoryRepository;
import com.rahulagarwal.promptforge.prompt.repository.PromptRepository;
import com.rahulagarwal.promptforge.prompt.service.PromptService;
import com.rahulagarwal.promptforge.prompt.service.PromptVersionService;
import com.rahulagarwal.promptforge.prompt.specification.PromptSpecification;
import com.rahulagarwal.promptforge.security.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PromptServiceImpl implements PromptService {

    private final PromptRepository promptRepository;
    private final PromptCategoryRepository categoryRepository;
    private final AuthorizationService authorizationService;
    private final PromptVersionService promptVersionService;
    private final PromptMapper mapper;

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("title", "createdAt", "updatedAt", "status");

    @Override
    public PromptResponse createPrompt(CreatePromptRequest request) {
        Project project = authorizationService.getOwnedProject(request.projectId());
        PromptCategory category = categoryRepository.findById(request.categoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found.", ErrorCode.RESOURCE_NOT_FOUND));
        if (promptRepository.existsByProjectIdAndTitleIgnoreCase(project.getId(), request.title().trim())) {
            throw new BadRequestException("Prompt title already exists in this project.", ErrorCode.BAD_REQUEST);
        }
        Prompt prompt = new Prompt();
        prompt.setProject(project);
        prompt.setCategory(category);
        prompt.setTitle(request.title().trim());
        prompt.setDescription(request.description());
        prompt.setContent(request.content());
        Prompt saved = promptRepository.save(prompt);
        promptVersionService.createInitialVersion(saved);
        log.info("Prompt {} created in project {}", saved.getTitle(), project.getName());
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PromptResponse getPrompt(UUID id) {
        Prompt prompt = authorizationService.getOwnedPrompt(id);
        return mapper.toResponse(prompt);
    }

    @Override
    public PromptResponse updatePrompt(UUID id, UpdatePromptRequest request) {
        Prompt prompt = authorizationService.getOwnedPrompt(id);
        if (request.title() != null && !request.title().equalsIgnoreCase(prompt.getTitle())) {
            if (promptRepository.existsByProjectIdAndTitleIgnoreCase(prompt.getProject().getId(), request.title())) {
                throw new BadRequestException("Prompt title already exists.", ErrorCode.BAD_REQUEST);
            }
            prompt.setTitle(request.title().trim());
        }
        if (request.categoryId() != null) {
            PromptCategory category = categoryRepository.findById(request.categoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found.", ErrorCode.RESOURCE_NOT_FOUND));
            prompt.setCategory(category);
        }
        if (request.description() != null) {
            prompt.setDescription(request.description());
        }
        if (request.content() != null) {
            prompt.setContent(request.content());
        }
        prompt.setLatestVersion(prompt.getLatestVersion() + 1);
        Prompt updated = promptRepository.save(prompt);
        promptVersionService.createNewVersion(updated, "Prompt updated");
        log.info("Prompt updated {}", updated.getTitle());
        return mapper.toResponse(updated);
    }

    @Override
    public void toggleFavorite(UUID id, Boolean favorite) {
        Prompt prompt = authorizationService.getOwnedPrompt(id);
        prompt.setFavorite(favorite);
        promptRepository.save(prompt);
        log.info("Favorite updated for prompt {}", prompt.getTitle());
    }

    @Override
    public void deletePrompt(UUID id) {
        Prompt prompt = authorizationService.getOwnedPrompt(id);
        prompt.setStatus(PromptStatus.ARCHIVED);
        promptRepository.save(prompt);
        log.info("Prompt archived {}", prompt.getTitle());
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<PromptSummaryResponse> searchPrompts(PromptSearchRequest request, int page, int size, String sortBy, String direction) {
        if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
            throw new BadRequestException("Invalid sort field.", ErrorCode.BAD_REQUEST);
        }
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Prompt> prompts = promptRepository.findAll(PromptSpecification.search(request.projectId(), request.categoryId(), request.keyword(), request.status()), pageable);
        return new PageResponse<>(prompts.getContent().stream().map(mapper::toSummary).toList(), prompts.getNumber(), prompts.getSize(), prompts.getTotalElements(), prompts.getTotalPages(), prompts.isFirst(), prompts.isLast());
    }
}