package com.herokuapp.obscurespire6277.photor;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    @RequestMapping("/")
    public String index() {
        return "Hello, world!";
    }
}
