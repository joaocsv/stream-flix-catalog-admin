package com.streamflix.catalog.admin.application.category.retrieve.list;

import com.streamflix.catalog.admin.application.UseCase;
import com.streamflix.catalog.admin.domain.category.CategorySearchQuery;
import com.streamflix.catalog.admin.domain.pagination.Pagination;

public abstract class ListCategoriesUseCase extends UseCase<CategorySearchQuery, Pagination<ListCategoryOutput>> {
}
