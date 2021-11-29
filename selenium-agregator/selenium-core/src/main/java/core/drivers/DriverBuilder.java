package core.drivers;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.Browser;

import core.configuration.Configurations;
import core.drivers.factory.DriverChromeFactory;
import core.drivers.factory.DriverEdgeFactory;
import core.drivers.factory.DriverFirefoxFactory;
import core.drivers.factory.DriverOperaFactory;
import core.mobile.utils.MobileOptions;

public class DriverBuilder {
	
	private static final List<String> SUPPORTED_BROWSERS = Arrays.asList(
			Browser.CHROME.browserName(),
			Browser.FIREFOX.browserName(),
			Browser.OPERA.browserName(),
			Browser.EDGE.browserName());
	
	public WebDriver createDriver(MobileOptions options) {
	
		verifyBrowserName(options.getBrowserName());

		if (Browser.FIREFOX.browserName().equalsIgnoreCase(options.getBrowserName())) {
			return new DriverFirefoxFactory().createDriver(options);
		}

		if (Browser.CHROME.browserName().equalsIgnoreCase(options.getBrowserName())) {
			return new DriverChromeFactory().createDriver(options);
		}

		throw new DriverOptionsException("Not implemented: " + options.getBrowserName());
	}

	protected void verifyBrowserName(String browserName) {
		if (StringUtils.isEmpty(browserName)) {
			throw new DriverOptionsException("browserName is required.");
		}
		
		if(!SUPPORTED_BROWSERS.contains(browserName)) {
			throw new DriverOptionsException("browser not supported.");
		}
	}

	public WebDriver createDriver(AbstractDriverOptions<?> options) {
		
		if (options instanceof FirefoxOptions) {
			return new DriverFirefoxFactory().createDriver(options);
		}

		if (options instanceof OperaOptions) {
			return new DriverOperaFactory().createDriver(options);
		}

		if (options instanceof ChromeOptions) {
			return new DriverChromeFactory().createDriver(options);
		}
		
		if (options instanceof EdgeOptions) {
			return new DriverEdgeFactory().createDriver(options);
		}

		throw new DriverOptionsException(
				"Driver Options not implemented. " + options.getClass().getName());
	}
	
	/**
	 * Method returns default driver.
	 * @return WebDriver defined in property browser.default of the configuration.properties file.
	 */
	public WebDriver getDriver() {
		String browserName = Configurations.getInstance().getBrowserDefault();
		
		return createDriver(browserName);
	}

	private WebDriver createDriver(String browserName) {
		verifyBrowserName(browserName);
		
		if (Browser.FIREFOX.browserName().equalsIgnoreCase(browserName)) {
			return new DriverFirefoxFactory().createDriver();
		}

		if (Browser.CHROME.browserName().equalsIgnoreCase(browserName)) {
			return new DriverChromeFactory().createDriver();
		}

		if (Browser.EDGE.browserName().equalsIgnoreCase(browserName)) {
			return new DriverEdgeFactory().createDriver();
		}
		
		if (Browser.OPERA.browserName().equalsIgnoreCase(browserName)) {
			return new DriverOperaFactory().createDriver();
		}
		
		throw new DriverOptionsException(
				"Driver Name not implemented. " + browserName);
	}
}
