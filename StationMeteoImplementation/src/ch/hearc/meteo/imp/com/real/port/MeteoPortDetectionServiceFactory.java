package ch.hearc.meteo.imp.com.real.port;

import ch.hearc.meteo.spec.com.port.MeteoPortDetectionServiceFactory_I;
import ch.hearc.meteo.spec.com.port.MeteoPortDetectionService_I;

public class MeteoPortDetectionServiceFactory implements MeteoPortDetectionServiceFactory_I{

	@Override
	public MeteoPortDetectionService_I create() {
		return new MeteoPortDetectionService();
	}

}
