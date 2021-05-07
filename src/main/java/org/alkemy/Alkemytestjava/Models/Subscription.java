package org.alkemy.Alkemytestjava.Models;

import org.alkemy.Alkemytestjava.Models.Subject;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="subject_id")
    private Subject subject;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    public Subscription() {
    }

    public Subscription(Subject subject, User user) {
        this.subject = subject;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public Subject getSubject() {
        return subject;
    }

    public User getUser() {
        return user;
    }




    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setStudent(User user) {
        this.user = user;
    }

}
