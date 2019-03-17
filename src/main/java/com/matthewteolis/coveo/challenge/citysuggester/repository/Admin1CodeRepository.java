package com.matthewteolis.coveo.challenge.citysuggester.repository;

import com.matthewteolis.coveo.challenge.citysuggester.entity.admin1.Admin1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Admin1CodeRepository extends JpaRepository<Admin1, String> {

}
