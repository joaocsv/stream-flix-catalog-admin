package com.streamflix.catalog.admin.domain;

import com.streamflix.catalog.admin.domain.validation.ValidationHandler;

import java.util.Objects;

public abstract class Entity <Id extends Identifier> {
    protected final Id id;

    public Entity(Id id) {
        this.id = id;
    }

    public Id getId() {
        return id;
    }

    public abstract void validate (ValidationHandler handler);

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Entity<?> entity = (Entity<?>) o;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
