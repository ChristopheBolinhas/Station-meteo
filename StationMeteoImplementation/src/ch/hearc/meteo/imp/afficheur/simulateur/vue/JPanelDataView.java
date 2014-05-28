package ch.hearc.meteo.imp.afficheur.simulateur.vue;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import ch.hearc.meteo.imp.afficheur.simulateur.moo.AfficheurServiceMOO;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;

public class JPanelDataView extends JPanel {
	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JPanelDataView(AfficheurServiceMOO afficheurServiceMOO) {

		this.panelControl = new JPanelControl(afficheurServiceMOO);
		this.panelAltitude = new JPanelDataAltitude(afficheurServiceMOO);
		this.panelPression = new JPanelDataPression(afficheurServiceMOO);
		this.panelTemperature = new JPanelDataTemperature(afficheurServiceMOO);

		this.panelSlider = new JPanelSlider(afficheurServiceMOO);

		geometry();
		control();
		apparence();
	}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void update() {
		panelPression.update();
		panelAltitude.update();
		panelTemperature.update();
	}

	public void updateMeteoServiceOptions(
			MeteoServiceOptions meteoServiceOptions) {
		panelSlider.updateMeteoServiceOptions(meteoServiceOptions);
	}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry() {

		// Creation du layout
		GridBagLayout gridLayout = new GridBagLayout();
		contraintes = new GridBagConstraints();

		setLayout(gridLayout);

		// LES PANELS CONTENANT LES GRAPHES
		contraintes.gridx = 1;
		contraintes.gridy = 0;
		contraintes.weightx = 1;
		contraintes.weighty = 2;
		contraintes.gridheight = 1;
		contraintes.gridwidth = 2;
		contraintes.fill = GridBagConstraints.BOTH;
		add(panelAltitude, contraintes);

		contraintes.gridx = 1;
		contraintes.gridy = 1;
		contraintes.weightx = 1;
		contraintes.weighty = 2;
		contraintes.gridheight = 1;
		contraintes.gridwidth = 2;
		add(panelTemperature, contraintes);

		contraintes.gridx = 1;
		contraintes.gridy = 2;
		contraintes.weightx = 1;
		contraintes.weighty = 2;
		contraintes.gridheight = 1;
		contraintes.gridwidth = 2;
		add(panelPression, contraintes);

		// LA BARRE DT
		contraintes.gridx = 1;
		contraintes.gridy = 3;
		contraintes.weightx = 1;
		contraintes.weighty = 0.25;
		contraintes.gridwidth = 1;
		contraintes.gridheight = 1;
		add(panelSlider, contraintes);

		// STOP START
		contraintes.gridx = 2;
		contraintes.gridy = 3;
		contraintes.weightx = 1;
		contraintes.weighty = 0.25;
		contraintes.gridwidth = 1;
		contraintes.gridheight = 1;
		add(panelControl, contraintes);
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
	private JPanelControl panelControl;
	private JPanelSlider panelSlider;
	private GridBagConstraints contraintes;

	// Tools
	private JPanelDataPression panelPression;
	private JPanelDataAltitude panelAltitude;
	private JPanelDataTemperature panelTemperature;
}
