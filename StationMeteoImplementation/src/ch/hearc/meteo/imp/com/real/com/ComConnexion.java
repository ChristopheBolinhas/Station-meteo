
package ch.hearc.meteo.imp.com.real.com;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.TooManyListenersException;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import ch.hearc.meteo.imp.com.logique.MeteoServiceCallback_I;
import ch.hearc.meteo.imp.com.real.com.trame.TrameDecoder;
import ch.hearc.meteo.imp.com.real.com.trame.TrameEncoder;
import ch.hearc.meteo.spec.com.meteo.exception.MeteoServiceException;

// TODO student
//  Implémenter cette classe
//  Updater l'implémentation de MeteoServiceFactory.create()

/**
 * <pre>
 * Aucune connaissance des autres aspects du projet ici
 *
 * Ouvrer les flux vers le port com
 * ecouter les trames arrivantes (pas boucle, mais listener)
 * decoder trame
 * avertir meteoServiceCallback
 *
 *</pre>
 */
public class ComConnexion implements ComConnexions_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public ComConnexion(MeteoServiceCallback_I meteoServiceCallback, String portName, ComOption comOption)
		{
		this.comOption = comOption;
		this.portName = portName;
		this.meteoServiceCallback = meteoServiceCallback;
		}

	/**
	 * <pre>
	 * Problem :
	 * 		MeteoService est un MeteoServiceCallback_I
	 * 		ComConnexions_I utilise MeteoServiceCallback_I
	 * 		MeteoService utilise ComConnexions_I
	 *
	 * On est dans la situation
	 * 		A(B)
	 * 		B(A)
	 *
	 * Solution
	 * 		 B
	 * 		 A(B)
	 *  	 B.setA(A)
	 *
	 *  Autrement dit:
	 *
	 *		ComConnexions_I comConnexion=new ComConnexion( portName,  comOption);
	 *      MeteoService_I meteoService=new MeteoService(comConnexion);
	 *      comConnexion.setMeteoServiceCallback(meteoService);
	 *
	 *      Ce travail doit se faire dans la factory
	 *
	 *  Warning : call next
	 *  	setMeteoServiceCallback(MeteoServiceCallback_I meteoServiceCallback)
	 *
	 *  </pre>
	 */
	public ComConnexion(String portName, ComOption comOption)
		{
		this(null, portName, comOption);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void start() throws MeteoServiceException, IOException
		{
		
		try {
			serialPort.addEventListener(new SerialPortEventListener() {
				
				@Override
				public void serialEvent(SerialPortEvent e) {
					switch(e.getEventType())
					{
					case SerialPortEvent.DATA_AVAILABLE:
						try {
							traiterDonneeRecu();
						} catch (MeteoServiceException | IOException e1) {
						 new MeteoServiceException("[MeteoService] : traiterDonneeRecu failure", e1).printStackTrace();
						}
						break;
					}
				}
			});
		} catch (TooManyListenersException e) {
			throw new MeteoServiceException("[MeteoService] : addEventListener failure", e);
		}

		serialPort.notifyOnDataAvailable(true);
		}

	@Override public void stop() throws Exception
		{
		serialPort.removeEventListener();
		}

	@Override public void connect() throws Exception
		{
		CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(portName);
		serialPort = (SerialPort)portId.open(portName, 10000);
		serialPort.setSerialPortParams(comOption.getSpeed(),comOption.getDataBit(),comOption.getStopBit(),comOption.getParity());
		serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
		
		//Initialise le reader et writer
		reader = serialPort.getInputStream();
		writer = serialPort.getOutputStream();

		}

	@Override public void disconnect() throws Exception
		{
		reader.close();
		writer.close();
		serialPort.close();
		}

	@Override public void askAltitudeAsync() throws Exception
		{
		writer.write(TrameEncoder.coder("010200"));
		}

	@Override public void askPressionAsync() throws Exception
		{
		writer.write(TrameEncoder.coder("010000"));
		}

	@Override public void askTemperatureAsync() throws Exception
		{
		writer.write(TrameEncoder.coder("010100"));
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	@Override public String getNamePort()
		{
		return portName;
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	/**
	 * For post building
	 */
	public void setMeteoServiceCallback(MeteoServiceCallback_I meteoServiceCallback)
		{
		this.meteoServiceCallback = meteoServiceCallback;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/
	
	private void traiterDonneeRecu() throws MeteoServiceException, IOException
	{
		@SuppressWarnings("resource")
		Scanner s = new Scanner(reader).useDelimiter("\\A");
	    String trame = s.hasNext() ? s.next() : "";
		
		float value = TrameDecoder.valeur(trame);
		
		switch(TrameDecoder.dataType(trame))
		{
		case ALTITUDE:
			meteoServiceCallback.altitudePerformed(value);
			break;
		case PRESSION:
			meteoServiceCallback.pressionPerformed(value);
			break;
		case TEMPERATURE:
			meteoServiceCallback.temperaturePerformed(value);
			break;
		}
	}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Input
	private ComOption comOption;
	private String portName;
	private MeteoServiceCallback_I meteoServiceCallback;
	
	// Tools
	private SerialPort serialPort;
	private OutputStream writer;
	private InputStream reader;

	}
