package com.lucas.springboot.app.repositories;

import com.lucas.springboot.app.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
}
