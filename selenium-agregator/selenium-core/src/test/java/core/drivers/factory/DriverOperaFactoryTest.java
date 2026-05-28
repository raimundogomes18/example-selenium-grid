package core.drivers.factory;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import core.drivers.DriverOptionsException;

/**
 * @deprecated Opera não é mais suportado pelo Selenium 4.3+.
 *             Testes mantidos apenas para referência histórica.
 */
@Execution(ExecutionMode.CONCURRENT)
@TestInstance(Lifecycle.PER_METHOD)
@Disabled("Opera não é mais suportado pelo Selenium 4.3+")
class DriverOperaFactoryTest {

	private DriverOperaFactory driverFactory;

	@BeforeEach
	void setUp() {
		driverFactory = new DriverOperaFactory();
	}

	@Test
	void testCreateDriverThrowsUnsupported() {
		assertThrows(DriverOptionsException.class, () -> driverFactory.createDriver());
	}

	@Test
	void testCreateDriverWithOptionsThrowsUnsupported() {
		assertThrows(DriverOptionsException.class, () -> driverFactory.createDriver(null));
	}
}
