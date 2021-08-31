package com.test.model;

public class People {
    private String name;
    private int age;

    public People(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<people>\n" +
                "\t<age> " + age + "</age>\n" +
                "\t<name>" + name + "</name>\n" +
                "</people>";
    }
}
