package ch.hearc.meteo.imp.reseau;

import java.rmi.RemoteException;

import ch.hearc.meteo.imp.afficheur.simulateur.AfficheurSimulateurFactory;
import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.afficheur.AfficheurFactory_I;
import ch.hearc.meteo.spec.afficheur.AfficheurService_I;
import ch.hearc.meteo.spec.com.meteo.exception.MeteoServiceException;
import ch.hearc.meteo.spec.reseau.rmiwrapper.MeteoServiceWrapper_I;

public class ServicesCreator {

	public ServicesCreator() {
		afficheurFactory = new AfficheurSimulateurFactory();
	}

	private AfficheurService_I createAfficheurService(
			final AffichageOptions affichageOptions,
			final MeteoServiceWrapper_I meteoServiceRemote)
			throws RemoteException {
		// AfficheurService_I afficheurService = new AfficheurServiceWrapper()
		try {
			meteoServiceRemote.connect();
		} catch (MeteoServiceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return afficheurFactory.createOnCentralPC(affichageOptions,
				meteoServiceRemote);
	}

	AfficheurFactory_I afficheurFactory;

}
