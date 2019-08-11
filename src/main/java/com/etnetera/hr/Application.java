package com.etnetera.hr;

import com.etnetera.hr.controller.JavaScriptFrameworkController;
import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.web.context.ContextLoader;

/**
 * Spring Boot application class.
 *
 * @author Etnetera
 */
@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class);

    }


    //@Bean  this is for my own test
    public CommandLineRunner loadData(JavaScriptFrameworkRepository repository) {
        return (args) -> {
            // save a couple of framework
            repository.save(new JavaScriptFramework("Poor","1.1.0","90 %"));
            repository.save(new JavaScriptFramework("Best","1.1.1","100 %"));
            repository.save(new JavaScriptFramework("Best","1.1.2","60 %"));

            // fetch all customers
            log.info("Frameworks found with findAll():");
            log.info("--------------------------------");
            for (JavaScriptFramework frameworks : repository.findAll()) {
                log.info(frameworks.toString());
            }
            log.info("");

            // fetch an individual customer by ID
            JavaScriptFramework framework = repository.findById(1L).get();
            log.info("Customer found with findOne(" + framework.getId() + "):");
            log.info("--------------------------------");
            log.info(framework.toString());
            log.info("");
        };
    }
}
