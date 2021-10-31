package core.drivers.factory;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.Browser;

import core.drivers.DriverOptionsException;
import core.mobile.utils.DeviceMetrics;
import core.mobile.utils.DeviceNames;
import core.mobile.utils.MobileOptions;

class DriverOperaFactoryTest {

	private OperaOptions options;
	private DriverOperaFactory driverFactory;

	@BeforeEach
	void setUp() throws Exception {
		options = new OperaOptions();
		driverFactory = new DriverOperaFactory();
	}

	@Test
	void testCreateDriverMobileOptions() {
		MobileOptions options = new MobileOptions();
		
		options.withBrowserName(Browser.OPERA.browserName());
		
		options.withDeviceMetrics(new DeviceMetrics().withHeight(100).withPixelRatio(1).withWidth(200));
		
		assertThrows(DriverOptionsException.class, () -> {
			driverFactory.createDriver(options);
		});
	}
	
	@Test
	void testCreateDriverDeviceName() {
		
		assertThrows(DriverOptionsException.class, () -> {
			driverFactory.createDriver(DeviceNames.IPAD);
		});
	}

	
	@Test
	@ResourceLock(value = "global", mode = ResourceAccessMode.READ_WRITE)
	void testUrlMalformed() {
		
		System.setProperty("REMOTE_URL","dsdcsfds");
		
		assertThrows(DriverOptionsException.class, () -> {
			driverFactory.remoteDriver(options);
		});

	}
	

}
