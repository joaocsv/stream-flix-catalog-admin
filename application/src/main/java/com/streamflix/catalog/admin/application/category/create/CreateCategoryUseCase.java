package com.streamflix.catalog.admin.application.category.create;

import com.streamflix.catalog.admin.application.UseCase;
import com.streamflix.catalog.admin.domain.validation.handler.NotificationHandler;
import io.vavr.control.Either;

public abstract class CreateCategoryUseCase extends UseCase<CreateCategoryCommand, Either<CreateCategoryOutput, NotificationHandler>> {}
