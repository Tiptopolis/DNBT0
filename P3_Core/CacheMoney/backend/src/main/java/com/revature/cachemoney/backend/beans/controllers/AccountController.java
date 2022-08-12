package com.revature.cachemoney.backend.beans.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.revature.cachemoney.backend.beans.models.Account;
import com.revature.cachemoney.backend.beans.models.Transaction;
import com.revature.cachemoney.backend.beans.services.AccountsService;



/**
 * Handles HTTP requests for user accounts
 */
@RestController
@RequestMapping("/accounts")
public class AccountController {
	private final AccountsService accountsService;

	@Autowired
	public AccountController(AccountsService accountsService) {
		this.accountsService = accountsService;
	}

	// GET all accounts
	@GetMapping()
	public List<Account> getAllAccounts() {
		return accountsService.getAllAccounts();
	}

	// GET account by ID
	@GetMapping(value = "/{id}")
	public Optional<Account> getAccountByID(@PathVariable Integer id) {
		return accountsService.getAccountByID(id);
	}

	// POST an account	
	@PostMapping()
	public void postAccount(@RequestBody Account account) {
		System.out.print("       >>>>>>"+account);
		accountsService.postAccount(account);
	}

	// DELETE an account by ID
	@DeleteMapping(value = "/{id}")
	public void deleteAccountById(@PathVariable Integer id) {
		accountsService.deleteAccountById(id);
	}

	// GET transaction by ID
	@GetMapping(value = "/transactions/{id}")
	public List<Transaction> getTransactionsById(@PathVariable Integer id) {
		return accountsService.getTransactionsById(id);
	}

}
