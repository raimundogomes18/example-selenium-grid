package core.drivers.factory;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;
import org.openqa.selenium.firefox.FirefoxOptions;

import core.drivers.DriverOptionsException;
import core.mobile.utils.DeviceNames;
import core.mobile.utils.MobileOptions;

class DriverFirefoxFactoryTest {


	@Test
	void testCreateDriverMobileOptions() {
		DriverFactory driverFactory = new DriverFirefoxFactory();
		MobileOptions options = new MobileOptions();
		assertThrows(DriverOptionsException.class, () -> {
			
			driverFactory.createDriver(options);
		});
	}

	@Test
	@ResourceLock(value = Resources.SYSTEM_PROPERTIES, mode = ResourceAccessMode.READ_WRITE)
	void testCreateDriverDeviceName() {
		System.setProperty("REMOTE_URL", "");
		DriverFactory driverFactory = new DriverFirefoxFactory();

		assertThrows(DriverOptionsException.class, () -> {
			driverFactory.createDriver(DeviceNames.IPHONE_6_7_8_PLUS);
		});
	}

	@Test
	@ResourceLock(value = Resources.SYSTEM_PROPERTIES, mode = ResourceAccessMode.READ_WRITE)
	void testCreateDriverWithMalFormedUrl() {

		System.setProperty("REMOTE_URL", "malformedurl");
		
		System.setProperty("REMOTE_EXECUTION", "true");

		DriverFactory driverFactory = new DriverFirefoxFactory();

		FirefoxOptions options = new FirefoxOptions();

		assertThrows(DriverOptionsException.class, () -> {
			driverFactory.createDriver(options);
		});

	}

}
