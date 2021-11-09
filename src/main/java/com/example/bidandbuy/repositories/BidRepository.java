package com.example.bidandbuy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bidandbuy.models.Bid;
@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

}
