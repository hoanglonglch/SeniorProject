package com.guru.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.guru.model.Hospital;
@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Integer> {

}
