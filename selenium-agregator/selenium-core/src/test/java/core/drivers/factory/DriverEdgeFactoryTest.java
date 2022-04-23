package core.drivers.factory;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.Browser;

import core.drivers.DriverOptionsException;
import core.mobile.utils.DeviceMetrics;
import core.mobile.utils.DeviceNames;
import core.mobile.utils.MobileOptions;

@Execution(ExecutionMode.CONCURRENT)
@TestInstance(Lifecycle.PER_METHOD)
class DriverEdgeFactoryTest {

	private EdgeOptions options;
	private DriverEdgeFactory driverFactory;

	@BeforeEach
	void setUp() throws Exception {
		options = new EdgeOptions();
		driverFactory = new DriverEdgeFactory();
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
		
		System.setProperty("REMOTE_URL","url_that_not_exists");
		
		assertThrows(DriverOptionsException.class, () -> {
			driverFactory.remoteDriver(options);
		});

	}
	

}
