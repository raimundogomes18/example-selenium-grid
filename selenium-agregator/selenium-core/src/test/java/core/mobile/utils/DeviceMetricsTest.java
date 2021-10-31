package core.mobile.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import core.drivers.DriverOptionsException;

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
