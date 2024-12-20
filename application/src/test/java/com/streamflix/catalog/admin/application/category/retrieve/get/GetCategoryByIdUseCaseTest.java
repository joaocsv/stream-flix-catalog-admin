package com.streamflix.catalog.admin.application.category.retrieve.get;

import com.streamflix.catalog.admin.domain.category.Category;
import com.streamflix.catalog.admin.domain.category.CategoryGateway;
import com.streamflix.catalog.admin.domain.category.CategoryId;
import com.streamflix.catalog.admin.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class GetCategoryByIdUseCaseTest {
    @InjectMocks
    private DefaultGetCategoryByIdUseCase getCategoryByIdUseCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    public void cleanMocks () {
        Mockito.reset(categoryGateway);
    }

    @Test
    public void givenAValidId_whenCallsGetCategory_thenReturnCategory() {
        final var expectedName = "TOP 5";
        final var expectedDescription = "Filmes mais assistidos na semana";
        final var expectedIsActive = true;

        final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        final var expectedId = category.getId();

        Mockito.when(categoryGateway.findById(Mockito.eq(expectedId)))
                .thenReturn(Optional.of(category.clone()));

        final var categoryFounded = getCategoryByIdUseCase.execute(expectedId.getValue());

        Assertions.assertEquals(expectedId, categoryFounded.id());
        Assertions.assertEquals(expectedName, categoryFounded.name());
        Assertions.assertEquals(expectedDescription, categoryFounded.description());
        Assertions.assertEquals(expectedIsActive, categoryFounded.isActive());
        Assertions.assertEquals(category.getUpdatedAt(), categoryFounded.updatedAt());
        Assertions.assertEquals(category.getCreatedAt(), categoryFounded.createdAt());
        Assertions.assertEquals(category.getDeletedAt(), categoryFounded.deletedAt());

        Mockito.verify(categoryGateway, Mockito.times(1)).findById(Mockito.eq(expectedId));
    }
    @Test
    public void givenAInvalidId_whenCallsGetCategory_thenReturnNotFound() {
        final var expectedId = CategoryId.from("138471291");

        Mockito.when(categoryGateway.findById(Mockito.eq(expectedId)))
                .thenReturn(Optional.empty());

        final var exception = Assertions.assertThrows(DomainException.class, () -> getCategoryByIdUseCase.execute(expectedId.getValue()));

        Assertions.assertEquals("Category with ID 138471291 was not found", exception.getMessage());

        Mockito.verify(categoryGateway, Mockito.times(1)).findById(Mockito.eq(expectedId));
    }
    @Test
    public void givenAValidId_whenGatewayThrowsException_shouldReturnException() {
        final var expectedErrorMessage = "Gateway Error";
        final var expectedId = CategoryId.from("138471291");

        Mockito.when(categoryGateway.findById(Mockito.eq(expectedId)))
                .thenThrow(new IllegalArgumentException(expectedErrorMessage));

        final var exception = Assertions
                .assertThrows(IllegalArgumentException.class, () -> getCategoryByIdUseCase.execute(expectedId.getValue()));


        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(categoryGateway, Mockito.times(1)).findById(Mockito.eq(expectedId));
    }
}
