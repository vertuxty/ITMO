package ru.itmo.web.hw4.model;

public enum Color {
    RED("RED"), GREEN("GREEN"), BLUE("BLUE");

    final String color;
    Color(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return color;
    }
}
