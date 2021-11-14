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

public abstract class DriverAbstractFactory implements DriverFactory {

	private static final String METHOD_NOT_IMPLEMENTED = "Method not implemented!";

	@Override
	public WebDriver createDriver(MobileOptions mobileOptions) {
		throw new DriverOptionsException(METHOD_NOT_IMPLEMENTED);
	}

	@Override
	public WebDriver createDriver(String deviceName) {
		throw new DriverOptionsException(METHOD_NOT_IMPLEMENTED);
	}

    protected WebDriver remoteDriver(AbstractDriverOptions<?> options) {
		try {
			RemoteWebDriver remote = new RemoteWebDriver(new URL(Configurations.getInstance().getRemoteUrl()), options);
			remote.setFileDetector(new LocalFileDetector());
			return  remote;
		} catch (MalformedURLException e) {
			throw new DriverOptionsException(e);
		}
	}

	protected boolean isExecutionRemote() {
		return Configurations.getInstance().isRemoteExecution();
	}
}
