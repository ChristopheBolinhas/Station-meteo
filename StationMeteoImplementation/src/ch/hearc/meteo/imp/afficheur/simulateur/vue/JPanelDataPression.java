package ch.hearc.meteo.imp.afficheur.simulateur.vue;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

import ch.hearc.meteo.imp.afficheur.simulateur.moo.AfficheurServiceMOO;

public class JPanelDataPression extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JPanelDataPression(AfficheurServiceMOO afficheurServiceMOO) {

		// Tools
		this.pannelPression = new JPanelEvent(
				afficheurServiceMOO.getStatPression(),
				afficheurServiceMOO.getListPression(), afficheurServiceMOO
						.getStatPression().getMoy(), afficheurServiceMOO
						.getStatPression().getMin(), afficheurServiceMOO
						.getStatPression().getMax(), "Pression");

		geometry();
		control();
		apparence();
	}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void update() {
		pannelPression.update();
	}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry() {
		setLayout(new BorderLayout());
		add(pannelPression);
	}

	private void apparence() {
		setBackground(Color.RED);
	}

	private void control() {
		// rien
	}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private JPanelEvent pannelPression;
}

