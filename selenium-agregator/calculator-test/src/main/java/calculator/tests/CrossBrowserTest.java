package calculator.tests;

import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;

import calculator.pageobjects.CalculatorPageObject;

@Execution(ExecutionMode.CONCURRENT)
@TestInstance(Lifecycle.PER_METHOD)
class CrossBrowserTest  {

	private Logger logger = Logger.getLogger(getClass().getName());

	private CalculatorBaseTest calculatorBaseTest =  new CalculatorBaseTest();

	@Test
	void firefoxTest() {
		logger.info("Start of test in  Firefox");
		calculatorBaseTest.calculatorTest(new CalculatorPageObject(new FirefoxOptions()));
		logger.info("End of test in  Firefox");
	}

	@Test
	void chrome1024x768Test() {
		logger.info("Start of test in  Chrome-1024x768");
		ChromeOptions options = new ChromeOptions();
		options.setCapability(CapabilityType.APPLICATION_NAME, "chrome-1024x768");
		CalculatorPageObject calculator = new CalculatorPageObject(options);

		calculatorBaseTest.calculatorTest(calculator);
		logger.info("End of test in  Chrome-1024x768");
	}

	@Test
	void chromeTest() {
		logger.info("Start of test in  Chrome");
		calculatorBaseTest.calculatorTest(new CalculatorPageObject(new ChromeOptions()));
		logger.info("End of test in  Chrome");
	}

	@Test
	void edgeTest() {
		logger.info("Start of test in  EDGE");
		calculatorBaseTest.calculatorTest(new CalculatorPageObject(new EdgeOptions()));
		logger.info("End of test in  EDGE");
	}
}
