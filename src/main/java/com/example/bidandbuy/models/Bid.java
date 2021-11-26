package com.example.bidandbuy.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Bid {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long bidId;
	private long amount;
	private boolean paymentStatus;
	private boolean sold;
}
