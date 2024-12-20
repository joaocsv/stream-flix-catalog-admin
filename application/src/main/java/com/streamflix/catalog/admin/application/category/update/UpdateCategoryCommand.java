package com.streamflix.catalog.admin.application.category.update;

import com.streamflix.catalog.admin.domain.category.CategoryId;

public record UpdateCategoryCommand (
        String id,
        String name,
        String description,
        boolean isActive
) {
     public static UpdateCategoryCommand with (String id, String name, String description, boolean isActive) {
         return new UpdateCategoryCommand(id, name, description, isActive);
     }
}
