package com.streamflix.catalog.admin.application.category.create;

import com.streamflix.catalog.admin.domain.category.Category;
import com.streamflix.catalog.admin.domain.category.CategoryGateway;
import com.streamflix.catalog.admin.domain.validation.handler.NotificationHandler;
import io.vavr.control.Either;

import java.util.Objects;

import static io.vavr.API.Try;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {
    private final CategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<NotificationHandler, CreateCategoryOutput> execute(CreateCategoryCommand input) {
        final var category = Category.newCategory(input.name(), input.description(), input.isActive());
        final var notificationHandler = NotificationHandler.create();

        category.validate(notificationHandler);

        if (notificationHandler.hasErrors()) {
            return Either.left(notificationHandler);
        }

        return Try(() -> this.categoryGateway.create(category))
                .toEither()
                .bimap(NotificationHandler::create, CreateCategoryOutput::from);
    }
}
