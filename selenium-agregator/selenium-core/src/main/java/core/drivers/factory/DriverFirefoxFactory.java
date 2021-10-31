package core.drivers.factory;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;

import core.configuration.Configurations;
import core.drivers.DriverOptionsException;
import core.mobile.utils.MobileOptions;

public class DriverFirefoxFactory implements DriverFactory {

	private static final String METHOD_NOT_IMPLEMENTED = "Method not implemented!";

	private WebDriver createDriverFirefoxOptions(FirefoxOptions options) {

		options.setHeadless(Configurations.getInstance().getHeadless());

		if (Configurations.getInstance().isRemoteExecution()) {
			return remoteDriver(options);
		}
		return new FirefoxDriver(options);
	}

	@Override
	public WebDriver createDriver(MobileOptions mobileOptions) {
		throw new DriverOptionsException(METHOD_NOT_IMPLEMENTED);
	}

	@Override
	public WebDriver createDriver(String deviceName) {
		throw new DriverOptionsException(METHOD_NOT_IMPLEMENTED);
	}

	@Override
	public WebDriver createDriver(AbstractDriverOptions<?> driverOptions) {
		return createDriverFirefoxOptions((FirefoxOptions) driverOptions);
	}

	@Override
	public WebDriver createDriver() {
		return createDriverFirefoxOptions(new FirefoxOptions());
	}

}
