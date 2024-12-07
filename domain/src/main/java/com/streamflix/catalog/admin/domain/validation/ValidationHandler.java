package com.streamflix.catalog.admin.domain.validation;

import java.util.List;


public interface ValidationHandler {
    List<Error> getErrors();

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
