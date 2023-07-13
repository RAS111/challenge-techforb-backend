package com.challenge.techforb.controller;

import com.challenge.techforb.dto.DepositDTO;
import com.challenge.techforb.dto.PaymentDTO;
import com.challenge.techforb.dto.TransactionDTO;

import com.challenge.techforb.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private ITransactionService transactionService;

    @GetMapping("")
    public ResponseEntity<List<TransactionDTO>> getTransaction(){
        List<TransactionDTO> transactionList = transactionService.getTransactions();
        return ResponseEntity.ok().body(transactionList);
    }

    @PostMapping("/sendPayment")
    public ResponseEntity<TransactionDTO> sendPayment(@RequestBody PaymentDTO paymentDTO){
        TransactionDTO result = transactionService.sendPayment(paymentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/sendDeposit")
    public ResponseEntity<TransactionDTO> sendDeposit(@RequestBody DepositDTO depositDTO){
        TransactionDTO result = transactionService.sendDeposit(depositDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

}
