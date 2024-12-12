package com.streamflix.catalog.admin.application.category.create;

import com.streamflix.catalog.admin.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;

public class CreateCategoryUseCaseTest {
    @Test
    public void givenAValidCommand_whenCallsCreateCategoryUseCase_thenShouldReturnCategoryId() {
        final CategoryGateway categoryGateway = Mockito.mock(CategoryGateway.class);
        Mockito.when(categoryGateway.create(Mockito.any())).thenAnswer(returnsFirstArg());

        final var expectedName = "TOP 10";
        final var expectedDescription = "Filmes no top 10 de hoje";
        final var expectedIsActive = true;
        final var command = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        final var createCategoryUseCase = new DefaultCreateCategoryUseCase(categoryGateway);
        final var output = createCategoryUseCase.execute(command).getLeft();

        Assertions.assertNotNull(output);
        Assertions.assertNotNull(output.id());

        Mockito.verify(categoryGateway, Mockito.times(1))
            .create(Mockito.argThat(category ->
                Objects.equals(expectedName, category.getName())
                && Objects.equals(expectedDescription, category.getDescription())
                && Objects.equals(expectedIsActive, category.getIsActive())
                && Objects.nonNull(category.getId())
                && Objects.nonNull(category.getCreatedAt())
                && Objects.nonNull(category.getUpdatedAt())
                && Objects.isNull(category.getDeletedAt())
            ));
    }

    @Test
    public void givenAInvalidName_whenCallsCreateCategoryUseCase_thenShouldReturnDomainException() {
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final CategoryGateway categoryGateway = Mockito.mock(CategoryGateway.class);
        final var command = CreateCategoryCommand.with(null, "Filmes no top 10 de hoje", true);
        final var createCategoryUseCase = new DefaultCreateCategoryUseCase(categoryGateway);
        final var notification = createCategoryUseCase.execute(command).get();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());
        Mockito.verify(categoryGateway, Mockito.times(0)).create(Mockito.any());
    }

    @Test
    public void givenAValidCommandWithInactiveCategory_whenCallsCreateCategoryUseCase_thenShouldReturnInactiveCategoryId() {
        final CategoryGateway categoryGateway = Mockito.mock(CategoryGateway.class);
        Mockito.when(categoryGateway.create(Mockito.any())).thenAnswer(returnsFirstArg());

        final var expectedName = "TOP 10";
        final var expectedDescription = "Filmes no top 10 de hoje";
        final var expectedIsActive = false;
        final var command = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        final var createCategoryUseCase = new DefaultCreateCategoryUseCase(categoryGateway);
        final var output = createCategoryUseCase.execute(command).getLeft();

        Assertions.assertNotNull(output);
        Assertions.assertNotNull(output.id());

        Mockito.verify(categoryGateway, Mockito.times(1))
            .create(Mockito.argThat(category ->
                Objects.equals(expectedName, category.getName())
                        && Objects.equals(expectedDescription, category.getDescription())
                        && Objects.equals(expectedIsActive, category.getIsActive())
                        && Objects.nonNull(category.getId())
                        && Objects.nonNull(category.getCreatedAt())
                        && Objects.nonNull(category.getUpdatedAt())
                        && Objects.nonNull(category.getDeletedAt())
            ));
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsRandomException_thenShouldReturnAException () {
        final CategoryGateway categoryGateway = Mockito.mock(CategoryGateway.class);
        Mockito.when(categoryGateway.create(Mockito.any())).thenThrow(new IllegalStateException("Gateway Error"));

        final var expectedName = "TOP 10";
        final var expectedDescription = "Filmes no top 10 de hoje";
        final var expectedIsActive = true;
        final var command = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);
        final var createCategoryUseCase = new DefaultCreateCategoryUseCase(categoryGateway);

        final var notification = createCategoryUseCase.execute(command).get();

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "Gateway Error";

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        Mockito.verify(categoryGateway, Mockito.times(0))
            .create(Mockito.argThat(category ->
                Objects.equals(expectedName, category.getName())
                    && Objects.equals(expectedDescription, category.getDescription())
                    && Objects.equals(expectedIsActive, category.getIsActive())
                    && Objects.nonNull(category.getId())
                    && Objects.nonNull(category.getCreatedAt())
                    && Objects.nonNull(category.getUpdatedAt())
                    && Objects.nonNull(category.getDeletedAt())
            ));
    }
}
