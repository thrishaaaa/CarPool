package com.CarPool.repository;

import com.CarPool.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {
    Provider findByEmail(String email); // useful for login

    List<Provider> findByCollegeId(Long collegeId);

    List<Provider> findByCollegeIdAndAreaContainingIgnoreCaseAndCarModelContainingIgnoreCase(
            Long collegeId, String area, String carModel
    );

}
