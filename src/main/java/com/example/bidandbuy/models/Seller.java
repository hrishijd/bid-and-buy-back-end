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
public class Seller {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long sellerId;
	@OneToMany
	private List<Product> productsSold;
}
