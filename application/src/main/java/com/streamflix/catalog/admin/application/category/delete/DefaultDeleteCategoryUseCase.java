package com.streamflix.catalog.admin.application.category.delete;

import com.streamflix.catalog.admin.domain.category.CategoryGateway;
import com.streamflix.catalog.admin.domain.category.CategoryId;

import java.util.Objects;

public class DefaultDeleteCategoryUseCase extends DeleteCategoryUseCase {
    private final CategoryGateway categoryGateway;

    DefaultDeleteCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public void execute(String id) {
        this.categoryGateway.deleteById(CategoryId.from(id));
    }
}
