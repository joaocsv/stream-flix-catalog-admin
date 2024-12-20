package com.streamflix.catalog.admin.application.category.retrieve.list;

import com.streamflix.catalog.admin.domain.category.Category;
import com.streamflix.catalog.admin.domain.category.CategoryId;

import java.time.Instant;

public record ListCategoryOutput (
        CategoryId id,
        String name,
        String description,
        boolean isActive,
        Instant createdAt,
        Instant deletedAt
) {
    public static ListCategoryOutput from(Category category) {
        return new ListCategoryOutput(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getIsActive(),
                category.getCreatedAt(),
                category.getDeletedAt()
        );
    }
}
