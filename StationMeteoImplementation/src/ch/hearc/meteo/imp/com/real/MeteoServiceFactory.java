package ch.hearc.meteo.imp.com.real;

import ch.hearc.meteo.imp.com.real.com.ComConnexion;
import ch.hearc.meteo.imp.com.real.com.ComOption;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceFactory_I;
import ch.hearc.meteo.spec.com.meteo.MeteoService_I;

public class MeteoServiceFactory implements MeteoServiceFactory_I{

	public MeteoServiceFactory()
	{
	}
	
	@Override
	public MeteoService_I create(String portName) {
		ComConnexion comConnexion=new ComConnexion(portName, new ComOption());	
		MeteoService meteoService=new MeteoService(comConnexion);
		
		comConnexion.setMeteoServiceCallback(meteoService);
		
		return meteoService;
	}

}
