package core.drivers.options;

import java.util.Arrays;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

import core.configuration.Configurations;

public class ChromeOptionsBuilder {
	
	public static final String DISABLE_DEV_SHM_USAGE = "--disable-dev-shm-usage";
	public static final String DISABLE_EXTENSIONS = "--disable-extensions";
	public static final String DISABLE_GPU = "--disable-gpu";
	public static final String DISABLE_INFOBARS = "disable-infobars";
	public static final String DISABLE_POPUP_BLOCKING = "--disable-popup-blocking";
	public static final String DISABLE_WEB_SECURITY = "--disable-web-security";
	public static final String IGNORE_CERTIFICATE_ERRORS = "ignore-certificate-errors";
	public static final String NO_SANDBOX = "--no-sandbox";
	public static final String START_MAXIMIZED = "start-maximized";
	
	
	private ChromeOptions options = new ChromeOptions();
	
	public ChromeOptionsBuilder acceptInsecureCerts() {
		this.options.setAcceptInsecureCerts(true);
		return this;
	}
	
	public ChromeOptionsBuilder browserName(String name) {
		this.options.setCapability(CapabilityType.BROWSER_NAME, name);
		return this;
	}
	
	public ChromeOptionsBuilder browserVersion(String version) {
		this.options.setCapability(CapabilityType.BROWSER_VERSION, version);
		return this;
	}
	
	public ChromeOptionsBuilder disableDevShmUsage() {
		options.addArguments(DISABLE_DEV_SHM_USAGE);
		return this;
	}
	
	public ChromeOptionsBuilder disableExtensions() {
		options.addArguments(DISABLE_EXTENSIONS);
		return this;
	}
	
	public ChromeOptionsBuilder disableGPU() {
		options.addArguments(DISABLE_GPU);
		return this;
	}
	
	public ChromeOptionsBuilder disableInfoBars() {
		options.addArguments(DISABLE_INFOBARS);
		return this;
	}
	
	public ChromeOptionsBuilder disablePopupBlocking() {
		options.setExperimentalOption("excludeSwitches",  Arrays.asList("disable-popup-blocking"));
		return this;
	}
	
	public ChromeOptionsBuilder disableWebSecurity() {
		options.addArguments(DISABLE_WEB_SECURITY);
		return this;
	}
	
	public ChromeOptions getChromeOptions() {
		return options;
	}
	
	public ChromeOptions getChromeOptionsDefault() {
		ignoreCertificateErrors();
		disablePopupBlocking();
		disableWebSecurity();
		startMaximized();
		disableInfoBars();
		options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
		
		options.setHeadless(Configurations.getInstance().getHeadless());
		
		return options;
	}
	
	public ChromeOptionsBuilder proxy(Proxy proxy) {
		this.options.setProxy(proxy);
		return this;
	} 
	
	
	public ChromeOptionsBuilder headless() {
		this.options.setHeadless(true);
		return this;
	}
	
	public ChromeOptionsBuilder ignoreCertificateErrors() {
		this.options.addArguments(IGNORE_CERTIFICATE_ERRORS);
		return this;
	}
	
	public ChromeOptionsBuilder noSandbox() {
		options.addArguments(NO_SANDBOX); // Bypass OS security model
		return this;
	}
	
	public ChromeOptionsBuilder plataformName(String name) {
		this.options.setCapability(CapabilityType.PLATFORM_NAME, name);
		return this;
	}
	
	public ChromeOptionsBuilder startMaximized() {
		options.addArguments(START_MAXIMIZED);
		return this;
	}
	
	public ChromeOptionsBuilder withCapability(String capabilityType, String value) {
		this.options.setCapability(capabilityType, value);
		return this;
	}
	
}
