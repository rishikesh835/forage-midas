package com.jpmc.midascore.Service;


import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class TransactionService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public ResponseEntity<Optional> processTransaction(Transaction transaction) {

        float amount = transaction.getAmount();
        UserRecord sender = userRepository.findById(transaction.getSenderId());
        UserRecord recipient = userRepository.findById(transaction.getRecipientId());
        if( sender == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(recipient == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(sender.getBalance() < amount){
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }

        sender.setBalance(sender.getBalance() - amount);
        recipient.setBalance(recipient.getBalance() + amount);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Incentive> response =
                restTemplate.postForEntity(
                        "http://localhost:8080/incentive",
                        transaction,
                        Incentive.class
                );

        float incentiveAmount = 0;

        if (response.getBody() != null) {
            incentiveAmount = response.getBody().getAmount();
        }

        // Add normal amount + incentive to recipient
        recipient.setBalance(recipient.getBalance() + incentiveAmount);

        // Save incentive inside transaction
        transaction.setIncentive(incentiveAmount);
        transactionRepository.save(transaction);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
