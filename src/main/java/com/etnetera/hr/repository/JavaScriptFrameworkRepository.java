package com.etnetera.hr.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import com.etnetera.hr.data.JavaScriptFramework;
import org.springframework.stereotype.Component;

/**
 * Spring data repository interface used for accessing the data in database.
 * 
 * @author Etnetera
 *
 */

public interface JavaScriptFrameworkRepository
        extends CrudRepository<JavaScriptFramework, Long> {

}
