package com.guru.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.guru.model.Bus;

@Repository
public interface BusStationRepository extends JpaRepository<Bus, Integer> {

}
