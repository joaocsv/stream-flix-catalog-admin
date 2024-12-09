package com.streamflix.catalog.admin.domain;

import com.streamflix.catalog.admin.domain.category.Category;
import com.streamflix.catalog.admin.domain.exceptions.DomainException;
import com.streamflix.catalog.admin.domain.validation.handler.ThrowValidationHandler;
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

    @Test
    public void givenAnInvalidNullName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        Category category = Category.newCategory(null, "New description", true);
        final var actualException = Assertions.assertThrows(DomainException.class, () -> category.validate(new ThrowValidationHandler()));
        Assertions.assertEquals(1, actualException.getErrors().size());
        Assertions.assertEquals("'name' should not be null", actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvalidEmptyName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        Category category = Category.newCategory("  ", "New description", true);
        final var actualException = Assertions.assertThrows(DomainException.class, () -> category.validate(new ThrowValidationHandler()));
        Assertions.assertEquals(1, actualException.getErrors().size());
        Assertions.assertEquals("'name' should not be empty", actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvalidNameLengthLessThan3Characters_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        Category category = Category.newCategory("Be ", "New description", true);
        final var actualException = Assertions.assertThrows(DomainException.class, () -> category.validate(new ThrowValidationHandler()));
        Assertions.assertEquals(1, actualException.getErrors().size());
        Assertions.assertEquals("'name' must be between 3 and 100 characters", actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvalidNameLengthMoreThan100Characters_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        Category category = Category.newCategory("Podemos já vislumbrar o modo pelo qual a mobilidade dos capitais internacionais auxilia a preparação e a composição", "New description", true);
        final var actualException = Assertions.assertThrows(DomainException.class, () -> category.validate(new ThrowValidationHandler()));
        Assertions.assertEquals(1, actualException.getErrors().size());
        Assertions.assertEquals("'name' must be between 3 and 100 characters", actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAValidEmptyDescription_whenCallNewCategoryAndValidate_thenShouldNotReceiveError() {
        String expectedName = "New Category";
        String expectedDescription = " ";
        Boolean expectedActive = true;

        Category category = Category.newCategory(expectedName, expectedDescription, expectedActive);

        Assertions.assertDoesNotThrow(() -> category.validate(new ThrowValidationHandler()));
        Assertions.assertNotNull(category.getId());
        Assertions.assertEquals(expectedName, category.getName());
        Assertions.assertEquals(expectedDescription, category.getDescription());
        Assertions.assertEquals(expectedActive, category.getIsActive());
        Assertions.assertNotNull(category.getCreatedAt());
        Assertions.assertNotNull(category.getUpdatedAt());
        Assertions.assertNull(category.getDeletedAt());
    }

    @Test
    public void givenAValidActiveAsFalse_whenCallNewCategoryAndValidate_thenShouldNotReceiveError() {
        String expectedName = "Terror";
        String expectedDescription = "Filmes do genero terror";
        Boolean expectedActive = false;

        Category category = Category.newCategory(expectedName, expectedDescription, expectedActive);

        Assertions.assertDoesNotThrow(() -> category.validate(new ThrowValidationHandler()));
        Assertions.assertNotNull(category.getId());
        Assertions.assertEquals(expectedName, category.getName());
        Assertions.assertEquals(expectedDescription, category.getDescription());
        Assertions.assertEquals(expectedActive, category.getIsActive());
        Assertions.assertNotNull(category.getCreatedAt());
        Assertions.assertNotNull(category.getUpdatedAt());
        Assertions.assertNotNull(category.getDeletedAt());
    }

    @Test
    public void givenAValidActiveCategory_whenCallDeactivate_thenReturnCategoryInactive() {
        Category categoryBefore = Category.newCategory("Terror", "Filmes do genero terror", true);
        Assertions.assertDoesNotThrow(() -> categoryBefore.validate(new ThrowValidationHandler()));

        final var updatedAtBefore = categoryBefore.getUpdatedAt();

        Assertions.assertTrue(categoryBefore.getIsActive());
        Assertions.assertNull(categoryBefore.getDeletedAt());
        Assertions.assertNotNull(categoryBefore.getUpdatedAt());


        final var actualCategory = categoryBefore.deactivate();
        Assertions.assertEquals(categoryBefore.getId(), actualCategory.getId());
        Assertions.assertNotNull(actualCategory.getDeletedAt());
        Assertions.assertFalse(actualCategory.getIsActive());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAtBefore));
    }

    @Test
    public void givenAValidInactiveCategory_whenCallActivate_thenReturnCategoryActive() {
        Category categoryBefore = Category.newCategory("Terror", "Filmes do genero terror", false);
        Assertions.assertDoesNotThrow(() -> categoryBefore.validate(new ThrowValidationHandler()));

        final var updatedAtBefore = categoryBefore.getUpdatedAt();

        Assertions.assertNotNull(categoryBefore.getDeletedAt());
        Assertions.assertFalse(categoryBefore.getIsActive());

        final var actualCategory = categoryBefore.activate();
        Assertions.assertEquals(categoryBefore.getId(), actualCategory.getId());
        Assertions.assertNull(actualCategory.getDeletedAt());
        Assertions.assertTrue(actualCategory.getIsActive());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAtBefore));
    }

    @Test
    public void givenAValidCategory_whenCallUpdate_thenReturnCategoryUpdated() {
        final var expectedName = "Terror";
        final var expectedDescription = "Filmes do genero terror";
        final var expectedActive = true;
        Category categoryBefore = Category.newCategory(expectedName, expectedDescription, expectedActive);
        Assertions.assertDoesNotThrow(() -> categoryBefore.validate(new ThrowValidationHandler()));
        final var updatedAtBefore = categoryBefore.getUpdatedAt();

        final var newExpectedDescription = "Filmes de Terror";
        final var actualCategory = categoryBefore.update(expectedName, newExpectedDescription, expectedActive);
        Assertions.assertEquals(categoryBefore.getId(), actualCategory.getId());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAtBefore));
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(newExpectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedActive, actualCategory.getIsActive());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidCategory_whenCallUpdateToInactive_thenReturnCategoryUpdated() {
        final var expectedName = "Terror";
        final var expectedDescription = "Filmes do genero terror";
        Category categoryBefore = Category.newCategory(expectedName, expectedDescription, true);
        Assertions.assertDoesNotThrow(() -> categoryBefore.validate(new ThrowValidationHandler()));

        final var updatedAtBefore = categoryBefore.getUpdatedAt();
        final var actualCategory = categoryBefore.update(expectedName, expectedDescription, false);
        Assertions.assertEquals(categoryBefore.getId(), actualCategory.getId());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAtBefore));
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(false, actualCategory.getIsActive());
        Assertions.assertNotNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidCategory_whenCallUpdateWithInvalidParams_thenReturnCategoryUpdated() {
        final var expectedName = "Terror";
        final var expectedDescription = "Filmes do genero terror";
        final var expectedIsActive = true;
        Category categoryBefore = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        Assertions.assertDoesNotThrow(() -> categoryBefore.validate(new ThrowValidationHandler()));

        final var updatedAtBefore = categoryBefore.getUpdatedAt();
        final var newExpectedName = "  ";
        final var actualCategory = categoryBefore.update(newExpectedName, expectedDescription, expectedIsActive);
        Assertions.assertEquals(categoryBefore.getId(), actualCategory.getId());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAtBefore));
        Assertions.assertEquals(newExpectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.getIsActive());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }
}
