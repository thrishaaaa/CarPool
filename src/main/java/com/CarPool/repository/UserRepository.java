package com.CarPool.repository;

import com.CarPool.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);
    User findByIdAndPassword(Long id, String password);// or findByEmail() if you add email
}
