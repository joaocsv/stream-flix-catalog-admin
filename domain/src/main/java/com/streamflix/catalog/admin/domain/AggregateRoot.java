package com.streamflix.catalog.admin.domain;

public abstract class AggregateRoot<Id extends Identifier> extends Entity<Id> {
    public AggregateRoot(Id id) {
        super(id);
    }
}
