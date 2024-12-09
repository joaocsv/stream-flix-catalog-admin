package com.streamflix.catalog.admin.application;

import com.streamflix.catalog.admin.domain.category.Category;

public abstract class UseCase <IN, OUT> {
    public abstract OUT execute (IN input);
}