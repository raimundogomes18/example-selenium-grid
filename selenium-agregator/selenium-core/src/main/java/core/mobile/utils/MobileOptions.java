package core.mobile.utils;

public class MobileOptions {
	
	public static final String DEVICE_NAME = "deviceName";
	
	public static final String MOBILE_EMULATION = "mobileEmulation";
	
	public static final String DEVICE_METRICS = "deviceMetrics";
	
	private String browserName = "";

	private String deviceName = "";
	
	private DeviceMetrics deviceMetrics;
	
	public MobileOptions withBrowserName(String value) {
		this.browserName = value;
		return this;
	}
	
	public MobileOptions withDeviceName(String value) {
		this.deviceName = value;
		return this;
	}
	
	public String getBrowserName() {
		return browserName;
	}
	
	public String getDeviceName() {
		return deviceName;
	}

	public MobileOptions withDeviceMetrics(DeviceMetrics deviceMetrics) {
		this.deviceMetrics  = deviceMetrics;
		return this;
	}

	public DeviceMetrics getDeviceMetrics() {
		return deviceMetrics;
	}
	
	
	
}
