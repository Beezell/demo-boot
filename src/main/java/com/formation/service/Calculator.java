package com.formation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;

import org.springframework.stereotype.Service;

@Service
public class Calculator {
	
	public int sum(int a, int b) {
		return a+b;
	}
	
	public int multiply(int a, int b) {
		return a*b;
	}

}
