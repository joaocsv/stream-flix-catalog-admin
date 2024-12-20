package com.streamflix.catalog.admin.application.category.update;

import com.streamflix.catalog.admin.domain.category.Category;
import com.streamflix.catalog.admin.domain.category.CategoryId;

public record UpdateCategoryOutput (
        CategoryId id
) {
    public static UpdateCategoryOutput from (Category category) {
        return new UpdateCategoryOutput(category.getId());
    }
}
