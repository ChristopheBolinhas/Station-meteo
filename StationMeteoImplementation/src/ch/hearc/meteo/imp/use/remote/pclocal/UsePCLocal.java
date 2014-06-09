
package ch.hearc.meteo.imp.use.remote.pclocal;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import ch.hearc.meteo.imp.reseau.AfficheurManager;
import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.exception.MeteoServiceException;

import com.bilat.tools.reseau.rmi.IdTools;
import com.bilat.tools.reseau.rmi.RmiTools;
import com.bilat.tools.reseau.rmi.RmiURL;

public class UsePCLocal
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public static void main(String[] args)
		{
		try {
			main();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MeteoServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}

	public static void main() throws UnknownHostException, RemoteException, MeteoServiceException, NotBoundException
		{
		InetAddress ip = InetAddress.getByName("127.0.0.1");
		String portCom = "COM1";
		AffichageOptions affichageOptions = new AffichageOptions(10, "Test");
		
		RmiURL rmiURLafficheurManager = new RmiURL(AfficheurManager.RMI_ID, ip, RmiTools.PORT_RMI_DEFAUT);

		int n = 2000;
		MeteoServiceOptions meteoServiceOptions = new MeteoServiceOptions(n, n, n);
		
		
		PCLocal pc = new PCLocal(meteoServiceOptions, portCom, affichageOptions, rmiURLafficheurManager);
		
		
		pc.run();
		
		//pc.addMeteoService("COM1");
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	}
