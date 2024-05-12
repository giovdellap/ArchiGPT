package com.lap.springbootdocker.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.lap.springbootdocker.Model.DataInfo;

public interface DataRepository extends MongoRepository<DataInfo, String> {
    
    @Query("{'type' :?0}")
    DataInfo findDataByType(String type);

    @Query(value="{'type' : ?0}", delete = true)
    public void deleteByType(String type);
    
    Boolean existsByType(String type);


}
