package com.streamflix.catalog.admin.application;

import com.streamflix.catalog.admin.domain.category.Category;

public class UseCase {
    public Category execute () {
        return new Category();
    }
}