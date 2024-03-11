package com.lap.springbootdocker.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.lap.springbootdocker.Model.ERole;
import com.lap.springbootdocker.Model.Role;
import org.springframework.data.mongodb.repository.Query;

public interface RoleRepository extends MongoRepository<Role, String>{
    @Query("{'name': ?0}")
    Optional<Role> findRoleByName(ERole role);
}
