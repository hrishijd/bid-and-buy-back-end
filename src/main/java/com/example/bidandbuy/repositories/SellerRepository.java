package com.example.bidandbuy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bidandbuy.models.Seller;
@Repository
public interface SellerRepository extends JpaRepository<Seller, Long>{

}
