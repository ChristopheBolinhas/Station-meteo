package ch.hearc.meteo.imp.afficheur.simulateur.vue;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import ch.hearc.meteo.imp.afficheur.simulateur.moo.AfficheurServiceMOO;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;

public class JPanelRoot extends JPanel {

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JPanelRoot(AfficheurServiceMOO afficheurServiceMOO) {

		// Contient la liste des stations météo
		this.jpanellist = new JPanelList(afficheurServiceMOO);

		// Les charts
		this.jpaneldataview = new JPanelDataView(afficheurServiceMOO);

		geometry();
		control();
		apparence();
	}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void update() {
		jpaneldataview.update();
		jpanellist.update();
	}

	public void updateMeteoServiceOptions(
			MeteoServiceOptions meteoServiceOptions) {
		jpaneldataview.updateMeteoServiceOptions(meteoServiceOptions);
	}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry() {

		// Creation du layout
		GridBagLayout gridLayout = new GridBagLayout();
		contraintes = new GridBagConstraints();

		setLayout(gridLayout);

		contraintes.fill = GridBagConstraints.BOTH;
		contraintes.gridwidth = 1;
		contraintes.gridheight = 1;
		contraintes.weighty = 1;
		contraintes.weightx = 1; // largeur du panel
		contraintes.gridx = 0;
		contraintes.gridy = 0;
		add(jpanellist, contraintes); // Panel contenant la Liste gauche

		contraintes.gridwidth = 5;
		contraintes.gridheight = 1;
		contraintes.weighty = 1;
		contraintes.weightx = 3; // largeur du panel
		contraintes.gridx = 1;
		contraintes.gridy = 0;
		add(jpaneldataview, contraintes); // Panel contenant les charts
	}

	private void apparence() {
		// rien
	}

	private void control() {
		// rien
	}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private GridBagConstraints contraintes;

	// Tools
	private JPanelList jpanellist;
	private JPanelDataView jpaneldataview;
}
