package com.streamflix.catalog.admin.domain.category;

import com.streamflix.catalog.admin.domain.AggregateRoot;
import com.streamflix.catalog.admin.domain.validation.ValidationHandler;

import java.time.Instant;

public class Category extends AggregateRoot <CategoryId> {
    private CategoryId id;
    private String name;
    private String description;
    private Boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Category(final CategoryId id, final String name, final String description, final Boolean isActive, final Instant createdAt, final Instant updatedAt, final Instant deletedAt) {
        super(id);
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Category newCategory (final String name, final String description, final Boolean isActive) {
        CategoryId id = CategoryId.unique();
        Instant now = Instant.now();
        Instant deletedAt = isActive ? null : now;
        return new Category(id, name, description, isActive, now, now, deletedAt);
    }

    @Override
    public void validate(ValidationHandler handler) {
        new CategoryValidator(this, handler).validate();
    }

    public Category activate () {
        if (this.getDeletedAt() != null) {
            this.setIsActive(true);
            this.setDeletedAt(null);
            this.setUpdatedAt(Instant.now());
        }

        return this;
    }

    public Category deactivate () {
        if (this.getDeletedAt() == null) {
            this.setIsActive(false);
            this.setDeletedAt(Instant.now());
            this.setUpdatedAt(Instant.now());
        }

        return this;
    }

    public Category update(final String name, final String description, final Boolean isActive) {
        if (isActive) {
            this.activate();
        } else {
            this.deactivate();
        }

        this.setName(name);
        this.setDescription(description);
        this.setUpdatedAt(Instant.now());

        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }
}