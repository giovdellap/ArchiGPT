package it.uniroma1.commons.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "person")
public class Person {
    @Id
    private String fiscalCode;

    @OneToMany(mappedBy = "receiver")
    private List<Fine> fines = new ArrayList<>();

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "address")
    private String address;

    @OneToOne(mappedBy = "owner")
    private Car car;
}