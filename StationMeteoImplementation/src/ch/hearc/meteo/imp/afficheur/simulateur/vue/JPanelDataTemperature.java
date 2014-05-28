package ch.hearc.meteo.imp.afficheur.simulateur.vue;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

import ch.hearc.meteo.imp.afficheur.simulateur.moo.AfficheurServiceMOO;

public class JPanelDataTemperature extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JPanelDataTemperature(AfficheurServiceMOO afficheurServiceMOO) {

		// Tools
		this.pannelTemperature = new JPanelEvent(
				afficheurServiceMOO.getStatTemperature(),
				afficheurServiceMOO.getListTemperature(), afficheurServiceMOO
						.getStatTemperature().getMoy(), afficheurServiceMOO
						.getStatTemperature().getMin(), afficheurServiceMOO
						.getStatTemperature().getMax(), "Température");

		geometry();
		control();
		apparence();
	}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void update() {
		pannelTemperature.update();
	}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry() {
		setLayout(new BorderLayout());
		add(pannelTemperature);
	}

	private void apparence() {
		setBackground(Color.BLUE);
	}

	private void control() {
		// rien
	}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private JPanelEvent pannelTemperature;
}
