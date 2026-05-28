package core.drivers.factory;

// NOTA: O Opera foi removido do suporte oficial do Selenium a partir da versão 4.3.
// Esta classe é mantida apenas para referência histórica e não deve ser utilizada.
// Para execução cross-browser, use Chrome, Firefox ou Edge.

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.AbstractDriverOptions;

import core.drivers.DriverOptionsException;

/**
 * @deprecated Opera não é mais suportado pelo Selenium 4.3+.
 *             Use {@link DriverChromeFactory}, {@link DriverFirefoxFactory} ou {@link DriverEdgeFactory}.
 */
@Deprecated
public class DriverOperaFactory extends DriverAbstractFactory {

	private static final String OPERA_NOT_SUPPORTED =
			"Opera não é mais suportado pelo Selenium 4.3+. Use Chrome, Firefox ou Edge.";

	@Override
	public WebDriver createDriver(AbstractDriverOptions<?> driverOptions) {
		throw new DriverOptionsException(OPERA_NOT_SUPPORTED);
	}

	@Override
	public WebDriver createDriver() {
		throw new DriverOptionsException(OPERA_NOT_SUPPORTED);
	}
}
