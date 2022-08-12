package com.revature.cachemoney.backend.beans.models;

import javax.persistence.*;

import com.revature.cachemoney.backend.BackendApplication;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Object for interacting with Users.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {

	@Id
	@Column(name = "account_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer accountId;

	@Column(name = "type", nullable = false)
	private String type;

	@Column(name = "balance")
	private Double balance;

	@Column(name = "name")
	private String name;

	@JoinColumn(name = "user_id", nullable = false)
	@ManyToOne
	private User userId;

	
	
	public Account(String type) {
		this.type = type;
		this.name = this.type + hashCode();
		this.balance = 0.00;
	}

	public Account(String type, String name) {
		this.type = type;
		this.name = name;
		this.balance = 0.00;

	}
	


	public Account(User user, String type, String name) {
		this.userId = user;
		this.type = type;
		this.name = name;
		this.balance = 0.00;

	}
	
	public Account(User user, String type) {
		this.userId = user;
		this.type = type;
		this.name = type;
		this.balance = 0.00;

	}
	
	public Account(int userId, String type)
	{
		System.out.println(""+userId + " " + type);
		this.userId = BackendApplication.userRepository.getById(userId);
		this.type = type;
		this.name = type;
		this.balance = 0.00;
	}
	
	
}
