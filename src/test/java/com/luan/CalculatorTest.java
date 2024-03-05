package com.luan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CalculatorTest {
	Calculation calculation;
	
	@Test
	void testMultiple() {
		calculation = new Calculation();
		assertEquals(20, calculation.multiply(4, 5));
	}
	@Test
	void testDivice() {
		calculation = new Calculation();
		assertEquals(2, calculation.divice(4, 0));
	}
	
	
}
