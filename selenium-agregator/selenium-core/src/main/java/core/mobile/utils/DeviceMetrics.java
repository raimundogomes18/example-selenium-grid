package core.mobile.utils;

import java.util.HashMap;
import java.util.Map;

import core.drivers.DriverOptionsException;

public class DeviceMetrics {
	
	private int width ;
	
	private int height;	
	private int pixelRatio;
	
	public Map<String, Object> getMapDeviceMetrics() {
		if(isEmpty()) {
			throw new DriverOptionsException("width and height must have value");
		}

		Map<String, Object> deviceMetrics = new HashMap<String, Object>();
		
		deviceMetrics.put("width", width);

		deviceMetrics.put("height", height);
		if(pixelRatio !=0) {
			deviceMetrics.put("pixelRatio", pixelRatio);
		}
		
		return deviceMetrics;
	}
	
	public boolean isEmpty() {
		if(width==0 || height==0) {
			return true;
		}
		
		return false;
	}
	
	public DeviceMetrics withWidth(int width) {
		this.width = width;
		
		return this;
	}
	
	public DeviceMetrics withHeight(int height) {
		this.height = height;
		
		return this;
	}
	
	public DeviceMetrics withPixelRatio(int pixelRatio) {
		this.pixelRatio = pixelRatio;
		
		return this;
	}
	

}
