package com.streamflix.catalog.admin.domain.validation.handler;
import com.streamflix.catalog.admin.domain.exceptions.DomainException;
import com.streamflix.catalog.admin.domain.validation.ValidationHandler;
import com.streamflix.catalog.admin.domain.validation.Error;

import java.util.List;

public class ThrowValidationHandler implements ValidationHandler {
    @Override
    public List<Error> getErrors() { return List.of(); }

    @Override
    public ValidationHandler append(ValidationHandler validationHandler) {
        throw DomainException.with(validationHandler.getErrors());
    }

    @Override
    public ValidationHandler append(Error error) {
        throw DomainException.with(error);
    }

    @Override
    public ValidationHandler validate(Validation validation) {
        try {
            validation.validate();
        } catch (final Exception exception) {
            throw DomainException.with(new Error(exception.getMessage()));
        }

        return this;
    }
}
