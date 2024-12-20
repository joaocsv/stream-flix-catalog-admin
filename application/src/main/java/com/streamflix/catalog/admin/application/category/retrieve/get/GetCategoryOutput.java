package com.streamflix.catalog.admin.application.category.retrieve.get;

import com.streamflix.catalog.admin.domain.category.Category;
import com.streamflix.catalog.admin.domain.category.CategoryId;

import java.time.Instant;

public record GetCategoryOutput (
   CategoryId id,
   String name,
   String description,
   boolean isActive,
   Instant createdAt,
   Instant updatedAt,
   Instant deletedAt
) {
    public static GetCategoryOutput from (Category category) {
        return new GetCategoryOutput(
            category.getId(),
            category.getName(),
            category.getDescription(),
            category.getIsActive(),
            category.getCreatedAt(),
            category.getUpdatedAt(),
            category.getDeletedAt()
        );
    }
}
