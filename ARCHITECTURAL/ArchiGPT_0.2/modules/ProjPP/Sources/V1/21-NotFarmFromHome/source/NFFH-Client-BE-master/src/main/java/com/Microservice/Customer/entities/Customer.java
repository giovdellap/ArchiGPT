package com.Microservice.Customer.entities;

import javax.persistence.*;

@Table(name = "customers")
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;

    @Column(name="email",unique = true, length = 200, nullable = false)
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Customer setId(Integer id) {
        this.id = id;
        return this;
    }

    public Customer setEmail(String email) {
        this.email = email;
        return this;
    }

    public Customer setName(String name) {
        this.name = name;
        return this;
    }

    public Customer setPassword(String description) {
        this.password = description;
        return this;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", Password='" + password + '\'' +
                '}';
    }
}
