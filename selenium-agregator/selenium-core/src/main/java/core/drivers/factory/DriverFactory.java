package core.drivers.factory;


import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import core.configuration.Configurations;
import core.drivers.DriverOptionsException;
import core.mobile.utils.MobileOptions;

public interface DriverFactory {
	
	WebDriver createDriver();
	
	WebDriver createDriver(MobileOptions options);

	WebDriver createDriver(String deviceName);
	
	WebDriver createDriver(AbstractDriverOptions<?> driverOptions);
	
	default WebDriver remoteDriver(AbstractDriverOptions<?> options) {
		try {
			RemoteWebDriver remote = new RemoteWebDriver(new URL(Configurations.getInstance().getRemoteUrl()), options);
			remote.setFileDetector(new LocalFileDetector());
			return  remote;
		} catch (MalformedURLException e) {
			throw new DriverOptionsException(e);
		}
	}
}
