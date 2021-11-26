package com.example.bidandbuy.controllers;

import com.example.bidandbuy.models.Product;
import com.example.bidandbuy.repositories.ProductRepository;
import com.example.bidandbuy.repositories.SellerRepository;
import com.example.bidandbuy.service.GenerateFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@CrossOrigin
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private GenerateFields generateFields;
    @RequestMapping(method = RequestMethod.GET,value = "/product/list/{sellerid}")
    public ResponseEntity<Object> getAllProductsBySellerId(@PathVariable("sellerid") long sellerId)
    {
        try{
            return ResponseEntity.ok(sellerRepository.findById(sellerId).orElse(null).getProductsUploaded());
        } catch (Exception e) {
            return ResponseEntity.ok(e.getClass());
        }
    }
    @RequestMapping(method = RequestMethod.GET,value = "/product/list")
    public ResponseEntity<Object> getAllProducts()
    {
        try{
            return ResponseEntity.ok(productRepository.findAll());
        } catch (Exception e) {
            return ResponseEntity.ok(e.getClass());
        }
    }
    @RequestMapping(method = RequestMethod.GET,value = "/product/{productid}")
    public ResponseEntity<Object> getProductById(@PathVariable("productid") long productId)
    {
        try{
            return ResponseEntity.ok(productRepository.findById(productId).orElseThrow(()->new NoSuchElementException()));
        } catch (Exception e) {
            return ResponseEntity.ok(e.getClass());
        }
    }
    @RequestMapping(method = RequestMethod.POST,value = "/product/new/{sellerid}")
    public ResponseEntity<Object> saveProduct(@RequestBody Product product, @PathVariable("sellerid") long sellerId)
    {
        try{
            return ResponseEntity.ok(generateFields.saveProduct(product,sellerId));
        } catch (Exception e) {
            return ResponseEntity.ok(e.getClass());
        }
    }
}
