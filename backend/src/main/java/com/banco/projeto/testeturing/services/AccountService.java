package com.banco.projeto.testeturing.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banco.projeto.testeturing.DTO.AccountDTO;
import com.banco.projeto.testeturing.entities.Account;
import com.banco.projeto.testeturing.exceptions.BusinessRulesException;
import com.banco.projeto.testeturing.repositories.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository repository;

	public List<AccountDTO> findAll() {
		List<Account> list = repository.findAll();
		List<AccountDTO> listAccountDTO = new ArrayList<>();

		for (Account account : list) {
			listAccountDTO.add(new AccountDTO(account));
		}

		return listAccountDTO;
	}

	public int accountValidation(int account) {
		int userId = repository.findUserIdByAccount(account);

		return userId;
	}

	public Double deposit(Double value, int accountNumber) {

		Account account = repository.findUserAccountByAccountNumber(accountNumber);

		System.out.println(account);
		if (value <= 0) {
			throw new BusinessRulesException("Valor de depósito inválido");
		}

		account.setSaldoConta(account.getSaldoConta() + value);

		repository.save(account);

		return account.getSaldoConta();

	}

	public Double withDraw(Double value, int accountNumber) {
		Account account = repository.findUserAccountByAccountNumber(accountNumber);

		if (value <= 0) {
			throw new BusinessRulesException("Valor de saque inválido!");
		}

		if (value > account.getSaldoConta()) {
			throw new BusinessRulesException("Saldo insuficiente");
		}

		account.setSaldoConta(account.getSaldoConta() - value);

		repository.save(account);

		return account.getSaldoConta();
	}

	public String pixTransfer(int issuerAccountNumber, int receiverAccountNumber, Double value) {

		//CONTA DO EMISSOR
		Account issuerAccount = repository.findUserAccountByAccountNumber(issuerAccountNumber);
		
		//CONTA DO RECEPTOR
		Account receiverAccount = repository.findUserAccountByAccountNumber(receiverAccountNumber);

		//VALOR MÁXIMO PARA TRANFERÊNCIAS VIA PIX
		Double maxValue = 5000d;

		// Verificando se os números das contas do emissor e do receptor são iguais
		if (issuerAccount.getNumeroConta() == receiverAccount.getNumeroConta()) {
			throw new BusinessRulesException("Não são permitidas tranferências a mesma conta");
		}

		// Verificando se o valor solicitado para a transferência está dentro do limite
		// de R$5mil
		if (value > maxValue) {
			throw new BusinessRulesException("Tranferencia Negada! O limite de valor máximo permitido para uma transferência via PIX é de R$ 5 mil");
		}

		// Verificando se o valor a ser tranferido é menor ou igual a zero
		if (value <= 0) {
			throw new BusinessRulesException("Valor inserido para fazer a transferência é inválido");
		}

		// verificando se o o emissor tem saldo suficiente para efetuar a tranferência
		if (value > issuerAccount.getSaldoConta()) {
			throw new BusinessRulesException("Saldo insuficiente para realizar a transferência");
		}

		// INSERINDO o valor solicitado para fazer a tranferência da conta do receptor
		receiverAccount.setSaldoConta(receiverAccount.getSaldoConta() + value);

		// RETIRANDO o valor solicitado para fazer a tranferência da conta do emissor
		issuerAccount.setSaldoConta(issuerAccount.getSaldoConta() - value);
		
		//ATUALIZANDO INFORMAÇÕES do receptor no banco de dados
		repository.save(receiverAccount);
		
		//ATUALIZANDO INFORMAÇÕES do emissor no banco de dados
		repository.save(issuerAccount);
		
		//PEGANDO AS INFORMAÇÕES ATUALIAZADAS do receptor
		receiverAccount = repository.findUserAccountByAccountNumber(receiverAccount.getNumeroConta());
		
		//PEGANDO AS INFORMAÇÕES ATUALIAZADAS do emissor
		issuerAccount = repository.findUserAccountByAccountNumber(issuerAccount.getNumeroConta());

		return "Sua transferência foi realizada com sucesso!\n"
				+ "Saldo do emissor: R$"+ issuerAccount.getSaldoConta()+"\n"
						+ "Saldo do receptor: R$"+ receiverAccount.getSaldoConta();
	}

}
