package com.streamflix.catalog.admin.application.category.update;

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

import java.util.Objects;
import java.util.Optional;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryUseCaseTest {
    @InjectMocks
    private DefaultUpdateCategoryUseCase updateCategoryUseCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    public void cleanMocks () {
        Mockito.reset(categoryGateway);
    }

    @Test
    public void givenAValidCommand_whenCallsUpdateCategory_thenShouldReturnCategoryId() {
        final var category = Category.newCategory("Filme", null, true);

        final var expectedId = category.getId();
        final var expectedName = "Filme de Terror";
        final var expectedDescription = "Filmes aterrorizante";
        final var expectedIsActive = true;

        final var command = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        Mockito.when(categoryGateway.findById(Mockito.eq(expectedId)))
                .thenReturn(Optional.of(category.clone()));

        Mockito.when(categoryGateway.update(Mockito.any()))
                .thenAnswer(returnsFirstArg());

        final var output = updateCategoryUseCase.execute(command).get();

        Assertions.assertNotNull(output);
        Assertions.assertNotNull(output.id());

        Mockito.verify(categoryGateway, Mockito.times(1)).findById(Mockito.eq(expectedId));
        Mockito.verify(categoryGateway, Mockito.times(1)).update(Mockito.argThat(
                updatedCategory -> Objects.equals(updatedCategory.getId(), expectedId)
                && Objects.equals(updatedCategory.getName(), expectedName)
                && Objects.equals(updatedCategory.getDescription(), expectedDescription)
                && Objects.equals(updatedCategory.getIsActive(), expectedIsActive)
                && Objects.equals(updatedCategory.getCreatedAt(), category.getCreatedAt())
                && updatedCategory.getUpdatedAt().isAfter(category.getUpdatedAt())
                && Objects.isNull(updatedCategory.getDeletedAt())
        ));
    }

    @Test
    public void givenAInvalidCommand_whenCallsUpdateCategory_thenShouldReturnDomainException() {
        final var category = Category.newCategory("Filme", null, true);

        final var expectedId = category.getId();
        final var expectedDescription = "Filmes aterrorizante";
        final var expectedIsActive = true;

        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var command = UpdateCategoryCommand.with(
                expectedId.getValue(),
                null,
                expectedDescription,
                expectedIsActive
        );

        Mockito.when(categoryGateway.findById(Mockito.eq(expectedId)))
                .thenReturn(Optional.of(category.clone()));

        final var notificationHandler = updateCategoryUseCase.execute(command).getLeft();

        Assertions.assertEquals(expectedErrorCount, notificationHandler.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notificationHandler.firstError().message());

        Mockito.verify(categoryGateway, Mockito.times(0)).update(Mockito.any());
    }

    @Test
    public void givenAValidInactiveCommand_whenCallsUpdateCategory_thenShouldInactiveCategoryId() {
        final var expectedName = "Filme de Terror";
        final var expectedDescription = "Filmes aterrorizante";

        final var category = Category.newCategory(expectedName, expectedDescription, true);
        final var expectedId = category.getId();

        final var expectedIsActive = false;

        final var command = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        Mockito.when(categoryGateway.findById(Mockito.eq(expectedId)))
                .thenReturn(Optional.of(category.clone()));

        Mockito.when(categoryGateway.update(Mockito.any()))
                .thenAnswer(returnsFirstArg());

        Assertions.assertTrue(category.getIsActive());
        Assertions.assertNull(category.getDeletedAt());

        final var output = updateCategoryUseCase.execute(command).get();

        Assertions.assertNotNull(output);
        Assertions.assertNotNull(output.id());

        Mockito.verify(categoryGateway, Mockito.times(1)).findById(Mockito.eq(expectedId));
        Mockito.verify(categoryGateway, Mockito.times(1)).update(Mockito.argThat(
                updatedCategory -> Objects.equals(updatedCategory.getId(), expectedId)
                        && Objects.equals(updatedCategory.getName(), expectedName)
                        && Objects.equals(updatedCategory.getDescription(), expectedDescription)
                        && Objects.equals(updatedCategory.getIsActive(), expectedIsActive)
                        && Objects.equals(updatedCategory.getCreatedAt(), category.getCreatedAt())
                        && updatedCategory.getUpdatedAt().isAfter(category.getUpdatedAt())
                        && Objects.nonNull(updatedCategory.getDeletedAt())
        ));
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsRandomException_thenShouldReturnAException () {
        final var category = Category.newCategory("TOP 10", "Filmes no top 10 de hoje", true);

        final var expectedId = category.getId();
        final var expectedName = "TOP 5";
        final var expectedDescription = "Filmes no top 5 de hoje";
        final var expectedIsActive = true;
        final var command = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        Mockito.when(categoryGateway.findById(Mockito.eq(expectedId)))
                .thenReturn(Optional.of(category.clone()));
        Mockito.when(categoryGateway.update(Mockito.any())).thenThrow(new IllegalStateException("Gateway Error"));

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "Gateway Error";

        final var notification = updateCategoryUseCase.execute(command).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        Mockito.verify(categoryGateway, Mockito.times(1)).update(Mockito.argThat(
                updatedCategory -> Objects.equals(updatedCategory.getId(), expectedId)
                        && Objects.equals(updatedCategory.getName(), expectedName)
                        && Objects.equals(updatedCategory.getDescription(), expectedDescription)
                        && Objects.equals(updatedCategory.getIsActive(), expectedIsActive)
                        && Objects.equals(updatedCategory.getCreatedAt(), category.getCreatedAt())
                        && updatedCategory.getUpdatedAt().isAfter(category.getUpdatedAt())
                        && Objects.isNull(updatedCategory.getDeletedAt())
        ));
    }

    @Test
    public void givenACommandWithInvalidID_whenCallsUpdateCategory_shouldReturnNotFoundException() {
        final var expectedId = CategoryId.from("129530239");
        final var expectedName = "TOP 5";
        final var expectedDescription = "Filmes no top 5 de hoje";
        final var expectedIsActive = true;
        final var command = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        Mockito.when(categoryGateway.findById(Mockito.eq(expectedId)))
                .thenReturn(Optional.empty());

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "Category with ID %s was not found".formatted(expectedId.getValue());

        final var exception = Assertions.assertThrows(DomainException.class, () -> updateCategoryUseCase.execute(command));

        Assertions.assertEquals(expectedErrorCount, exception.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(categoryGateway, Mockito.times(1)).findById(Mockito.eq(expectedId));
        Mockito.verify(categoryGateway, Mockito.times(0)).update(Mockito.any());
    }
}
