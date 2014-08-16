package io;

import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import data.Participant;
import data.Review;

public class EmailDelivery {

	private final static Charset ENCODING = StandardCharsets.UTF_8;
	static String finalEmailText = new String("<html>");

	final static JFrame optionFrame = new JFrame();

	public static void deliverEmail(ArrayList<Review> reviews) {

		optionFrame.setLayout(new GridLayout(2, 1));

		JLabel optionLabel = new JLabel(
				"Bitte wählen Sie ein Textdokument, das den Inhalt der e-Mail enthält");
		optionFrame.add(optionLabel);

		JButton browseButton = new JButton("Browse");
		optionFrame.add(browseButton);

		optionFrame.setVisible(true);
		optionFrame.pack();

		browseButton.addActionListener(new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChoser = new JFileChooser(".txt");
				fileChoser.setFileFilter(new TxtDialogFilter());

				int result = fileChoser.showOpenDialog(null);
				switch (result) {

				case JFileChooser.APPROVE_OPTION:

					optionFrame.setVisible(false);

					showTextFrame(fileChoser);

				}
			}

			private void showTextFrame(JFileChooser fileChoser) {
				final JFrame textFrame = new JFrame();
				textFrame.setLayout(new GridLayout(5, 1));

				JLabel optionLabel = new JLabel("Folgender Text wurde gelesen:");
				textFrame.add(optionLabel);

				Scanner scanner = null;
				try {
					scanner = new Scanner(fileChoser.getSelectedFile(),
							ENCODING.name());
				} catch (IOException io) {
					JOptionPane.showMessageDialog(null,
							"Fehler beim Öffnen der Datei",
							"Öffnen fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(null, "Encoding falsch",
							"Einlesen fehlgeschlagen",
							JOptionPane.ERROR_MESSAGE);
				}

				finalEmailText = "<html>";

				while (scanner.hasNextLine()) {
					String currentLine = scanner.nextLine();
					finalEmailText = finalEmailText + currentLine + "<br>";
				}

				String emailText = finalEmailText
						+ "<br>Review Gruppe i: Raum x von y bis z<br>"
						+ "Author: <br>" + "Moderator: <br>" + "Notar: <br>"
						+ "Gutachter: <br>";

				JLabel textLabel = new JLabel(emailText);
				textFrame.add(textLabel);

				JPanel buttonPanel = new JPanel();
				textFrame.add(buttonPanel);
				buttonPanel.setLayout(new GridLayout(1, 2));

				JButton backButton = new JButton("Zurück");
				buttonPanel.add(backButton);

				backButton.addActionListener(new AbstractAction() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						textFrame.dispose();
						optionFrame.setVisible(true);
					}
				});

				JButton nextButton = new JButton("Weiter");
				buttonPanel.add(nextButton);

				nextButton.addActionListener(new AbstractAction() {

					@Override
					public void actionPerformed(ActionEvent e) {
						Desktop desktop;
						if (Desktop.isDesktopSupported()
								&& (desktop = Desktop.getDesktop())
										.isSupported(Desktop.Action.MAIL)) {
							URI mailto;
							try {
								
								finalEmailText = finalEmailText.replace("<html>", "");
								finalEmailText = finalEmailText.replace("<br>", "%0d%0a");
								finalEmailText = finalEmailText.trim();
								finalEmailText = finalEmailText.replace(" ", "%20");
								mailto = new URI(
										"mailto:verena.kaefer@gmail.com?subject=SoPra%20Reviews&body="+finalEmailText);
								desktop.mail(mailto);
							} catch (URISyntaxException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}

						} else {
							// TODO fallback to some Runtime.exec(..) voodoo?
							throw new RuntimeException(
									"desktop doesn't support mailto; mail is dead anyway ;)");
						}
					}

				});

				textFrame.setVisible(true);
				textFrame.pack();
			}

		});

	}

}
