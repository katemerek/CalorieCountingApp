package com.github.katemerek.calorie_counting_app.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@Table(name="Person")
public class Person {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="username")
    private String username;

    @Column(name="email")
    private String email;

    @Column(name="age")
    private int age;

    @Column(name="west")
    private int west;

    @Column(name="growth")
    private int growth;

    @Column(name="goal")
    private String goal;

    @Column(name="dailycalorie")
    private String dailyCalorie;

    @OneToMany(mappedBy = "owner")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Item> items;

