package ch.hearc.meteo.imp.afficheur.simulateur.vue;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.JPanel;

import ch.hearc.meteo.imp.afficheur.simulateur.moo.AfficheurServiceMOO;
import ch.hearc.meteo.imp.afficheur.simulateur.vue.atome.JPanelStat;

public class JPanelBarData extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JPanelBarData(AfficheurServiceMOO afficheurServiceMOO) {

		// Tools

		// afficheurServiceMOO.getStatPression()
		// afficheurServiceMOO.getListPression()

		// afficheurServiceMOO.getStatAltitude()
		// afficheurServiceMOO.getListAltitude()

		// afficheurServiceMOO.getStatTemperature()
		// afficheurServiceMOO.getListTemperature()

		panelStatPression = new JPanelStat(
				afficheurServiceMOO.getStatPression(), "Pression");

		panelStatAltitude = new JPanelStat(
		afficheurServiceMOO.getStatAltitude(), "Altitude");

		panelStatTemperature = new JPanelStat(
		afficheurServiceMOO.getStatTemperature(), "Température");

		Box boxV = Box.createVerticalBox();
		boxV.add(Box.createVerticalStrut(5));
		boxV.add(panelStatPression);
		boxV.add(Box.createVerticalStrut(5));
		boxV.add(panelStatAltitude);
		boxV.add(Box.createVerticalStrut(5));
		boxV.add(panelStatTemperature);
		boxV.add(Box.createVerticalStrut(5));

		Box boxH = Box.createHorizontalBox();
		boxH.add(Box.createHorizontalStrut(5));
		boxH.add(boxV);
		boxH.add(Box.createHorizontalStrut(5));

		setLayout(new BorderLayout());
		add(boxH, BorderLayout.CENTER);
	}

	public void update() {
		// boxSerieTemnporelle.update();
		panelStatPression.update();
		panelStatAltitude.update();
		panelStatTemperature.update();
	}

	// Input
	private JPanelStat panelStatPression;
	private JPanelStat panelStatAltitude;
	private JPanelStat panelStatTemperature;
}
