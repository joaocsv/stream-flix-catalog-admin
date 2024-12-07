package com.streamflix.catalog.admin.domain;

import com.streamflix.catalog.admin.domain.category.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoryTest {
    @Test
    public void givenAValidParams_whenCallNewCategory_thenInstantiateACategory() {
        String expectedName = "New Category";
        String expectedDescription = "New description";
        Boolean expectedActive = true;

        Category category = Category.newCategory(expectedName, expectedDescription, expectedActive);

        Assertions.assertNotNull(category.getId());
        Assertions.assertEquals(expectedName, category.getName());
        Assertions.assertEquals(expectedDescription, category.getDescription());
        Assertions.assertEquals(expectedActive, category.getIsActive());
        Assertions.assertNotNull(category.getCreatedAt());
        Assertions.assertNotNull(category.getUpdatedAt());
        Assertions.assertNull(category.getDeletedAt());
    }
}
