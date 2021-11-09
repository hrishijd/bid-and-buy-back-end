package com.example.bidandbuy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bidandbuy.models.Collector;
@Repository
public interface CollectorRepository extends JpaRepository<Collector, Long> {

}
