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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void server() throws RemoteException {
		this.remoteAfficheurCreator = AfficheurManager.getInstance();
		//this.rmiURLafficheurManager = new RmiURL(AfficheurManager.RMI_ID,	RmiTools.PORT_RMI_DEFAUT);
		//RmiTools.shareObject(this.remoteAfficheurCreator, this.rmiURLafficheurManager);

		/*while (true) {

			System.out.println("ATTENTE");

			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // disons
		}*/

	}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
	private AffichageOptions affichageOptions;
	private RmiURL rmiURLafficheurManager;
	private RemoteAfficheurCreator_I remoteAfficheurCreator;
}
