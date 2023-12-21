package com.example.demounitech;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @PostMapping("/transfer")
    public ResponseEntity<String> transferMoney(@RequestBody Transaction transaction) {
        Account senderAccount = transaction.getSenderAccount();
        Account receiverAccount = transaction.getReceiverAccount();

        if (senderAccount.getId().equals(receiverAccount.getId())) {
            return ResponseEntity.badRequest().body("eyni hesaba transfer mumkun deyil");
        }

        if (!senderAccount.isActive()) {
            return ResponseEntity.badRequest().body("hesab aktiv deyil");
        }

        if (senderAccount.getBalance() < transaction.getAmount()) {
            return ResponseEntity.badRequest().body("hesabinizda kifayet qeder mebleg yoxdur");
        }

        Optional<Account> optionalReceiverAccount = accountRepository.findByAccountNumber(receiverAccount.getAccountNumber());
        if (optionalReceiverAccount.isEmpty()) {
            return ResponseEntity.badRequest().body("istifadeci tapilmadi");
        }

        senderAccount.setBalance(senderAccount.getBalance() - transaction.getAmount());
        receiverAccount.setBalance(receiverAccount.getBalance() + transaction.getAmount());
        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);

        transaction.setTransactionTime(LocalDateTime.now());
        transactionRepository.save(transaction);

        return ResponseEntity.ok("transfer ugurla tamamlandi");
    }
}

