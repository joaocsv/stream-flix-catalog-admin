package com.streamflix.catalog.admin.application.category.create;

import com.streamflix.catalog.admin.domain.category.Category;
import com.streamflix.catalog.admin.domain.category.CategoryId;

public record CreateCategoryOutput (
        CategoryId id
) {
    public static CreateCategoryOutput from(Category category) {
        return new CreateCategoryOutput(category.getId());
    }
}
