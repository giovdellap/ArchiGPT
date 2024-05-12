package com.lap.springbootdocker.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.lap.springbootdocker.Model.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    
    @Query("{'username' :?0}")
    User findUserByUsername(String username);

    @Query(value="{'username' : ?0}", delete = true)
    public void deleteByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    
    @SuppressWarnings("unchecked")
    User save(User user);

}

