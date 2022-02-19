package com.develop.webapp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class CalculatorClassTest {
	
	Calculator underTest = new Calculator();
	
	@Test
	void contextLoads() {
		//given
		int numberOne = 20;
		int numberTwo = 40;
		
		//when
		int result = underTest.add(numberOne, numberTwo);
		
		//then
		int expected = 60;
		assertThat(result).isEqualTo(expected);
	}

	class Calculator {
		int add(int a, int b) {
			return a+b;
		}
	}

}
