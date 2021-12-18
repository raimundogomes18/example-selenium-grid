package calculator.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

import calculator.pageobjects.CalculatorPageObject;
import calculator.pageobjects.Constants;

class CalculatorBaseTest {

	private Logger logger = Logger.getLogger(getClass().getName());

	@Test
	void calculatorTest() {

		CalculatorPageObject calculator = new CalculatorPageObject();
		calculatorTest(calculator);
	}

	public void calculatorTest(CalculatorPageObject calculator) {

		try {

			calculator.acessCalculator();

			testDivision(calculator);

			testMultiplication(calculator);

			testSum(calculator);

			testSubtraction(calculator);

		} catch (Exception e) {
			fail("Test failed!");
			logger.info("Test failed!");
			
		} finally {
			calculator.close();
			logger.info("browser closed!!!");
		}

	}

	private void testDivision(CalculatorPageObject calculator) {

		String result = calculate(calculator, Constants.EIGHT, Constants.DIVISION, Constants.FOUR);

		assertEquals(Constants.TWO, result);
	}

	private void testSum(CalculatorPageObject calculator) {

		String result = calculate(calculator, Constants.TWO, Constants.PLUS, Constants.SEVEN);

		assertEquals(Constants.NINE, result);

		result = calculate(calculator, Constants.ZERO, Constants.PLUS, Constants.ONE);

		assertEquals(Constants.ONE, result);
	}

	private void testSubtraction(CalculatorPageObject calculator) {

		String result = calculate(calculator, Constants.SEVEN, Constants.MINUS, Constants.SIX);

		assertEquals(Constants.ONE, result);

		result = calculate(calculator, Constants.THREE, Constants.MINUS, Constants.ZERO);

		assertEquals(Constants.THREE, result);
	}

	private String calculate(CalculatorPageObject calculator, String firtEOperator, String operation,	String secondOperator) {

		calculator.clickButton(firtEOperator);

		calculator.clickButton(operation);

		calculator.clickButton(secondOperator);

		calculator.clickButton(Constants.EQUAL);

		return calculator.getResult();
	}

	private void testMultiplication(CalculatorPageObject calculator) {

		String result = calculate(calculator, Constants.ZERO, Constants.MULTIPLICATION, Constants.NINE);

		assertEquals(Constants.ZERO, result);

		result = calculate(calculator, Constants.ONE, Constants.MULTIPLICATION, Constants.FIVE);

		assertEquals(Constants.FIVE, result);
	}

}
