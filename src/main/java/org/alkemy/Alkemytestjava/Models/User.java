package org.alkemy.Alkemytestjava.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String dni;

    @JsonProperty
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

    @JsonIgnore
    public String getFile() {
        return file;
    }

    public String getRole() {
        return role;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    @JsonIgnore
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
