package org.alkemy.Alkemytestjava;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private int dni;
    private Integer file;
    private String role;

    public User() {
    }

    public User(int dni, Integer file, String role) {
        this.dni = dni;
        this.file = file;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public int getDni() {
        return dni;
    }

    public Integer getFile() {
        return file;
    }

    public String getRole() {
        return role;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public void setFile(Integer file) {
        this.file = file;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
