
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
		
			MeteoServiceWrapper_I meteoServiceRemote = null;
			
			// client
			{
				meteoServiceRemote = (MeteoServiceWrapper_I)RmiTools.connectionRemoteObjectBloquant(meteoServiceRmiURL);
			}

			// server
			{
			AfficheurService_I afficheurService = createAfficheurService(affichageOptions, meteoServiceRemote);
			
			AfficheurServiceWrapper afficheurServiceWrapper = new AfficheurServiceWrapper(afficheurService);
			RmiURL afficheurServicermiURL = rmiUrl();
			RmiTools.shareObject(afficheurServiceWrapper, afficheurServicermiURL);
			
			
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
