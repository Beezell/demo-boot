package com.formation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.formation.service.Calculator;

@SpringBootTest
class DemoBootApplicationTests {
	
	Calculator calculator = new Calculator();

	@Test
	void testSum() {
		
		assertEquals(6, calculator.sum(2, 4));
	}
	
	@Test
	void testMultiply() {
		
		assertEquals(8, calculator.multiply(2, 4));
	}
	
	@Test
	void testDevide() {
		
		assertEquals(1, calculator.devide(4, 4));
	}
	
	


}
