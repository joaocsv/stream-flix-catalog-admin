package com.streamflix.catalog.admin.domain.category;

import com.streamflix.catalog.admin.domain.validation.Error;
import com.streamflix.catalog.admin.domain.validation.ValidationHandler;
import com.streamflix.catalog.admin.domain.validation.Validator;

public class CategoryValidator extends Validator {
    private final Category category;

    public CategoryValidator(Category category, final ValidationHandler validationHandler) {
        super(validationHandler);
        this.category = category;
    }

    @Override
    public void validate() {
        if (category.getName() == null) {
            this.validationHandler().append(new Error("'name' should not be null"));
        }
    }
}
