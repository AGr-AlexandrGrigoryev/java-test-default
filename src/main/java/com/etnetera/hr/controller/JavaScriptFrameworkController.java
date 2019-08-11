package com.etnetera.hr.controller;

import com.etnetera.hr.rest.Errors;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;

import javax.validation.Valid;

/**
 * Simple REST controller for accessing application logic.
 *
 * @author Etnetera
 */

@RestController
public class JavaScriptFrameworkController extends EtnRestController {

    private final JavaScriptFrameworkRepository repository;

    //@Autowired we shouldn't write this on our code from version 3 Spring
    public JavaScriptFrameworkController(JavaScriptFrameworkRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/frameworks")
    public Iterable<JavaScriptFramework> frameworks() {
        return repository.findAll();
    }

    @PostMapping(value = {"/add", "/update"})
    public ResponseEntity<?> saveUbdateJavaScriptFramework(@Valid @RequestBody JavaScriptFramework framework, BindingResult result) {
        System.out.println(framework);
        if(result.hasErrors()) {
            return handleValidationException(new MethodArgumentNotValidException(null, result));
        }
        return ResponseEntity.ok(repository.save(framework));
    }


    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteJavaScriptFramework(@RequestBody JavaScriptFramework framework) {
        repository.delete(framework);
        return ResponseEntity.ok(framework);
    }

}
