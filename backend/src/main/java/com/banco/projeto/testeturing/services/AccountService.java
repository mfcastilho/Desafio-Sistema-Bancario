package com.banco.projeto.testeturing.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banco.projeto.testeturing.DTO.AccountDTO;
import com.banco.projeto.testeturing.entities.Account;
import com.banco.projeto.testeturing.repositories.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository repository;
	
	public List<AccountDTO> findAll () {
		List<Account> list = repository.findAll();
		
		List<AccountDTO> listAccountDTO = new ArrayList<>();
		
		for(Account account : list) {
			listAccountDTO.add(new AccountDTO(account));	
			
		}
		
		
		return listAccountDTO;
		
	}
	
	public int accountValidation(int account) {
		int obj = repository.findUserAccount(account);		
		
		
		return obj;
		
	}
}
