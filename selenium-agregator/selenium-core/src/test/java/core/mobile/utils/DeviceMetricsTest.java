package core.mobile.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import core.drivers.DriverOptionsException;

@Execution(ExecutionMode.CONCURRENT)
@TestInstance(Lifecycle.PER_METHOD)
class DeviceMetricsTest {


	@Test
	void testGetMapDeviceMetrics() {
		
		Map<String, Object> expected = new HashMap<String, Object>();
		
		expected.put("height", 600);
		expected.put("width", 200);
		
		Map<String, Object> mapDeviceMetrics = new DeviceMetrics().withHeight(600).withWidth(200).getMapDeviceMetrics();
		
		Assertions.assertEquals(expected, mapDeviceMetrics);
	}
	
	@Test
	void testGetMapDeviceMetricsPixelZero() {
		
		Map<String, Object> expected = new HashMap<String, Object>();
		
		expected.put("height", 600);
		expected.put("width", 200);
		
		Map<String, Object> mapDeviceMetrics = new DeviceMetrics()
				.withHeight(600)
				.withWidth(200)
				.withPixelRatio(0)
				.getMapDeviceMetrics();
		
		Assertions.assertEquals(expected, mapDeviceMetrics);
	}
	
	@Test
	void testGetMapDeviceMetricsPixelNotEmpty() {
		
		Map<String, Object> expected = new HashMap<String, Object>();
		
		expected.put("height", 600);
		expected.put("width", 200);
		expected.put("pixelRatio", 1);
		
		Map<String, Object> mapDeviceMetrics = new DeviceMetrics()
				.withHeight(600)
				.withWidth(200)
				.withPixelRatio(1)
				.getMapDeviceMetrics();
		
		Assertions.assertEquals(expected, mapDeviceMetrics);
	}
	
	@Test
	void testGetMapDeviceMetricsEmpty() {
		
		DeviceMetrics deviceMetrics = new DeviceMetrics();
		
		assertThrows(DriverOptionsException.class, () -> {
			deviceMetrics.getMapDeviceMetrics();
		});
	}

}
