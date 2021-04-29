package org.alkemy.Alkemytestjava;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private String name;
    private LocalTime time;
    private Integer availability;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="teacher_id")
    private Teacher teacher;

    @OneToMany(mappedBy="subject", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Subscription> subscriptions;

    public Subject() {

    }

    public Subject(String name, LocalTime time,Teacher teacher, Integer availability) {
        this.name = name;
        this.time = time;
        this.availability = availability;
        this.teacher = teacher;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalTime getTime() {
        return time;
    }

    public Integer getAvailability() {
        return availability;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setAvailability(Integer availability) {
        this.availability = availability;
    }
}
