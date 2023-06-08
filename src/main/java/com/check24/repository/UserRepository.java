package com.check24.repository;

import com.check24.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    List<UserEntity> findAllByOrderByIdAsc();

}
