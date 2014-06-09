package ch.hearc.meteo.imp.com.real.port;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;

import ch.hearc.meteo.imp.com.real.com.ComOption;
import ch.hearc.meteo.imp.com.real.com.trame.TrameDecoder;
import ch.hearc.meteo.imp.com.real.com.trame.TrameEncoder;
import ch.hearc.meteo.spec.com.meteo.exception.MeteoServiceException;
import ch.hearc.meteo.spec.com.port.MeteoPortDetectionService_I;


public class MeteoPortDetectionService implements MeteoPortDetectionService_I{


	@Override
	public List<String> findListPortSerie() {
		
		List<String> listPortSerie = new ArrayList<String>();
		
		@SuppressWarnings("unchecked")
		Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
		System.out.println("wut ?");
		while(portEnum.hasMoreElements())
		{
			CommPortIdentifier portIdentifier = portEnum.nextElement();
<<<<<<< HEAD
			//switch(portIdentifier.getPortType())
			//{
			//case CommPortIdentifier.PORT_SERIAL:
				listPortSerie.add(portIdentifier.getName());
			
				//break;
			//}
=======
			listPortSerie.add(portIdentifier.getName());
			
>>>>>>> 1b90c84e1053506c9035b2f163c23a5705523d25
		}
		
		return listPortSerie;
	}

	@Override
	public boolean isStationMeteoAvailable(String portName, long timeoutMS){

		SerialPort serialPort = null;
		reader = null;
		OutputStream writer = null;
		
		try{
			CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(portName);
			serialPort = (SerialPort)portId.open(portName, 10000);
			ComOption comOption = new ComOption();
			serialPort.setSerialPortParams(comOption.getSpeed(),comOption.getDataBit(),comOption.getStopBit(),comOption.getParity());
			serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
			
			//Initialise le reader et writer
			reader = serialPort.getInputStream();
			writer = serialPort.getOutputStream();
	
			serialPort.addEventListener(new SerialPortEventListener() {
				
				@Override
				public void serialEvent(SerialPortEvent e) {
					switch(e.getEventType())
					{
					case SerialPortEvent.DATA_AVAILABLE:
						checkPacketRecu();
						break;
					}
				}

			});
			
			serialPort.notifyOnDataAvailable(true);
			
			valueTest = 0;
			
			//Envoie et attends
			writer.write(TrameEncoder.coder("010200"));
			
			Thread.sleep(timeoutMS);
			
			serialPort.removeEventListener();
			reader.close();
			writer.close();
			serialPort.close();
			
			System.out.println(valueTest);
			
			if(valueTest != 0){
				return true;
			}
			else
				return false;
		}
		catch(Exception e){
			return false;	
		}
	}
	
	private void checkPacketRecu() {
		//Essaye de lire
		@SuppressWarnings("resource")
		Scanner s = new Scanner(reader).useDelimiter("\\A");
	    String trame = s.hasNext() ? s.next() : "";
		
		try {
			valueTest = TrameDecoder.valeur(trame);
		} catch (MeteoServiceException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<String> findListPortMeteo(List<String> listPortExcluded) {
		
		List<String> listStationMeteo = findListPortMeteo();
		
		for(String portExcluded:listPortExcluded)
		{
			if(listStationMeteo.contains(portExcluded))
				listStationMeteo.remove(portExcluded);
		}
		
		return listStationMeteo;
	}

	@Override
	public List<String> findListPortMeteo() {
		List<String> listPort = findListPortSerie();
		List<String> listStationMeteo = new ArrayList<String>();
		
		for(String portName:listPort)
		{
			//Si la station est disponible dans les 250ms
			if(isStationMeteoAvailable(portName, 500))
				listStationMeteo.add(portName);
		}
		
		return listStationMeteo;
	}
	
	float valueTest;
	InputStream reader;
}
