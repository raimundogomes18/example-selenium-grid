package core.drivers.factory;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;

public class DriverOperaFactory  extends DriverAbstractFactory{

	@Override
	public WebDriver createDriver(AbstractDriverOptions<?> driverOptions) {
		return createDriverWithOperaOptions((OperaOptions) driverOptions);
	}
	
	private WebDriver createDriverWithOperaOptions(OperaOptions driverOptions) {
		
		if (isExecutionRemote()) {
			return remoteDriver(driverOptions);
		}
		
		return new OperaDriver(driverOptions);
	}

	@Override
	public WebDriver createDriver() {
		return createDriverWithOperaOptions(new OperaOptions());
	}


}
