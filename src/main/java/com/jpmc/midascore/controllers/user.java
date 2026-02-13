package com.jpmc.midascore.controllers;


import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.repository.UserRepository;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class user {

    @Autowired
    private UserRepository userRepository;
    @GetMapping("/balance")
    public ResponseEntity<Balance> getBalance(@RequestParam Long userId){
        Optional<UserRecord> u = userRepository.findById(userId);
//        System.out.println(u.get().getBalance());
        if(u.isEmpty()){
            return new ResponseEntity<Balance>(new Balance(0), HttpStatus.OK);
        }
        UserRecord user = u.get();
        return new ResponseEntity<Balance>(new Balance(user.getBalance()),HttpStatus.OK);
    }
}
