package com.streamflix.catalog.admin.domain.validation.handler;

import com.streamflix.catalog.admin.domain.exceptions.DomainException;
import com.streamflix.catalog.admin.domain.validation.Error;
import com.streamflix.catalog.admin.domain.validation.ValidationHandler;
import java.util.ArrayList;
import java.util.List;

public class NotificationHandler implements ValidationHandler {
    private final List<Error> errors;

    private NotificationHandler(List<Error> errors) {
        this.errors = errors;
    }

    public static NotificationHandler create() {
        return new NotificationHandler(new ArrayList<Error>());
    }

    public static NotificationHandler create(Error errors) {
        return NotificationHandler.create().append(errors);
    }

    public static NotificationHandler create (Throwable throwable) {
        return NotificationHandler.create().append(new Error(throwable.getMessage()));
    }

    @Override
    public NotificationHandler append(ValidationHandler validationHandler) {
        this.errors.addAll(validationHandler.getErrors());
        return this;
    }

    @Override
    public NotificationHandler append(Error error) {
        this.errors.add(error);
        return this;
    }

    @Override
    public NotificationHandler validate(Validation validation) {
        try {
            validation.validate();
        } catch (final DomainException exception) {
            this.errors.addAll(exception.getErrors());
        } catch (final Throwable throwable) {
            this.errors.add(new Error(throwable.getMessage()));
        }

        return this;
    }

    @Override
    public List<Error> getErrors() {
        return this.errors;
    }
}
