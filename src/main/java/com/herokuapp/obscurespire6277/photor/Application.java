package com.herokuapp.obscurespire6277.photor;

import static spark.Spark.*;

public class Application {
    public static void main(String[] args) {
        get("/", (req, resp) -> "Hello world!");
    }
}
