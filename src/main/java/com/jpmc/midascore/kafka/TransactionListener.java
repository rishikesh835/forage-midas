package com.jpmc.midascore.kafka;

import com.jpmc.midascore.foundation.Transaction;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionListener {
    private final AtomicInteger transactionCount = new AtomicInteger(0);
    private final List<Float> firstFourAmounts = new ArrayList<>();

    @KafkaListener(
            topics = "${general.kafka-topic}",
            groupId = "midas-core-group"
    )
    public void listen(Transaction transaction) {
        // 🔴 SET BREAKPOINT HERE
        // Do nothing for now

        int count = transactionCount.incrementAndGet();
        float amount = transaction.getAmount();

        System.out.println(amount);
//        System.out.println("Transaction #" + count + " - Amount: " + amount);
//
//        if (count <= 4) {
//            synchronized (firstFourAmounts) {
//                firstFourAmounts.add(amount);
//                if (count == 4) {
//                    System.out.println("\n=== First Four Transaction Amounts ===");
//                    for (int i = 0; i < firstFourAmounts.size(); i++) {
//                        System.out.println("Transaction " + (i + 1) + ": " + firstFourAmounts.get(i));
//                    }
//                    System.out.println("=====================================\n");
//                }
//            }
//        }
    }
}
