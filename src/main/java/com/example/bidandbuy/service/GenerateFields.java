package com.example.bidandbuy.service;

import com.example.bidandbuy.configurations.JwtUtil;
import com.example.bidandbuy.configurations.MyUserDetailsService;
import com.example.bidandbuy.models.*;
import com.example.bidandbuy.pojo.AuthenticationRequest;
import com.example.bidandbuy.pojo.AuthenticationResponse;
import com.example.bidandbuy.pojo.JwtToken;
import com.example.bidandbuy.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class GenerateFields {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private MyUserDetailsService uds;
    @Autowired
    private UsersRepository usersRepo;
    @Autowired
    private CollectorRepository collectorRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BidRepository bidRepository;
    @Autowired
    private PaymentInfoRepository paymentInfoRepository;
    public ResponseEntity<Object> instantiateUsers(Users user)
    {
        Collector collector=collectorRepository.save(new Collector());
        Seller seller=sellerRepository.save(new Seller());
        user.setCollector(collector);
        user.setSeller(seller);
        user=usersRepo.save(user);
        return getToken(new AuthenticationRequest(user.getUsername(),user.getPassword()));
    }
    public ResponseEntity<Object> getToken(AuthenticationRequest ar)
    {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(ar.getUsername(),ar.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.ok(new BadCredentialsException("wrong username or password").getClass());
        }
        final UserDetails ud=uds.loadUserByUsername(ar.getUsername());
        final String jwt= jwtUtil.generateToken(ud);
        final Users user=usersRepo.findByUsername(ar.getUsername());
        return ResponseEntity.ok(new AuthenticationResponse(user,new JwtToken(jwt)));
    }
    public ResponseEntity<Object> saveProduct(Product product, long sellerId)
    {
        Seller seller= sellerRepository.findById(sellerId).orElse(null);
        if(seller!=null)
        {
            product= productRepository.save(product);
            List<Product> list= seller.getProductsUploaded();
            list.add(product);
            seller.setProductsUploaded(list);
            seller=sellerRepository.save(seller);
            return ResponseEntity.ok(product);
        }
        else
            return ResponseEntity.ok(NoSuchElementException.class);
    }
    public ResponseEntity<Object> saveBid(Bid bid, long collectorId,long productId)
    {
        Collector collector= collectorRepository.findById(collectorId).orElse(null);
        Product product=productRepository.findById(productId).orElse(null);
        if(collector!=null&&product!=null)
        {
            bid=bidRepository.save(bid);
            List<Bid> list= collector.getBids();
            list.add(bid);
            collector.setBids(list);
            collector=collectorRepository.save(collector);
            list= product.getBids();
            list.add(bid);
            product.setBids(list);
            productRepository.save(product);
            return ResponseEntity.ok(bid);
        }
        else
            return ResponseEntity.ok(NoSuchElementException.class);
    }
}
