package calculator.pageobjects;

import java.text.MessageFormat;
import java.time.Duration;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.AbstractDriverOptions;

import core.configuration.Configurations;
import core.drivers.DriverBuilder;
import core.mobile.utils.MobileOptions;

public class CalculatorPageObject {

	private WebDriver driver;
	
	protected Logger logger = Logger.getLogger(getClass().getName());

	public CalculatorPageObject(AbstractDriverOptions<?> options) {
		this.driver = new DriverBuilder().createDriver(options);
	}
	
	public CalculatorPageObject(MobileOptions mobileOptions) {
		this.driver = new DriverBuilder().createDriver(mobileOptions);
	}

	public CalculatorPageObject() {
		this.driver = new DriverBuilder().getDriver();
	}

	public void close() {
		driver.quit();

	}

	public void acessCalculator() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		logger.info(MessageFormat.format("Access app: {0}", Configurations.getInstance().getUrl()));
		driver.get(Configurations.getInstance().getUrl());
	}

	public String getResult() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		return driver.findElement(By.xpath("//div[@class='component-display']/div")).getText();
	}

	public void clickButton(String value) {
		if (Constants.ZERO.equals(value)) {
			clickBy("//div[@class='component-button  wide']/button");
		} else {
			clickBy("//div[@class='component-button' or @class='component-button orange']/button[text()='" + value
					+ "']");
		}
	}

	public void clickBy(String xpath) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.findElement(By.xpath(xpath)).click();
	}

}
