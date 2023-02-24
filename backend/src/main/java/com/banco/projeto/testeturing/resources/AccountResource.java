package com.banco.projeto.testeturing.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banco.projeto.testeturing.DTO.AccountDTO;
import com.banco.projeto.testeturing.DTO.RequestDepositDTO;
import com.banco.projeto.testeturing.services.AccountService;

@RestController
@RequestMapping(value = "/conta")
public class AccountResource {
	
	@Autowired
	public AccountService service;
	
	@GetMapping
	public ResponseEntity<List<AccountDTO>> findAll(){
		List<AccountDTO> listDTO = service.findAll();
		
		return ResponseEntity.ok().body(listDTO);
	}

	
	@GetMapping(value = "/{account}")
	public ResponseEntity<Integer> findUserAccountByAccountNumber(@PathVariable int account){
		int userId =service.accountValidation(account);
		return ResponseEntity.ok().body(userId);
	}
	
	
	@PostMapping(value = "/depositar")
	public ResponseEntity<RequestDepositDTO> deposit(@RequestBody RequestDepositDTO request){
		
		System.out.println("Conta do Usuário:"+request.getAccountNumber());
		System.out.println("Valor do depósito:"+request.getValue());
		
		service.deposit(request.getValue(), request.getAccountNumber());
		
		return ResponseEntity.ok().body(request);
	}
	
	
	

	
}
