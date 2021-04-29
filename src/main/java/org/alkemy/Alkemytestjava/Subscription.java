package org.alkemy.Alkemytestjava;

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


}
