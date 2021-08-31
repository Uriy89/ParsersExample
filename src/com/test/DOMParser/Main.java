package com.test.DOMParser;

import com.test.model.Root;

public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser();
        Root root = parser.parse();
    }
}
