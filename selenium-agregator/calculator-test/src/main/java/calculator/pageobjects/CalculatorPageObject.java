package calculator.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.AbstractDriverOptions;

import core.configuration.Configurations;
import core.drivers.DriverBuilder;
import core.mobile.utils.MobileOptions;

public class CalculatorPageObject {

	private WebDriver driver;

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
		driver.get(Configurations.getInstance().getUrl());
	}

	public String getResult() {
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
		driver.findElement(By.xpath(xpath)).click();
	}

}
