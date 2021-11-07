package core.drivers.factory;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;
import org.openqa.selenium.chrome.ChromeOptions;

import core.drivers.DriverOptionsException;
import core.mobile.utils.DeviceMetrics;
import core.mobile.utils.MobileOptions;

@Execution(ExecutionMode.CONCURRENT)
@TestInstance(Lifecycle.PER_METHOD)
class DriverChromeFactoryTest {

	@Test
	@ResourceLock(value = Resources.SYSTEM_PROPERTIES, mode = ResourceAccessMode.READ_WRITE)
	void testMalFormedUrlTest() {
		
		System.setProperty("REMOTE_URL","malformedurl");
		
		DriverChromeFactory driverChromeFactory = new DriverChromeFactory();
		
		ChromeOptions options = new ChromeOptions();
		
		assertThrows(DriverOptionsException.class, () -> {
			driverChromeFactory.remoteDriver(options);
		});

	}
	
	@Test
	@ResourceLock(value = "global", mode = ResourceAccessMode.READ_WRITE)
	void testMobileEmpty() {
		
		DriverFactory driverChromeFactory = new DriverChromeFactory();
		
		MobileOptions options = new MobileOptions();
		options.withDeviceName("");
		options.withDeviceMetrics(new DeviceMetrics());
		
		assertThrows(DriverOptionsException.class, () -> {
			driverChromeFactory.createDriver(options);
		});

	}
	
	@Test
	void testDeviceNameEmptyDriverOptionsException() {
		
		DriverChromeFactory driverChromeFactory = new DriverChromeFactory();
		
		assertThrows(DriverOptionsException.class, () -> {
			driverChromeFactory.createDriver("");
		});

	}

}
