package com.streamflix.catalog.admin.application.category.retrieve.get;

import com.streamflix.catalog.admin.domain.category.CategoryGateway;
import com.streamflix.catalog.admin.domain.category.CategoryId;
import com.streamflix.catalog.admin.domain.exceptions.DomainException;
import com.streamflix.catalog.admin.domain.validation.Error;

import java.util.Objects;

public class DefaultGetCategoryByIdUseCase extends GetCategoryByIdUseCase {
    private final CategoryGateway categoryGateway;

    DefaultGetCategoryByIdUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public GetCategoryOutput execute(String input) {
        final var categoryId = CategoryId.from(input);

        return this.categoryGateway.findById(categoryId)
                .map(GetCategoryOutput::from)
                .orElseThrow(() -> notFound(categoryId));
    }

    private DomainException notFound(CategoryId categoryId) {
        return DomainException.with(new Error("Category with ID %s was not found".formatted(categoryId.getValue())));
    }
}
