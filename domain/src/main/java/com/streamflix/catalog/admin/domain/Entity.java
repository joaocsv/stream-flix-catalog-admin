package com.streamflix.catalog.admin.domain;

import java.util.Objects;

public class Entity <Id extends Identifier> {
    protected final Id id;

    public Entity(Id id) {
        this.id = id;
    }

    public Id getId() {
        return id;
    }

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
