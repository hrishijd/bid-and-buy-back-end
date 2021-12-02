package com.example.bidandbuy.controllers;

import com.example.bidandbuy.models.Bid;
import com.example.bidandbuy.models.Product;
import com.example.bidandbuy.models.ProductAndBid;
import com.example.bidandbuy.models.Users;
import com.example.bidandbuy.repositories.BidRepository;
import com.example.bidandbuy.repositories.ProductRepository;
import com.example.bidandbuy.repositories.SellerRepository;
import com.example.bidandbuy.repositories.UsersRepository;
import com.example.bidandbuy.service.GenerateFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
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
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private BidRepository bidRepository;
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
    @RequestMapping(method = RequestMethod.GET,value = "/bids/products")
    public ResponseEntity<Object> saveProduct(Principal principal)
    {
        try{
            String username;
            if (principal instanceof UserDetails) {
                username = ((UserDetails)principal). getUsername();
            } else {
                username = principal.getName();
            }
            List<Bid> bids=usersRepository.findByUsername(username).getCollector().getBids();
            List<ProductAndBid> pab=new ArrayList<>();
            bids.forEach(bid->{
                productRepository.findAll().forEach(
                        product->{
                            if(product.getBids().contains(bid))
                            {
                                pab.add(new ProductAndBid(product,bid));
                            }
                        }
                );
            });
            return ResponseEntity.ok(pab);
        } catch (Exception e) {
            return ResponseEntity.ok(e.getClass());
        }
    }
    @RequestMapping(method = RequestMethod.POST,value = "/mark-sold")
    public ResponseEntity<Object> MarkSold(Principal principal,@RequestBody Bid bid)
    {
        try{
            String username;
            if (principal instanceof UserDetails) {
                username = ((UserDetails)principal). getUsername();
            } else {
                username = principal.getName();
            }
            Users user=usersRepository.findByUsername(username);
            Product pr=new Product();
            productRepository.findAll().forEach(product -> {
                if(product.getBids().contains(bid))
                {
                    product.setSold(true);
                    productRepository.save(product);
                    bid.setSold(true);
                    bidRepository.save(bid);
                }
            });
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }
}
