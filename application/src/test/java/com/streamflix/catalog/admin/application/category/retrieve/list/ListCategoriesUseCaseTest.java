package com.streamflix.catalog.admin.application.category.retrieve.list;

import com.streamflix.catalog.admin.domain.category.Category;
import com.streamflix.catalog.admin.domain.category.CategoryGateway;
import com.streamflix.catalog.admin.domain.category.CategorySearchQuery;
import com.streamflix.catalog.admin.domain.pagination.Pagination;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ListCategoriesUseCaseTest {
    @InjectMocks
    private DefaultListCategoriesUseCase listCategoriesUseCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    public void cleanMocks () {
        Mockito.reset(categoryGateway);
    }

    @Test
    public void givenAValidQuery_whenCallsListCategories_thenShouldReturnCategories() {
        final var categories = List.of(
                Category.newCategory("TOP 10", "10 filmes mais assistidos na semana", true),
                Category.newCategory("TOP 5", "5 filmes mais assistidos na semana", true)
        );

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "createdAt";
        final var expectedDirection = "asc";

        final var query = new CategorySearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        final var expectedItemsCount = 2;
        final var expectedPagination =
                new Pagination<>(expectedPage, expectedPerPage, categories.size(), categories);

        final var expectedResult = expectedPagination.map(ListCategoryOutput::from);

        Mockito.when(categoryGateway.findAll(Mockito.eq(query)))
                .thenReturn(expectedPagination);

        final var output = listCategoriesUseCase.execute(query);

        Assertions.assertEquals(expectedItemsCount, output.items().size());
        Assertions.assertEquals(expectedResult, output);
        Assertions.assertEquals(expectedPage, output.currentPage());
        Assertions.assertEquals(expectedPerPage, output.perPage());
        Assertions.assertEquals(categories.size(), output.total());
    }

    @Test
    public void givenAValidQuery_whenHasNoResults_thenShouldReturnEmptyCategories() {
        final var categories = List.<Category>of();

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "createdAt";
        final var expectedDirection = "asc";

        final var query = new CategorySearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        final var expectedItemsCount = 0;
        final var expectedPagination =
                new Pagination<>(expectedPage, expectedPerPage, expectedItemsCount, categories);

        final var expectedResult = expectedPagination.map(ListCategoryOutput::from);

        Mockito.when(categoryGateway.findAll(Mockito.eq(query)))
                .thenReturn(expectedPagination);

        final var output = listCategoriesUseCase.execute(query);

        Assertions.assertEquals(expectedItemsCount, output.items().size());
        Assertions.assertEquals(expectedResult, output);
        Assertions.assertEquals(expectedPage, output.currentPage());
        Assertions.assertEquals(expectedPerPage, output.perPage());
        Assertions.assertEquals(expectedItemsCount, output.total());
    }

    @Test
    public void givenAValidQuery_whenGatewayThrows_thenShouldReturnException() {
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "createdAt";
        final var expectedDirection = "asc";
        final var expectedErrorMessage = "Gateway exception";

        final var query = new CategorySearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        Mockito.when(categoryGateway.findAll(Mockito.eq(query)))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final var exception = Assertions.assertThrows(IllegalStateException.class, () -> listCategoriesUseCase.execute(query));

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
    }

}
