package core.configuration;


import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;

@TestMethodOrder(MethodOrderer.MethodName.class)
class ConfigurationsTest {
	

	private static final String REMOTE_URL_EXPECTED = "http://localhost:4444/wd/hub";
	private static final String REMOTE_EXECUTION = "REMOTE_EXECUTION";
	private static final String REMOTE_URL = "REMOTE_URL";

	@Test
	void testConfigurationRemoteExecution() {
		
		
		String configurationResult = Configurations.getInstance().getConfiguration(Configurations.REMOTE_EXECUTION);
		
		Assertions.assertEquals( "true", configurationResult);
		
	}
	
	@Test
	@ResourceLock(value = Resources.SYSTEM_PROPERTIES, mode = ResourceAccessMode.READ_WRITE)
	void testConfigurationRemoteUrlWithVariableEnvironmentValue() {
		
		System.setProperty(REMOTE_URL, REMOTE_URL_EXPECTED);
		
		String url = System.getProperty(REMOTE_URL);
		
		String configurationResult = Configurations.getInstance(true).getConfiguration(Configurations.REMOTE_URL);
		
		Assertions.assertEquals( configurationResult, url);
		
	}
	
	@Test
	@ResourceLock(value = Resources.SYSTEM_PROPERTIES, mode = ResourceAccessMode.READ_WRITE)
	void testConfigurationRemoteUrl() {
		
		 System.setProperty(REMOTE_URL,"");
		 
		String configurationResult = Configurations.getInstance(true).getConfiguration(Configurations.REMOTE_URL);
		
		Assertions.assertEquals(REMOTE_URL_EXPECTED, configurationResult);
		
	}
	
	@Test
	@ResourceLock(value = Resources.SYSTEM_PROPERTIES, mode = ResourceAccessMode.READ_WRITE)
	void testGetHeadless() {
		
		Assertions.assertEquals(Boolean.FALSE, Configurations.getInstance(true).getHeadless());
		
	}
	
	@Test
	@ResourceLock(value = Resources.SYSTEM_PROPERTIES, mode = ResourceAccessMode.READ_WRITE)
	void getUrlTest() {
		System.setProperty(REMOTE_EXECUTION, "true");
		Assertions.assertEquals(Configurations.getInstance(true).getUrl(), "http://calculator:3000");
		
	}
	
	@Test
	@ResourceLock(value = Resources.SYSTEM_PROPERTIES, mode = ResourceAccessMode.READ_WRITE)
	void getRemoteUrlTest() {
		System.setProperty(REMOTE_EXECUTION, "false");
		Assertions.assertEquals(REMOTE_URL_EXPECTED, Configurations.getInstance(true).getRemoteUrl());
		
	}
	
	@Test
	@ResourceLock(value = Resources.SYSTEM_PROPERTIES, mode = ResourceAccessMode.READ_WRITE)
	void testGetUrlLocalTest() {
		System.setProperty(REMOTE_EXECUTION, "false");
		
		Assertions.assertEquals("http://localhost:3000", Configurations.getInstance(true).getUrl());
		
	}
	
	@Test
	@Execution(ExecutionMode.SAME_THREAD)
	@ResourceLock(value = "global", mode = ResourceAccessMode.READ_WRITE)
	void testConfigurationRuntimeException() {

		assertThrows(ConfigurationRuntimeException.class, () -> {
			Configurations.getInstance("temp.properties", true);
		});

	}
	
}
