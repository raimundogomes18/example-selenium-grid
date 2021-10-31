package core.drivers.options;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.Browser;


class ChromeOptionsBuilderTest {

	@Test
	void testGetChromeOptionsDefault() {
		 ChromeOptions options = new ChromeOptionsBuilder().getChromeOptionsDefault();
		 
		 assertEquals( Browser.CHROME.browserName(), options.getBrowserName());
		 
		 assertEquals( PageLoadStrategy.NORMAL, options.getCapability("pageLoadStrategy"));
		 
	}
	
	@Test
	void testGetChromeOptions() {
		 ChromeOptions options = new ChromeOptionsBuilder().getChromeOptions();
		 
		 assertEquals( Browser.CHROME.browserName(), options.getBrowserName());
		 
		 assertEquals( null, options.getCapability("pageLoadStrategy"));
		 
	}

}
