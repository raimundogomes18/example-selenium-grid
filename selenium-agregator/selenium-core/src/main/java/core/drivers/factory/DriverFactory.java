package core.drivers.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.AbstractDriverOptions;

import core.mobile.utils.MobileOptions;

public interface DriverFactory {

	WebDriver createDriver();

	WebDriver createDriver(MobileOptions options);

	WebDriver createDriver(String deviceName);

	WebDriver createDriver(AbstractDriverOptions<?> driverOptions);

}
