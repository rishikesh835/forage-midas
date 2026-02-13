package com.jpmc.midascore.kafka;

import com.jpmc.midascore.Service.TransactionService;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionListener {
    private final AtomicInteger transactionCount = new AtomicInteger(0);
    private final List<Float> firstFourAmounts = new ArrayList<>();

    @Autowired
    private TransactionService transactionService;

    @KafkaListener(
            topics = "${general.kafka-topic}",
            groupId = "midas-core-group"
    )
    public void listen(Transaction transaction) {
        // 🔴 SET BREAKPOINT HERE
        // Do nothing for now

        try{
            ResponseEntity<Optional> response = transactionService.processTransaction(transaction);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
