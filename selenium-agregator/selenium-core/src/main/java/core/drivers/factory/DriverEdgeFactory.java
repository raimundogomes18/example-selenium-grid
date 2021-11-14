package core.drivers.factory;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;

import core.configuration.Configurations;


public class DriverEdgeFactory extends DriverAbstractFactory {

	private WebDriver createDriverEdgeOptions(EdgeOptions options) {

		options.setHeadless(Configurations.getInstance().getHeadless());
 
		if (isExecutionRemote()) {
			return remoteDriver(options);
		}
		return new EdgeDriver(options);
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
