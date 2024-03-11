package it.uniroma1.commons.repository;

import it.uniroma1.commons.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository  extends JpaRepository<Person, String> {
}
