package com.banco.projeto.testeturing.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banco.projeto.testeturing.repositories.UserRepository;

@Service
public class UserServices {
	
	@Autowired
	private UserRepository repository;

}
