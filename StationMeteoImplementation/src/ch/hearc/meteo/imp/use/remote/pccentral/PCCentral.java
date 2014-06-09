package ch.hearc.meteo.imp.use.remote.pccentral;

import java.rmi.RemoteException;

import com.bilat.tools.reseau.rmi.RmiTools;
import com.bilat.tools.reseau.rmi.RmiURL;

import ch.hearc.meteo.imp.reseau.AfficheurManager;
import ch.hearc.meteo.imp.use.remote.PC_I;
import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.reseau.RemoteAfficheurCreator_I;

public class PCCentral implements PC_I {

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public PCCentral(AffichageOptions affichageOptions) {
		this.affichageOptions = affichageOptions;
	}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public void run() {
		try {
			server();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void server() throws RemoteException {
		this.remoteAfficheurCreator = AfficheurManager.getInstance();
	}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
	private AffichageOptions affichageOptions;
	private RmiURL rmiURLafficheurManager;
	private RemoteAfficheurCreator_I remoteAfficheurCreator;
}
