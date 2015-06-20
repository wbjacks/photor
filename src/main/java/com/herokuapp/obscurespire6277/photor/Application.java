package com.herokuapp.obscurespire6277.photor;

import spark.Spark;

public class Application {
    public static void main(String[] args) {
        Spark.get("/", (req, resp) -> "Hello world!");
    }
}
