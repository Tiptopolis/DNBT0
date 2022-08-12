package com.revature.cachemoney.backend;

import static com.revature.cachemoney.backend.bones.AppUtils.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.revature.cachemoney.backend.beans.models.Account;
import com.revature.cachemoney.backend.beans.models.Transaction;
import com.revature.cachemoney.backend.beans.models.User;
import com.revature.cachemoney.backend.beans.repositories.AccountRepo;
import com.revature.cachemoney.backend.beans.repositories.TransactionRepo;
import com.revature.cachemoney.backend.beans.repositories.UserRepo;
import com.revature.cachemoney.backend.beans.services.AccountsService;
import com.revature.cachemoney.backend.beans.utils.ApplicationContextProvider;

@SpringBootApplication(scanBasePackages = "com.revature.cachemoney.backend.beans")
public class BackendApplication {

	public static ApplicationContext context;
	public static UserRepo userRepository;
	public static AccountsService acctSvc;
	public static TransactionRepo trnsRepository;

	public static void main(String[] args) {
		Log("------------->>>");
		SpringApplication.run(BackendApplication.class, args);
		Log("<<<-------------");
		context = ApplicationContextProvider.getApplicationContext();

		userRepository = context.getBean(UserRepo.class);
		acctSvc = context.getBean(AccountsService.class);
		trnsRepository = context.getBean(TransactionRepo.class);

		tst1();

	}

	private static void tst1() {
		Log("-----------------------------------------------");
		User newUser = new User();
		Account newAcct = new Account();
		Transaction newTrns = new Transaction();

		newUser = new User("Fn1", "Ln1", "email1@email.com", "password1", "username1");
		User u1 = newUser;
		userRepository.save(u1);

		newAcct = new Account(u1, "CHECKING");
		Account a1_1 = newAcct;
		acctSvc.postAccount(newAcct);
		newAcct = new Account(u1, "SAVINGS");
		Account a1_2 = newAcct;
		acctSvc.postAccount(newAcct);

		newUser = new User("Fn2", "Ln2", "email2@email.com", "password2", "username2");
		User u2 = newUser;
		userRepository.save(u2);

		newAcct = new Account(u2, "CHECKING");
		Account a2_1 = newAcct;
		acctSvc.postAccount(newAcct);
		newAcct = new Account(u2, "SAVINGS");
		Account a2_2 = newAcct;
		acctSvc.postAccount(newAcct);

		Log(u1);
		Log("    "+acctSvc.getAccountByID(a1_1.getAccountId()));

		Log(u2);

	}
}
