package core.drivers.factory;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;

import core.configuration.Configurations;
import core.drivers.DriverOptionsException;
import core.mobile.utils.MobileOptions;

public class DriverEdgeFactory implements DriverFactory {

	private static final String METHOD_NOT_IMPLEMENTED = "Method not implemented!";

	private WebDriver createDriverEdgeOptions(EdgeOptions options) {

		options.setHeadless(Configurations.getInstance().getHeadless());

		if (Configurations.getInstance().isRemoteExecution()) {
			return remoteDriver(options);
		}
		return new EdgeDriver(options);
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
		return createDriverEdgeOptions((EdgeOptions) driverOptions);
	}

	@Override
	public WebDriver createDriver() {
		return createDriverEdgeOptions(new EdgeOptions());
	}

}
