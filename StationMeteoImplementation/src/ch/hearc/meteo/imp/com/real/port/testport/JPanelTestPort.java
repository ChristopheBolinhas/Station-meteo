package ch.hearc.meteo.imp.com.real.port.testport;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jfree.ui.tabbedui.VerticalLayout;

import ch.hearc.meteo.imp.com.real.port.MeteoPortDetectionServiceFactory;
import ch.hearc.meteo.spec.com.port.MeteoPortDetectionService_I;

public class JPanelTestPort extends JPanel implements ListSelectionListener{

	public JPanelTestPort()
	{
		control();
		geometry();
		apparence();
		
		updateListe();
	}
	
	private void apparence() {
		
	}

	private void geometry() {
		panelButton = new JPanel();
		panelButton.setLayout(new FlowLayout());
		panelButton.add(buttonConnectPort);
		panelButton.add(buttonUpdatePort);
		
		panelIP = new JPanel();
		panelIP.setLayout(new FlowLayout());
		panelIP.add(labelIP);
		panelIP.add(textIP);
		
		panelConnectIP = new JPanel();
		panelConnectIP.setLayout(new VerticalLayout());
		panelConnectIP.add(panelIP);
		panelConnectIP.add(buttonConnectIP);
		
		setLayout(new BorderLayout());
		add(listGraphiquePort,BorderLayout.CENTER);
		add(panelButton,BorderLayout.SOUTH);
		add(panelConnectIP,BorderLayout.NORTH);
	}

	private void control() {
		listGraphiquePort = new JList<String>();
		listPortAlreadyUsed = null;
		
		buttonUpdatePort = new JButton("Refresh");
		buttonUpdatePort.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateListe();
			}
		});
		
		buttonConnectPort = new JButton("Connect");
		buttonConnectPort.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		
		buttonConnectIP = new JButton("Connect to");
		buttonConnectIP.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		textIP = new JTextField("127.0.0.1");
		textIP.setPreferredSize(new Dimension(100,20));
		labelIP = new JLabel("IP : ");
		
		portDetection = (new MeteoPortDetectionServiceFactory()).create();
	}

	public void updateListe()
	{
		listGraphiquePort.removeAll();
		List<String> tmpListPort;
		
		if(listPortAlreadyUsed != null)
			tmpListPort = portDetection.findListPortMeteo(listPortAlreadyUsed);
		else
			tmpListPort = portDetection.findListPortMeteo();
		
		String[] arrayTmpPort = new String[tmpListPort.size()];
		int x=0;
		
		for(String s :tmpListPort)
		{
			arrayTmpPort[x++] = s;
		}
		
		listGraphiquePort.setListData(arrayTmpPort);
	}
	
	public void setListPortAlreadyUsed(List<String> tmpList)
	{
		listPortAlreadyUsed = tmpList;
	}
	
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		portSelected = listGraphiquePort.getSelectedValue();
	}
	
	JList<String> listGraphiquePort;
	List<String> listPortAlreadyUsed;
	MeteoPortDetectionService_I portDetection;
	JButton buttonConnectPort, buttonUpdatePort, buttonConnectIP;
	JTextField textIP;
	String portSelected;
	JPanel panelButton, panelIP, panelConnectIP;
	JLabel labelIP;
}
