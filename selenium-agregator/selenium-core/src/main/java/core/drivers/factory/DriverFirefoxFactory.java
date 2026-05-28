package core.drivers.factory;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;

import core.configuration.Configurations;

public class DriverFirefoxFactory extends DriverAbstractFactory {

	private WebDriver createDriverFirefoxOptions(FirefoxOptions options) {

		if (Boolean.TRUE.equals(Configurations.getInstance().getHeadless())) {
			options.addArguments("-headless");
		}

		if (Configurations.getInstance().isRemoteExecution()) {
			return remoteDriver(options);
		}
		return new FirefoxDriver(options);
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
