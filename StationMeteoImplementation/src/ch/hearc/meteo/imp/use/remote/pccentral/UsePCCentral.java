
package ch.hearc.meteo.imp.use.remote.pccentral;

import ch.hearc.meteo.spec.afficheur.AffichageOptions;




public class UsePCCentral
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public static void main(String[] args)
		{
		main();
		}

	public static void main()
		{
		pc = new PCCentral(new AffichageOptions(5, "test"));
		pc.run();
		
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/
	private static PCCentral pc;
	}

