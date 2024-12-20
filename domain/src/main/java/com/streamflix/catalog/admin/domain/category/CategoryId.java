package com.streamflix.catalog.admin.domain.category;

import com.streamflix.catalog.admin.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class CategoryId extends Identifier {
    private final String value;

    private CategoryId(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static CategoryId unique() {
        return CategoryId.from(UUID.randomUUID());
    }

    public static CategoryId from(String value) {
        return new CategoryId(value);
    }

    public static CategoryId from (UUID value) {
        return new CategoryId(value.toString().toLowerCase());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CategoryId that = (CategoryId) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
