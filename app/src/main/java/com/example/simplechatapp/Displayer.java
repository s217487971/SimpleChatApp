package com.example.simplechatapp;

@FunctionalInterface
public interface Displayer<T> {
    void display(T value);
}
