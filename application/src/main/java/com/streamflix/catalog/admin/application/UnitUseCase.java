package com.streamflix.catalog.admin.application;

public abstract class UnitUseCase <IN> {
    public abstract void execute (IN input);
}
