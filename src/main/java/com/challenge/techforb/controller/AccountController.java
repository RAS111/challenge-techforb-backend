package com.challenge.techforb.controller;

import com.challenge.techforb.dto.AccountDTO;
import com.challenge.techforb.service.IAccountService;
import com.challenge.techforb.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IAccountService iAccountService;

    @GetMapping("/balance")
    public ResponseEntity<List<AccountDTO>> getBalance(){
        List<AccountDTO> accounts = iAccountService.getBalance();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<AccountDTO>> getAccountById(@PathVariable Long userId){
        List<AccountDTO> listAccounts = this.iAccountService.finAllByUser(userId);
        return ResponseEntity.ok().body(listAccounts);
    }
}
