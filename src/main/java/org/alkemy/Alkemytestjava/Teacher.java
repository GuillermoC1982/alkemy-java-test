package org.alkemy.Alkemytestjava;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
