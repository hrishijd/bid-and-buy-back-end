package com.example.bidandbuy.controllers;

import com.example.bidandbuy.models.Bid;
import com.example.bidandbuy.repositories.BidRepository;
import com.example.bidandbuy.repositories.CollectorRepository;
import com.example.bidandbuy.repositories.UsersRepository;
import com.example.bidandbuy.service.GenerateFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin
public class BidController {
    @Autowired
    private CollectorRepository collectorRepository;
    @Autowired
    private GenerateFields generateFields;
    @Autowired
    private BidRepository bidRepository;
    @Autowired
    private UsersRepository usersRepository;
    @RequestMapping(method = RequestMethod.GET,value = "/bid/list")
    public ResponseEntity<Object> getAllBidsByCollectorId(Principal principal)
    {
        try{
            String username;
            if (principal instanceof UserDetails) {
                username = ((UserDetails)principal). getUsername();
            } else {
                username = principal.getName();
            }
            return ResponseEntity.ok(collectorRepository.findById(usersRepository.findByUsername(username).getCollector().getCollectorId()).orElse(null).getBids());
        } catch (Exception e) {
            return ResponseEntity.ok(e.getClass());
        }
    }
    @RequestMapping(method = RequestMethod.GET,value = "/bid/{bidid}")
    public ResponseEntity<Object> getBidById(@PathVariable("bidid") long bidId)
    {
        try{
            return ResponseEntity.ok(bidRepository.findById(bidId).orElseThrow(()-> new NoSuchElementException()));
        } catch (Exception e) {
            return ResponseEntity.ok(e.getClass());
        }
    }
    @RequestMapping(method = RequestMethod.POST,value = "/bid/new/{productid}")
    public ResponseEntity<Object> saveBid(Principal principal, @RequestBody Bid bid, @PathVariable("productid") long productId)
    {
        try{
            String username;
            if (principal instanceof UserDetails) {
                username = ((UserDetails)principal). getUsername();
            } else {
                username = principal.getName();
            }
            return ResponseEntity.ok(generateFields.saveBid(bid,usersRepository.findByUsername(username).getCollector().getCollectorId(),productId));
        } catch (Exception e) {
            return ResponseEntity.ok(e.getClass());
        }
    }
}
