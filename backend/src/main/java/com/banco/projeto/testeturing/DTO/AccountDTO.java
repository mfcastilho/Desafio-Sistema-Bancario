package com.banco.projeto.testeturing.DTO;

import java.io.Serializable;

import com.banco.projeto.testeturing.entities.Account;
import com.banco.projeto.testeturing.entities.User;

public class AccountDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private User titular;
    private int agenciaConta;
    private int numeroConta;
    private Double saldoConta;
    
    
    public AccountDTO() {
    	
    }

	public AccountDTO(Long id, User titular, int agenciaConta, int numeroConta, Double saldoConta) {
		super();
		this.id = id;
		this.titular = titular;
		this.agenciaConta = agenciaConta;
		this.numeroConta = numeroConta;
		this.saldoConta = saldoConta;
	}
	
	public AccountDTO(Account account) {
		this.id = account.getId();
		this.titular = account.getTitular();
		this.agenciaConta = account.getAgenciaConta();
		this.numeroConta = account.getNumeroConta();
		this.saldoConta = account.getSaldoConta();
	}


	public Long getId() {
		return id;
	}


	public User getTitular() {
		return titular;
	}


	public int getAgenciaConta() {
		return agenciaConta;
	}


	public int getNumeroConta() {
		return numeroConta;
	}


	public Double getSaldoConta() {
		return saldoConta;
	}
	
	public void setSaldoConta(Double saldoConta) {
		
		this.saldoConta = saldoConta;
	}


}
