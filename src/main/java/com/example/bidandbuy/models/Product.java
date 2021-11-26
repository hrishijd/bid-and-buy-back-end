package com.example.bidandbuy.models;

import javax.persistence.*;

import lombok.Data;

import java.util.List;

@Entity
@Data
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long productId;
	@OneToMany
	private List<Bid> bids;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private long minimumAmount;
	@Column(nullable = false)
	private long bidSlab;
	@Column(nullable = false)
	private String description;
	@Column(nullable = false)
	private boolean sold;
	@Lob
	private String image;
}
