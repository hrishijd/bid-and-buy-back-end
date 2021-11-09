package com.example.bidandbuy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bidandbuy.models.PaymentInfo;

public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, Long>{

}
