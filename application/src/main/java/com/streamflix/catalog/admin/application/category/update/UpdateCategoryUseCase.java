package com.streamflix.catalog.admin.application.category.update;

import com.streamflix.catalog.admin.application.UseCase;
import com.streamflix.catalog.admin.domain.validation.handler.NotificationHandler;
import io.vavr.control.Either;

public abstract class UpdateCategoryUseCase extends UseCase<UpdateCategoryCommand, Either<NotificationHandler, UpdateCategoryOutput>> {
}
