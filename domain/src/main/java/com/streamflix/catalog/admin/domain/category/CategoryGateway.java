package com.streamflix.catalog.admin.domain.category;

import com.streamflix.catalog.admin.domain.pagination.Pagination;

import java.util.Optional;

public interface CategoryGateway {
    Category create (Category category);
    Optional<Category> findById(CategoryId id);
    void deleteById(CategoryId id);
    Category update(Category category);
    Pagination<Category> findAll(CategorySearchQuery query);
}
