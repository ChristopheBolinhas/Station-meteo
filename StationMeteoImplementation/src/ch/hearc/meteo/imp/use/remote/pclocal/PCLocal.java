package ch.hearc.meteo.imp.use.remote.pclocal;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

import ch.hearc.meteo.imp.afficheur.simulateur.AfficheurSimulateurFactory;
import ch.hearc.meteo.imp.com.real.MeteoServiceFactory;
import ch.hearc.meteo.imp.com.real.port.MeteoPortDetectionService;
import ch.hearc.meteo.imp.com.simulateur.MeteoServiceSimulatorFactory;
import ch.hearc.meteo.imp.use.remote.PC_I;
import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.afficheur.AfficheurFactory_I;
import ch.hearc.meteo.spec.afficheur.AfficheurService_I;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.MeteoService_I;
import ch.hearc.meteo.spec.com.meteo.exception.MeteoServiceException;
import ch.hearc.meteo.spec.com.meteo.listener.MeteoAdapter;
import ch.hearc.meteo.spec.com.meteo.listener.MeteoListener_I;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;
import ch.hearc.meteo.spec.reseau.RemoteAfficheurCreator_I;
import ch.hearc.meteo.spec.reseau.rmiwrapper.AfficheurServiceWrapper_I;
import ch.hearc.meteo.spec.reseau.rmiwrapper.MeteoServiceWrapper;

import com.bilat.tools.reseau.rmi.IdTools;
import com.bilat.tools.reseau.rmi.RmiTools;
import com.bilat.tools.reseau.rmi.RmiURL;

public class PCLocal implements PC_I {

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public PCLocal(MeteoServiceOptions meteoServiceOptions, String portCom,
			AffichageOptions affichageOptions, RmiURL rmiURLafficheurManager) {
		this.meteoServiceOptions = meteoServiceOptions;
		this.portCom = portCom;
		//this.afficheurFactory = new AfficheurService();
		this.affichageOptions = affichageOptions;
		this.rmiURLafficheurManager = rmiURLafficheurManager;
		this.meteoServices = new LinkedList<MeteoService_I>();
		this.afficheurFactory = new AfficheurSimulateurFactory();
	}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public void run() {
		try {
			server(); // avant
		} catch (Exception e) {
			System.err.println("[PCLocal :  run : server : failed");
			e.printStackTrace();
		}

		try {
			client(); // apr�s
		} catch (RemoteException e) {
			System.err.println("[PCLocal :  run : client : failed");
			e.printStackTrace();
		} catch (MeteoServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				server			*|
	\*------------------------------*/

	private void server() throws MeteoServiceException, RemoteException, NotBoundException {
		// Connexion au serveur
		afficheurManager = (RemoteAfficheurCreator_I)RmiTools.connectionRemoteObjectBloquant(rmiURLafficheurManager);
		
		
	}

	/*------------------------------*\
	|*				client			*|
	\*------------------------------*/

	private void client() throws RemoteException, MeteoServiceException, NotBoundException 
	{
		meteoServiceFactory = new MeteoServiceFactory();
		MeteoPortDetectionService service = new MeteoPortDetectionService();
		//System.out.println(service.findListPortSerie());
		 addMeteoService("COM3");
		
	}

	public void addMeteoService(String comPort) throws MeteoServiceException, RemoteException, NotBoundException {
		MeteoService_I meteoService = meteoServiceFactory.create(comPort);
		//meteoService.connect();
		String currentIp = "0.0.0.0";
		//Création du titre de la fenêtre
		try {
			currentIp = InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		AffichageOptions affichageOptions = new AffichageOptions(10, currentIp + " - " + comPort);
		
		meteoServices.add(meteoService);
		meteoService.setMeteoServiceOptions(meteoServiceOptions);
		//meteoService.start(meteoServiceOptions);
		meteoService.start(meteoServiceOptions);
		MeteoServiceWrapper meteoServiceWrapper = new MeteoServiceWrapper(meteoService);
		
		RmiURL meteoServiceRmiURL = new RmiURL(IdTools.createID(MeteoService_I.class.getName()));
		
		RmiTools.shareObject(meteoServiceWrapper, meteoServiceRmiURL);
		RmiURL remoteAfficheurServiceRmiURL  = afficheurManager.createRemoteAfficheurService(affichageOptions, meteoServiceRmiURL);
		
		final AfficheurServiceWrapper_I afficheurServiceWrapper = (AfficheurServiceWrapper_I)RmiTools.connectionRemoteObjectBloquant(remoteAfficheurServiceRmiURL);

		
		final AfficheurService_I afficheurServiceLocal = afficheurFactory.createOnLocalPC(affichageOptions, meteoServiceWrapper);
		
		
		//Maintenant qu'on a l'affichage distant, on peut connecter les evenement du meteo service a celui-ci
		meteoService.addMeteoListener(new MeteoListener_I() {
			
			@Override
			public void temperaturePerformed(MeteoEvent event) {
				try {
					afficheurServiceWrapper.printTemperature(event);
					afficheurServiceLocal.printTemperature(event);
				} catch (RemoteException e) {
					// TODO 
					System.out.println("Erreur mise a jour interface");
				}
				
			}
			
			@Override
			public void pressionPerformed(MeteoEvent event) {
				try {
					afficheurServiceWrapper.printPression(event);
					afficheurServiceLocal.printPression(event);
				} catch (RemoteException e) {
					System.out.println("Erreur mise a jour interface");
				}
				
			}
			
			@Override
			public void altitudePerformed(MeteoEvent event) {
				try {
					//TODO
					afficheurServiceWrapper.printAltitude(event);
					afficheurServiceLocal.printAltitude(event);
				} catch (RemoteException e) {
					System.out.println("Erreur mise a jour interface");
				}
				
			}
		});
		
		
	}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	private MeteoServiceOptions meteoServiceOptions;
	private String portCom;
	private AffichageOptions affichageOptions;
	private RmiURL rmiURLafficheurManager;
	

	// Tools
	private RemoteAfficheurCreator_I afficheurManager;
	private AfficheurFactory_I afficheurFactory;
	private MeteoServiceFactory meteoServiceFactory;
	private List<MeteoService_I> meteoServices;
}
