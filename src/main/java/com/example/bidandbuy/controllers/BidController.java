package com.example.bidandbuy.controllers;

import com.example.bidandbuy.models.Bid;
import com.example.bidandbuy.repositories.BidRepository;
import com.example.bidandbuy.repositories.CollectorRepository;
import com.example.bidandbuy.service.GenerateFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping(method = RequestMethod.GET,value = "/bid/list/{collectorid}")
    public ResponseEntity<Object> getAllBidsByCollectorId(@PathVariable("collectorid") long collectorId)
    {
        try{
            return ResponseEntity.ok(collectorRepository.findById(collectorId).orElse(null).getBids());
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
    @RequestMapping(method = RequestMethod.POST,value = "/bid/new/{collectorid}")
    public ResponseEntity<Object> saveBid(@RequestBody Bid bid, @PathVariable("collectorid") long collectorId,@RequestParam("productid") long productId)
    {
        try{
            return ResponseEntity.ok(generateFields.saveBid(bid,collectorId,productId));
        } catch (Exception e) {
            return ResponseEntity.ok(e.getClass());
        }
    }
}
