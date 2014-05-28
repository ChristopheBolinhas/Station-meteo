package ch.hearc.meteo.imp.afficheur.simulateur.vue;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import ch.hearc.meteo.imp.use.local.UseComplet;

public class MyButtonGUI extends JButton {
	public MyButtonGUI(String text) {
		super(text);

		buttonname = text;

		setOpaque(true);
		setMargin(new Insets(0, 0, 0, 0));
		// to add a different background

		// to remove the border
		setFocusable(false);
		// setBackground(Color.WHITE);

		/*
		 * setHorizontalAlignment(LEFT); setContentAreaFilled(false);
		 * setBorder(BorderFactory.createEmptyBorder());
		 * 
		 * Image img; try { img =
		 * ImageIO.read(getClass().getResource("/barre.png")); setIcon(new
		 * ImageIcon(img)); } catch (IOException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); }
		 */

		// setIcon(normal);
		// setRolloverIcon(normal);
		// setPressedIcon(normal);
		// setDisabledIcon(normal);

		// Remove default graphics from the button
		setContentAreaFilled(false);

		// Remove the focus-indicating dotted square when focused (optional)
		setFocusPainted(false);

		ov = new ObservableValue(null);
		UseComplet to = new UseComplet(ov);
		ov.addObserver(to);

		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {

				ae.getSource();// get the
								// button
								// that

				// was clicked
				System.out.println("click");

				// set its background and foreground
				//MyButtonGUI.this.setBackground(Color.green);

				ov.setValue(buttonname);

			}
		};

		addActionListener(al);

		/*
		 * this.button.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) {
		 * 
		 * 
		 * } });
		 */
	}

	private ObservableValue ov;
	private String buttonname;

}
