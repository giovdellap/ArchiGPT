package it.uniroma1.commons.repository;

import it.uniroma1.commons.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, String> {
}
