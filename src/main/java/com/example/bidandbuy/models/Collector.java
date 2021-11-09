package com.example.bidandbuy.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;
@Entity
@Data
public class Collector {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long collectorId;
	@OneToMany
	private List<Product> productsBought;
	@OneToMany
	private List<Bid> bids;
}
