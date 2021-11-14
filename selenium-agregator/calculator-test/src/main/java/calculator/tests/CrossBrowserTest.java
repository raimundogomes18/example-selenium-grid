package calculator.tests;

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
class CrossBrowserTest extends CalculatorBaseTest {

	@Test
	void firefoxTest() {
		calculatorTest(new CalculatorPageObject(new FirefoxOptions()));
	}

	@Test
	void chrome1024x768Test() {
		ChromeOptions options = new ChromeOptions();
		options.setCapability(CapabilityType.APPLICATION_NAME, "chrome-1024x768");
		CalculatorPageObject calculator = new CalculatorPageObject(options);

		calculatorTest(calculator);
	}

	@Test
	void chromeTest() {
		calculatorTest(new CalculatorPageObject(new ChromeOptions()));
	}

	@Test
	void edgeTest() {
		calculatorTest(new CalculatorPageObject(new EdgeOptions()));
	}
}
