package com.streamflix.catalog.admin.domain.exceptions;

import java.util.List;
import com.streamflix.catalog.admin.domain.validation.Error;

public class DomainException extends NoStackTraceException {
    private final List<Error> errors;

    private DomainException(String message, List<Error> errors) {
        super("");
        this.errors = errors;
    }

    public static DomainException with (final Error error) {
        return new DomainException(error.message(), List.of(error));
    }

    public static DomainException with (final List<Error> errors) {
        return new DomainException("", errors);
    }

    public List<Error> getErrors() {
        return errors;
    }
}
