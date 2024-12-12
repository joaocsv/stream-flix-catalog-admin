package com.streamflix.catalog.admin.domain.validation;

import java.util.List;


public interface ValidationHandler {
    List<Error> getErrors();

    default Error firstError() {
        if (this.getErrors() != null && !this.getErrors().isEmpty()) {
            return getErrors().get(0);
        }

        return null;
    }

    default boolean hasErrors() {
        return getErrors() != null && !getErrors().isEmpty();
    }

    ValidationHandler append (ValidationHandler validationHandler);
    ValidationHandler append (Error error);
    ValidationHandler validate (Validation validation);

    interface Validation {
        void validate ();
    }
}
