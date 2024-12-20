package com.streamflix.catalog.admin.application.category.update;

import com.streamflix.catalog.admin.domain.category.CategoryGateway;
import com.streamflix.catalog.admin.domain.category.CategoryId;
import com.streamflix.catalog.admin.domain.exceptions.DomainException;
import com.streamflix.catalog.admin.domain.validation.Error;
import com.streamflix.catalog.admin.domain.validation.handler.NotificationHandler;
import io.vavr.control.Either;

import java.util.Objects;
import java.util.function.Supplier;

import static io.vavr.API.Try;

public class DefaultUpdateCategoryUseCase extends UpdateCategoryUseCase {
    private final CategoryGateway categoryGateway;

    public DefaultUpdateCategoryUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<NotificationHandler, UpdateCategoryOutput> execute(UpdateCategoryCommand input) {
        final var categoryId = CategoryId.from(input.id());

        final var category = this.categoryGateway.findById(categoryId)
                .orElseThrow(notFound(categoryId));

        category.update(input.name(), input.description(), input.isActive());

        final var notification = NotificationHandler.create();
        category.validate(notification);

        if (notification.hasErrors()) {
            return Either.left(notification);
        }

        return Try(() -> this.categoryGateway.update(category))
                .toEither()
                .bimap(NotificationHandler::create, UpdateCategoryOutput::from);
    }

    private Supplier<DomainException> notFound (CategoryId categoryId) {
        return () -> DomainException.with(new Error("Category with ID %s was not found".formatted(categoryId.getValue())));
    }
}
