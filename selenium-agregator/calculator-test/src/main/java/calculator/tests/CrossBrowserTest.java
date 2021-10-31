package calculator.tests;

import java.net.MalformedURLException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.CapabilityType;

import calculator.pageobjects.CalculatorPageObject;

@Execution(ExecutionMode.CONCURRENT)
class CrossBrowserTest extends CalculatorBaseTest {

	@Test
	void firefoxTest() throws MalformedURLException {
		CalculatorPageObject calculator = new CalculatorPageObject();
		calculator.setConfiguration(new FirefoxOptions());

		calculatorTest(calculator);
	}

	@Test
	void chrome1024x768Test() {
		ChromeOptions options = new ChromeOptions();
		options.setCapability(CapabilityType.APPLICATION_NAME, "chrome-1024x768");
		CalculatorPageObject calculator = new CalculatorPageObject();

		calculator.setConfiguration(options);

		calculatorTest(calculator);
	}

	@Test
	void chromeTest() throws MalformedURLException {
		CalculatorPageObject calculator = new CalculatorPageObject();
		calculator.setConfiguration(new ChromeOptions());

		calculatorTest(calculator);
	}

	@Test
	void operaTest() throws MalformedURLException {
		CalculatorPageObject calculator = new CalculatorPageObject();
		calculator.setConfiguration(new OperaOptions());

		calculatorTest(calculator);
	}

}
