package core.drivers.factory;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.CapabilityType;

import core.configuration.Configurations;
import core.drivers.DriverOptionsException;
import core.mobile.utils.MobileOptions;

public class DriverOperaFactory implements DriverFactory {

	@Override
	public WebDriver createDriver(MobileOptions options) {
		throw new DriverOptionsException("Method not implemented!");
	}

	@Override
	public WebDriver createDriver(String deviceName) {
		throw new DriverOptionsException("Method not implemented!");
	}

	@Override
	public WebDriver createDriver(AbstractDriverOptions<?> driverOptions) {
		return createDriverWithOperaOptions((OperaOptions) driverOptions);
	}
	
	private WebDriver createDriverWithOperaOptions(OperaOptions driverOptions) {
		driverOptions.setCapability(CapabilityType.BROWSER_NAME, "operablink");
		
		if (Configurations.getInstance().isRemoteExecution()) {
			return remoteDriver(driverOptions);
		}
		return new OperaDriver(driverOptions);
	}

	@Override
	public WebDriver createDriver() {
		return createDriverWithOperaOptions(new OperaOptions());
	}


}
