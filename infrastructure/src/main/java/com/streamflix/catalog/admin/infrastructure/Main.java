package com.streamflix.catalog.admin.infrastructure;

import com.streamflix.catalog.admin.application.UseCase;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        System.out.println(new UseCase().execute());
    }
}