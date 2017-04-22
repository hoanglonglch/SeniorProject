package com.guru.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.guru.model.WC;

@Repository
public interface WCRepository  extends JpaRepository<WC, Integer>{

}
