package ch.hearc.meteo.imp.afficheur.simulateur.vue;

import javax.swing.JFrame;

import ch.hearc.meteo.imp.afficheur.simulateur.moo.AfficheurServiceMOO;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;

public class JFrameAfficheurService extends JFrame {

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JFrameAfficheurService(AfficheurServiceMOO afficheurServiceMOO) {
		this.afficheurServiceMOO = afficheurServiceMOO;

		geometry();
		control();
		apparence();
	}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void refresh() {
		panelRoot.update();
	}

	public void updateMeteoServiceOptions(
			MeteoServiceOptions meteoServiceOptions) {
		panelRoot.updateMeteoServiceOptions(meteoServiceOptions);
	}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry() {
		panelRoot = new JPanelRoot(afficheurServiceMOO);
		add(panelRoot);
	}

	private void control() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void apparence() {
		setTitle(afficheurServiceMOO.getTitre());

		setSize(1200, 700);
		setResizable(true);
		setVisible(true);
	}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	private AfficheurServiceMOO afficheurServiceMOO;

	// Tools
	private JPanelRoot panelRoot;

}
