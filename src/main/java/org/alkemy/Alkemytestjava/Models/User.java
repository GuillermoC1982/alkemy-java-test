package org.alkemy.Alkemytestjava.Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String dni;
    private String file;
    private String role;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private final Set<Subscription> subscriptions = new HashSet<>();

    public User() {
    }

    public User(String dni, String file, String role) {
        this.dni = dni;
        this.file = file;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public String getDni() {
        return dni;
    }

    public String getFile() {
        return file;
    }

    public String getRole() {
        return role;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Subscription> getSubscriptions() {
        return subscriptions;
    }
}
