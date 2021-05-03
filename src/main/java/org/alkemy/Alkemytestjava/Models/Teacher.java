package org.alkemy.Alkemytestjava.Models;

import org.alkemy.Alkemytestjava.Models.Subject;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String name;
    private String  last_name;
    private int dni;
    private boolean active;

    @OneToMany(mappedBy="teacher", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Subject> subjects;

    public Teacher() {
    }

    public Teacher(String name, String last_name, int dni, boolean active) {
        this.name = name;
        this.last_name = last_name;
        this.dni = dni;
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLast_name() {
        return last_name;
    }

    public int getDni() {
        return dni;
    }

    public boolean isActive() {
        return active;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
