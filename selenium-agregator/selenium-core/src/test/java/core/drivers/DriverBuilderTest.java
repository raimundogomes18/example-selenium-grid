package core.drivers;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;
import org.openqa.selenium.remote.Browser;
import org.openqa.selenium.safari.SafariOptions;

import core.configuration.Configurations;
import core.mobile.utils.DeviceMetrics;
import core.mobile.utils.MobileOptions;

class DriverBuilderTest {
	
	private DriverBuilder driver;
	
	private MobileOptions options = new MobileOptions();
	
	@BeforeEach
	void setup() {
		driver = new DriverBuilder();
		options = new MobileOptions();
	}

	@AfterEach
	void tearDown() {
		System.clearProperty(Configurations.DEFAULT_BROWSER.replace(".", "_").toUpperCase());
	}
	
	@Test
	void testBrowserOptionsNotSupported() {
		
		SafariOptions options = new SafariOptions();
		
		assertThrows(DriverOptionsException.class, () -> {
			driver.createDriver(options);
		});
	}
	
	@Test
	void testBrowserNameDriverOptionsException() {

		assertThrows(DriverOptionsException.class, () -> {
			driver.createDriver(options);
		});
	}
	
	@Test
	void testMobileOptionsDriverOptionsException() {
		DriverBuilder driver = new DriverBuilder(); 
		
		MobileOptions options = new MobileOptions();
		
		options.withBrowserName(Browser.FIREFOX.browserName());
		
		assertThrows(DriverOptionsException.class, () -> {
			driver.createDriver(options);
		});
		
		
	}
	
	@Test
	void testMobileOptionsBrowserNameDriverOptionsException() {
		DriverBuilder driver = new DriverBuilder(); 
		
		MobileOptions options = new MobileOptions();
		
		options.withBrowserName(Browser.EDGE.browserName());
		
		assertThrows(DriverOptionsException.class, () -> {
			driver.createDriver(options);
		});
		
		
	}
	
	@Test
	void testDeviceNameEmptyDriverOptionsException() {
		
		
		options.withDeviceName("");
		
		options.withBrowserName(Browser.FIREFOX.browserName());
		
		assertThrows(DriverOptionsException.class, () -> {
			driver.createDriver(options);
		});
	}
	
	@Test
	void testDeviceMetricsOnlyHeightDriverOptionsException() {
		
		
		options.withDeviceMetrics(new DeviceMetrics().withHeight(100));
		
		options.withBrowserName(Browser.FIREFOX.browserName());
		
		assertThrows(DriverOptionsException.class, () -> {
			driver.createDriver(options);
		});
	}
	
	@Test
	void testDeviceMetricsEmptyDriverOptionsException() {
		
		DriverBuilder driver = new DriverBuilder(); 
		
		MobileOptions options = new MobileOptions();
		
		options.withDeviceMetrics(new DeviceMetrics());
		
		options.withBrowserName(Browser.FIREFOX.browserName());
		
		assertThrows(DriverOptionsException.class, () -> {
			driver.createDriver(options);
		});
	}
	
	@Test
	@ResourceLock(value = Resources.SYSTEM_PROPERTIES, mode = ResourceAccessMode.READ_WRITE)
	void testCreateBrowserNameNotSupported() {
		
		System.setProperty(Configurations.DEFAULT_BROWSER.replace(".", "_").toUpperCase(), "safari");
		
		assertThrows(DriverOptionsException.class, () -> {
			driver.getDriver();
		});
		
	}
	
	@Test
	@ResourceLock(value = Resources.SYSTEM_PROPERTIES, mode = ResourceAccessMode.READ_WRITE)
	void testCreateBrowserNameEmpty() {
		
		assertThrows(DriverOptionsException.class, () -> {
			driver.createDriver(new MobileOptions().withBrowserName(""));
		});
		
	}
	
}
