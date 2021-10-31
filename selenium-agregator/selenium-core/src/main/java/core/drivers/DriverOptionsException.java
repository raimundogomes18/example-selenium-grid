package core.drivers;

import java.net.MalformedURLException;

public class DriverOptionsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DriverOptionsException(String message) {
		super(message);
	}

	public DriverOptionsException(MalformedURLException e) {
		super(e);
	}

}
