package ch.hearc.meteo.imp.afficheur.simulateur.vue;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ch.hearc.meteo.imp.afficheur.simulateur.moo.AfficheurServiceMOO;

public class JPanelList extends JPanel {
	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JPanelList(AfficheurServiceMOO afficheurServiceMOO) {

		this.afficheurServiceMOO = afficheurServiceMOO;

		geometry();
		control();
		apparence();
	}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void update() {
		con.update();
	}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry() {

		// Creation du layout
		GridBagLayout gridLayout = new GridBagLayout();
		contraintes = new GridBagConstraints();

		setLayout(gridLayout);

		// Instanciation de la barre latérale
		barlaterale = new JOutlookBar();

		contraintes.fill = GridBagConstraints.BOTH;
		contraintes.gridwidth = 1;
		contraintes.gridheight = 1;
		contraintes.weighty = 6.25;
		contraintes.weightx = 1; // largeur du panel
		contraintes.gridx = 0;
		contraintes.gridy = 0;

		con = new JPanelBarData(afficheurServiceMOO);

		// On ajoute les stations météo une à une
		barlaterale.addBar("Station météo 1", con);
		barlaterale.addBar("Station météo 2", new JPanelBarData(
				afficheurServiceMOO));
		barlaterale.addBar("Station météo 3", new JPanelBarData(
				afficheurServiceMOO));
		barlaterale.addBar("Station météo 4", new JPanelBarData(
				afficheurServiceMOO));
		barlaterale.addBar("Station météo 5", new JPanelBarData(
				afficheurServiceMOO));
		barlaterale.addBar("Station météo 6", new JPanelBarData(
				afficheurServiceMOO));
		barlaterale.addBar("Station météo 7", new JPanelBarData(
				afficheurServiceMOO));
		barlaterale.addBar("Station météo 8", new JPanelBarData(
				afficheurServiceMOO));
		barlaterale.addBar("Station météo 9", new JPanelBarData(
				afficheurServiceMOO));
		barlaterale.addBar("Station météo 10", new JPanelBarData(
				afficheurServiceMOO));
		barlaterale.addBar("Station météo 11", new JPanelBarData(
				afficheurServiceMOO));
		barlaterale.addBar("Station météo 12", new JPanelBarData(
				afficheurServiceMOO));
		barlaterale.addBar("Station météo 13", new JPanelBarData(
				afficheurServiceMOO));
		barlaterale.addBar("Station météo 14", new JPanelBarData(
				afficheurServiceMOO));
		barlaterale.addBar("Station météo 15", new JPanelBarData(
				afficheurServiceMOO));
		barlaterale.addBar("Station météo 16", new JPanelBarData(
				afficheurServiceMOO));
		barlaterale.addBar("Station météo 17", new JPanelBarData(
				afficheurServiceMOO));
		barlaterale.addBar("Station météo 18", new JPanelBarData(
				afficheurServiceMOO));
		barlaterale.addBar("Station météo 19", new JPanelBarData(
				afficheurServiceMOO));

		JScrollPane scrollPane = new JScrollPane(barlaterale);
		add(scrollPane, contraintes);

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
	// Inputs
	private AfficheurServiceMOO afficheurServiceMOO;

	// Tools
	private JOutlookBar barlaterale;
	private JPanelBarData con;
	private GridBagConstraints contraintes;
}
