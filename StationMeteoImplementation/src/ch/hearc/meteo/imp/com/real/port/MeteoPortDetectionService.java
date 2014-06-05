package ch.hearc.meteo.imp.com.real.port;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;

import ch.hearc.meteo.imp.com.real.com.ComOption;
import ch.hearc.meteo.imp.com.real.com.trame.TrameDecoder;
import ch.hearc.meteo.imp.com.real.com.trame.TrameEncoder;
import ch.hearc.meteo.spec.com.port.MeteoPortDetectionService_I;


public class MeteoPortDetectionService implements MeteoPortDetectionService_I{


	@Override
	public List<String> findListPortSerie() {
		
		List<String> listPortSerie = new ArrayList<String>();
		
		@SuppressWarnings("unchecked")
		Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
		while(portEnum.hasMoreElements())
		{
			CommPortIdentifier portIdentifier = portEnum.nextElement();
			//switch(portIdentifier.getPortType())
			//{
			//case CommPortIdentifier.PORT_SERIAL:
				listPortSerie.add(portIdentifier.getName());
				//break;
			//}
		}
		
		return listPortSerie;
	}

	@Override
	public boolean isStationMeteoAvailable(String portName, long timeoutMS){

		SerialPort serialPort = null;
		InputStream reader = null;
		OutputStream writer = null;
		
		try{
			CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(portName);
			serialPort = (SerialPort)portId.open(portName, 10000);
			ComOption comOption = new ComOption();
			serialPort.setSerialPortParams(comOption .getSpeed(),comOption.getDataBit(),comOption.getStopBit(),comOption.getParity());
			serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
			
			//Initialise le reader et writer
			reader = serialPort.getInputStream();
			writer = serialPort.getOutputStream();
	
			serialPort.notifyOnDataAvailable(true);
			
			//Envoie et attends
			writer.write(TrameEncoder.coder("010200"));
			Thread.sleep(timeoutMS);
			
			//Essaye de lire
			@SuppressWarnings("resource")
			Scanner s = new Scanner(reader).useDelimiter("\\A");
		    String trame = s.hasNext() ? s.next() : "";
			
			float value = TrameDecoder.valeur(trame);
			if(!Float.isNaN(value))
				return true;
			else
				return false;
		}
		catch(Exception e){
			return false;	
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
			if(isStationMeteoAvailable(portName, 250))
				listStationMeteo.add(portName);
		}
		
		return listStationMeteo;
	}
	

}
