package core.drivers.factory;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.Browser;

import core.drivers.DriverOptionsException;
import core.drivers.options.ChromeOptionsBuilder;
import core.mobile.utils.MobileOptions;

public class DriverChromeFactory extends DriverAbstractFactory {

	public WebDriver createDriver(MobileOptions options) {

		if (StringUtils.isEmpty(options.getDeviceName()) && options.getDeviceMetrics().isEmpty()) {
			throw new DriverOptionsException("MobileOptions empty!");
		}

		ChromeOptions chromeOptions = new ChromeOptionsBuilder().getChromeOptionsDefault();

		Map<String, Object> mobileEmulation = new HashMap<String, Object>();

		if (StringUtils.isNotEmpty(options.getDeviceName())) {
			mobileEmulation.put(MobileOptions.DEVICE_NAME, options.getDeviceName());
		}

		if (options.getDeviceMetrics() != null) {

			mobileEmulation.put(MobileOptions.DEVICE_METRICS, options.getDeviceMetrics().getMapDeviceMetrics());

			mobileEmulation.put("userAgent",
					"Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 5 Build/JOP40D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19");

		}

		chromeOptions.setExperimentalOption(MobileOptions.MOBILE_EMULATION, mobileEmulation);

		return getDriverChrome(chromeOptions);
	}

	public WebDriver createDriver(String deviceName) {
		if (StringUtils.isEmpty(deviceName)) {
			throw new DriverOptionsException("Device name empty!");
		}

		MobileOptions options = new MobileOptions().withBrowserName(Browser.CHROME.browserName())
				.withDeviceName(deviceName);

		return createDriver(options);
	}

	public WebDriver getDriverChrome(ChromeOptions options) {

		if (isExecutionRemote()) {
			return remoteDriver(options);
		}

		return new ChromeDriver(options);

	}

	@Override
	public WebDriver createDriver(AbstractDriverOptions<?> driverOptions) {
		return getDriverChrome((ChromeOptions) driverOptions);
	}

	public WebDriver createDriver() {
		return getDriverChrome(new ChromeOptionsBuilder().getChromeOptionsDefault());
	}
}
