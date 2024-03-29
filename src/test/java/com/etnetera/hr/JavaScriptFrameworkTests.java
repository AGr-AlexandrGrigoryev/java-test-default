package com.etnetera.hr;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Class used for Spring Boot/MVC based tests.
 *
 * @author Etnetera
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class JavaScriptFrameworkTests {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private JavaScriptFrameworkRepository repository;

    private void prepareData() throws Exception {
        repository.findAll().forEach(repository::delete);

        JavaScriptFramework react = new JavaScriptFramework("ReactJS");
        JavaScriptFramework vue = new JavaScriptFramework("Vue.js");

        repository.save(react);
        repository.save(vue);
    }

    @Test
    public void frameworksTest() throws Exception {
        prepareData();

        mockMvc.perform(get("/frameworks")).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("ReactJS")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Vue.js")));
    }

    @Test
    public void addFrameworkInvalid() throws JsonProcessingException, Exception {
        JavaScriptFramework framework = new JavaScriptFramework();
        mockMvc.perform(post("/add").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(framework)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].field", is("name")))
                .andExpect(jsonPath("$.errors[0].message", is("NotEmpty")));

        framework.setName("verylongnameofthejavascriptframeworkjavaisthebest");
        mockMvc.perform(post("/add").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(framework)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].field", is("name")))
                .andExpect(jsonPath("$.errors[0].message", is("Size")));
    }

    @Test
    public void deleteFrameworksTest() throws Exception {
        prepareData();
        JavaScriptFramework framework = repository.findById(1l).get();
        mockMvc.perform(delete("/delete").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(framework)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/frameworks")).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].name", is("Vue.js")));
    }

    @Test
    public void updateFrameworksTest() throws Exception {
        prepareData();
        JavaScriptFramework framework = repository.findById(1l).get();
        framework.setHypeLevel("11%");

        mockMvc.perform(post("/update").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(framework)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("ReactJS")))
                .andExpect(jsonPath("$.hypeLevel", is("11%")));

        mockMvc.perform(get("/frameworks")).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("ReactJS")))
                .andExpect(jsonPath("$[0].hypeLevel", is("11%")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Vue.js")));
    }

}
