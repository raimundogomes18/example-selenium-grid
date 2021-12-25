package calculator.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.openqa.selenium.remote.Browser;

import calculator.pageobjects.CalculatorPageObject;
import core.mobile.utils.DeviceMetrics;
import core.mobile.utils.DeviceNames;
import core.mobile.utils.MobileOptions;

@Execution(ExecutionMode.CONCURRENT)
@TestInstance(value = Lifecycle.PER_METHOD)
class CalculatorEmulationMobileTest extends CalculatorBaseTest {

	private static final String CHROME_BROWSER_NAME = Browser.CHROME.browserName();

	@Test
	void galaxyS5Test() {
		MobileOptions mobileOptions = new MobileOptions().withBrowserName(CHROME_BROWSER_NAME)
				.withDeviceName(DeviceNames.GALAXY_S5);
		CalculatorPageObject calculator = new CalculatorPageObject(mobileOptions);

		calculatorTest(calculator);

	}

	@Test
	void iPhone6_7_8_PlusTest() {
		MobileOptions mobileOptions = new MobileOptions().withBrowserName(CHROME_BROWSER_NAME)
				.withDeviceName(DeviceNames.IPHONE_6_7_8_PLUS);
		CalculatorPageObject calculator = new CalculatorPageObject(mobileOptions);

		calculatorTest(calculator);
	}

	@Test
	void iPhone6_7_8_Test() {
		CalculatorPageObject calculator = new CalculatorPageObject(
				new MobileOptions().withBrowserName(CHROME_BROWSER_NAME).withDeviceName(DeviceNames.IPHONE_6_7_8));

		calculatorTest(calculator);
	}

	@Test
	void iPhoneXTest() {
		CalculatorPageObject calculator = new CalculatorPageObject(
				new MobileOptions().withBrowserName(CHROME_BROWSER_NAME).withDeviceName(DeviceNames.IPHONE_X));

		calculatorTest(calculator);
	}

	@Test
	void iPadTest() {
		CalculatorPageObject calculator = new CalculatorPageObject(
				new MobileOptions().withBrowserName(CHROME_BROWSER_NAME).withDeviceName(DeviceNames.IPAD));

		calculatorTest(calculator);
	}

	@Test
	void motoG4Test() {
		CalculatorPageObject calculator = new CalculatorPageObject(
				new MobileOptions().withBrowserName(CHROME_BROWSER_NAME).withDeviceName(DeviceNames.MOTO_G4));
		calculatorTest(calculator);
	}

	@Test
	void nexus5XTest() {

		CalculatorPageObject calculator = new CalculatorPageObject(
				new MobileOptions().withBrowserName(CHROME_BROWSER_NAME).withDeviceName(DeviceNames.NEXUS_5X));

		calculatorTest(calculator);
	}

	@Test
	void deviceMetricsWidht414AndHeight846Test() {

		DeviceMetrics deviceMetrics = new DeviceMetrics().withWidth(414).withHeight(846);

		MobileOptions options = new MobileOptions().withBrowserName(CHROME_BROWSER_NAME)
				.withDeviceMetrics(deviceMetrics);

		CalculatorPageObject calculator = new CalculatorPageObject(options);
		calculatorTest(calculator);
	}
}