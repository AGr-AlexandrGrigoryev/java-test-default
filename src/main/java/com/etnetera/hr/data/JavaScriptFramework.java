package com.etnetera.hr.data;

import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Simple data entity describing basic properties of every JavaScript framework.
 *
 * @author Etnetera
 */

@Entity
public class JavaScriptFramework {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    @Size(max = 30)
    @Column(nullable = false, length = 30)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) //What is it
    private List<FrameworkVersion> versions;


    @Column(nullable = true, length = 30)
    private String hypeLevel;

    @Column(nullable = true, length = 30)
    private LocalDate deprecationDate;


    public JavaScriptFramework() {

    }

    public JavaScriptFramework(String name, String version, String hypeLevel) {
        this.name = name;
        this.hypeLevel = hypeLevel;
        this.versions = new ArrayList<>();
        versions.add(new FrameworkVersion(version));

    }

    public JavaScriptFramework(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FrameworkVersion> getVersions() {
        return versions;
    }

    public void setVersions(List<FrameworkVersion> versions) {
        this.versions = versions;
    }

    public String getHypeLevel() {
        return hypeLevel;
    }

    public void setHypeLevel(String hypeLevel) {
        this.hypeLevel = hypeLevel;
    }

    public LocalDate getDeprecationDate() {
        return deprecationDate;
    }

    public void setDeprecationDate(LocalDate deprecationDate) {
        this.deprecationDate = deprecationDate;
    }

    @Override
    public String toString() {
        return "JavaScriptFramework [id=" + id + ", name=" + name + "]";
    }

}
