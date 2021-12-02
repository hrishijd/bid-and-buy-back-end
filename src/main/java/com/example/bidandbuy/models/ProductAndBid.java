package com.example.bidandbuy.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductAndBid {
    private Product product;
    private Bid bid;
}
