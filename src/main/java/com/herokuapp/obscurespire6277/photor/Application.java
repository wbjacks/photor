package com.herokuapp.obscurespire6277.photor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Application.class, args);

        // For debugging and also the getting started guide made me do it:
        // Also fuck you Amith I'm leaving comments deal w it
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            // TODO: Move this to logging
            System.out.println("Bean loaded in ApplicationContext: " + beanName);
        }

    }
}
