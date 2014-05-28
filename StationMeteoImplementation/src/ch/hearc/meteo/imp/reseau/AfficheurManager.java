
package ch.hearc.meteo.imp.reseau;

import java.rmi.RemoteException;

import ch.hearc.meteo.imp.afficheur.simulateur.AfficheurSimulateurFactory;
import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.afficheur.AfficheurFactory_I;
import ch.hearc.meteo.spec.afficheur.AfficheurService_I;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.exception.MeteoServiceException;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;
import ch.hearc.meteo.spec.reseau.RemoteAfficheurCreator_I;
import ch.hearc.meteo.spec.reseau.rmiwrapper.AfficheurServiceWrapper;
import ch.hearc.meteo.spec.reseau.rmiwrapper.MeteoServiceWrapper_I;

import com.bilat.tools.reseau.rmi.IdTools;
import com.bilat.tools.reseau.rmi.RmiTools;
import com.bilat.tools.reseau.rmi.RmiURL;

/**
 * <pre>
 * One instance only (Singleton) on PC-Central
 * RMI-Shared
 * </pre>
 */
public class AfficheurManager implements RemoteAfficheurCreator_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	private AfficheurManager() throws RemoteException
		{
		afficheurFactory = new AfficheurSimulateurFactory();
		server();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/


	/**
	 * Remote use
	 */
	@Override public RmiURL createRemoteAfficheurService(AffichageOptions affichageOptions, RmiURL meteoServiceRmiURL) throws RemoteException
		{
		
			System.out.println("REMOTE AFFICHAGE CREE");
			MeteoServiceWrapper_I meteoServiceRemote = null;
			
			// client
			{
			// TODO connecion to meteoService on PC-Local with meteoServiceRmiURL
				//On connecte le wrapper au service meteo
				System.out.println("Connexion au service meteo effectue");
				meteoServiceRemote = (MeteoServiceWrapper_I)RmiTools.connectionRemoteObjectBloquant(meteoServiceRmiURL);
				/*try {
					meteoServiceRemote.connect();
				} catch (MeteoServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			}

			// server
			{
			AfficheurService_I afficheurService = createAfficheurService(affichageOptions, meteoServiceRemote);
			
			AfficheurServiceWrapper afficheurServiceWrapper = new AfficheurServiceWrapper(afficheurService);
			RmiURL afficheurServicermiURL = rmiUrl();
			System.out.println("Partage de l'affichage");
			RmiTools.shareObject(afficheurServiceWrapper, afficheurServicermiURL);
			
			
			//afficheurFactory.createOnLocalPC(affichageOptions, meteoServiceRemote);
			
			return afficheurServicermiURL; // Retourner le RMI-ID pour une connection distante sur le serveur d'affichage
			}
		}

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	public static synchronized RemoteAfficheurCreator_I getInstance() throws RemoteException
		{
		if (INSTANCE == null)
			{
			INSTANCE = new AfficheurManager();
			}

		return INSTANCE;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private AfficheurService_I createAfficheurService(AffichageOptions affichageOptions, final MeteoServiceWrapper_I meteoServiceRemote) throws RemoteException
		{
		//AfficheurService_I afficheurService = new AfficheurServiceWrapper()
		try {
			meteoServiceRemote.connect();
		} catch (MeteoServiceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		return afficheurFactory.createOnCentralPC(affichageOptions, meteoServiceRemote);
		}

	private void server() throws RemoteException
		{
			//Partage de l'afficheurManager
			RmiTools.shareObject(this, new RmiURL(RMI_ID));
		}

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	/**
	 * Thread Safe
	 */
	private static RmiURL rmiUrl()
		{
		String id = IdTools.createID(PREFIXE);
		return new RmiURL(id);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	// Tools

	private static RemoteAfficheurCreator_I INSTANCE = null;
	private static AfficheurSimulateurFactory afficheurFactory;
	
	// Tools final
	public static final String PREFIXE = "AFFICHEUR_SERVICE";

	public static final String RMI_ID = AfficheurManager.class.getName();

	}
