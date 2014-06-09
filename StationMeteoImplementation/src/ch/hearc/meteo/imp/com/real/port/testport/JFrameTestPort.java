package ch.hearc.meteo.imp.com.real.port.testport;

import javax.swing.JFrame;

public class JFrameTestPort extends JFrame{

	public JFrameTestPort()
	{
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(200,200);
		this.setContentPane(new JPanelTestPort());
		this.setVisible(true);
	}
	
}
