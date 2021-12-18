package core.configuration;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

public class Configurations {

	public static final String DEFAULT_FILE = "configuration.properties";

	public static final String HEADLESS = "headless";

	private static Configurations instance;

	public static final String REMOTE_EXECUTION = "remote.execution";

	public static final String REMOTE_URL = "remote.url";

	public static final String URL_APP_LOCAL = "app.url.local";

	public static final String URL_APP_REMOTE = "app.url.remote";

	public static final String DEFAULT_BROWSER = "default.browser";

	private Properties properties;
	
	final Logger logger = LoggerFactory.getLogger(Configurations.class);

	public static Configurations getInstance() {
		return getInstance(DEFAULT_FILE);
	}
	
	public static Configurations getInstance( boolean newInstance) {
		return getInstance(DEFAULT_FILE, newInstance);
	}

	public static Configurations getInstance(String file) {
		return getInstance(file, false);
	}

	public static Configurations getInstance(String file, boolean newInstance) {
		if (instance == null || newInstance) {
			instance = new Configurations();
			instance.readConfigFile(file);
		}
		return instance;
	}
	
	public String getConfiguration(String key) {

		String environment = key.replace(".", "_").toUpperCase();
		
		String value = StringUtils.isNotEmpty(System.getenv(environment)) ? System.getenv(environment) : System.getProperty(environment) ;
		
		if(StringUtils.isNotEmpty(value)) {
			return value;
		}
		
		return properties.getProperty(key);
		

	}

	public String getRemoteUrl() {

		return getConfiguration(REMOTE_URL);

	}
	
	public String getUrl() {
		if(isLocalExecution()) {
			return getConfiguration(URL_APP_LOCAL);
		}
		
		return getConfiguration(URL_APP_REMOTE);

	}


	public boolean isRemoteExecution() {
		return "true".equals(getConfiguration(REMOTE_EXECUTION));
	}

	public boolean isLocalExecution() {
		return !isRemoteExecution();
	}

	protected void readConfigFile(String file) {
		properties = new Properties();

		try {
			InputStream is = getClass().getResourceAsStream("/" + file);
			properties.load(is);
		} catch (Exception e) {
			throw new ConfigurationRuntimeException(e);
		}
	}

	public Boolean getHeadless() {
		return Boolean.parseBoolean(properties.getProperty(HEADLESS));
	}

	public String getBrowserDefault() {
		return getConfiguration(DEFAULT_BROWSER);
		
	}

}
