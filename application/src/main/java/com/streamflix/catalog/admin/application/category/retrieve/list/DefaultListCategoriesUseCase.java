package com.streamflix.catalog.admin.application.category.retrieve.list;

import com.streamflix.catalog.admin.domain.category.CategoryGateway;
import com.streamflix.catalog.admin.domain.category.CategorySearchQuery;
import com.streamflix.catalog.admin.domain.pagination.Pagination;

import java.util.Objects;

public class DefaultListCategoriesUseCase extends ListCategoriesUseCase{
    private final CategoryGateway categoryGateway;

    DefaultListCategoriesUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Pagination<ListCategoryOutput> execute(final CategorySearchQuery input) {
        return this.categoryGateway.findAll(input)
                .map(ListCategoryOutput::from);
    }
}
