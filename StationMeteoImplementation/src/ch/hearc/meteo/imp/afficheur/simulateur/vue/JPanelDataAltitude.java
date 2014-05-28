package ch.hearc.meteo.imp.afficheur.simulateur.vue;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

import ch.hearc.meteo.imp.afficheur.simulateur.moo.AfficheurServiceMOO;

public class JPanelDataAltitude extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JPanelDataAltitude(AfficheurServiceMOO afficheurServiceMOO) {

		// Tools
		this.pannelAltitude = new JPanelEvent(
				afficheurServiceMOO.getStatAltitude(),
				afficheurServiceMOO.getListAltitude(), afficheurServiceMOO
						.getStatAltitude().getMoy(), afficheurServiceMOO
						.getStatAltitude().getMin(), afficheurServiceMOO
						.getStatAltitude().getMax(), "Altitude");

		geometry();
		control();
		apparence();
	}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void update() {
		pannelAltitude.update();
	}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry() {
		setLayout(new BorderLayout());
		add(pannelAltitude);
	}

	private void apparence() {
		setBackground(Color.GREEN);
	}

	private void control() {
		// rien
	}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
	// Tools
	private JPanelEvent pannelAltitude;
}
