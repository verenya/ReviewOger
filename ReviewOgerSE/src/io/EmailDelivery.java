package io;

import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import data.Review;
import data.Participant;
import data.Room;

/**
 * This class holds the functionality for the delivery of the emails to every
 * moderator
 * 
 */
public class EmailDelivery {

	private final static Charset ENCODING = StandardCharsets.UTF_8;
	static String finalEmailText = new String("<html>");

	final static JFrame optionFrame = new JFrame();

	/**
	 * Opens the e-mails in the standard email program
	 * 
	 * @param reviews
	 *            the list of reviews
	 */
	public static void deliverEmail(final ArrayList<Review> reviews) {

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
				JFileChooser fileChooser = new JFileChooser(".txt");
				fileChooser.setFileFilter(new TxtDialogFilter());

				int result = fileChooser.showOpenDialog(null);
				switch (result) {

				case JFileChooser.APPROVE_OPTION:

					optionFrame.setVisible(false);

					showTextFrame(fileChooser);

				}
			}

			/**
			 * Shows the read text
			 * 
			 * @param fileChooser
			 *            the file chooser which chose the file
			 */
			private void showTextFrame(JFileChooser fileChooser) {
				final JFrame textFrame = new JFrame();
				textFrame.setLayout(new GridLayout(5, 1));

				JLabel optionLabel = new JLabel("Folgender Text wurde gelesen:");
				textFrame.add(optionLabel);

				Scanner scanner = null;
				try {
					scanner = new Scanner(fileChooser.getSelectedFile(),
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

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void actionPerformed(ActionEvent arg0) {
						textFrame.dispose();
						optionFrame.setVisible(true);
					}
				});

				JButton nextButton = new JButton("Weiter");
				buttonPanel.add(nextButton);

				nextButton.addActionListener(new AbstractAction() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void actionPerformed(ActionEvent e) {

						Desktop desktop;
						if (Desktop.isDesktopSupported()
								&& (desktop = Desktop.getDesktop())
										.isSupported(Desktop.Action.MAIL)) {
							URI mailto;

							// attach correct participants for every review
							for (Review currentReview : reviews) {
								String separateFinalEmailText = finalEmailText
										+ "<br>Review Gruppe "
										+ currentReview.getGroupNumber()
										+ ": Raum "
										+ currentReview.getAssignedRoom()
												.getFormatedDate()
										+ "<br>"
										+ "Author: "
										+ currentReview.getAuthor()
												.getFirstName()
										+ " "
										+ currentReview.getAuthor()
												.getLastName()
										+ " "
										+ currentReview.getAuthor()
												.geteMailAdress()
										+ "<br>"
										+ "Moderator:  "
										+ currentReview.getModerator()
												.getFirstName()
										+ " "
										+ currentReview.getModerator()
												.getLastName()
										+ " "
										+ currentReview.getModerator()
												.geteMailAdress()
										+ "<br>"
										+ "Notar:  "
										+ currentReview.getScribe()
												.getFirstName()
										+ " "
										+ currentReview.getScribe()
												.getLastName()
										+ " "
										+ currentReview.getScribe()
												.geteMailAdress() + "<br>";

								for (Participant reviewer : currentReview
										.getReviewers()) {
									separateFinalEmailText = separateFinalEmailText
											+ "Gutachter:  "
											+ reviewer.getFirstName()
											+ " "
											+ reviewer.getLastName()
											+ " "
											+ reviewer.geteMailAdress()
											+ "<br>";
								}
								try {
									String zeilenumbruch = System
											.getProperty("line.separator");

									separateFinalEmailText = separateFinalEmailText
											.replace("<html>", "");
									separateFinalEmailText = separateFinalEmailText
											.replace("<br>", zeilenumbruch);

									String body = URLEncoder.encode(
											separateFinalEmailText, "utf-8")
											.replace("+", "%20");

									String email = currentReview.getModerator()
											.geteMailAdress();

									mailto = new URI("mailto:" + email
											+ "?subject=SoPra%20Reviews&body="
											+ body);
									desktop.mail(mailto);
								} catch (URISyntaxException e1) {
									JOptionPane.showMessageDialog(null,
											e1.getLocalizedMessage());
								} catch (IOException e2) {
									JOptionPane.showMessageDialog(null,
											e2.getLocalizedMessage());
								}
							}

						} else {
							JOptionPane.showMessageDialog(null,
									"Konnte kein E-Mail-Prgramm öffnen");
							textFrame.dispose();
							optionFrame.setVisible(true);
						}
					}

				});

				textFrame.setVisible(true);
				textFrame.pack();
			}

		});

	}

	/**
	 * This function loads a saved result and sends the e-mails
	 */
	public static void sendLoadedReviews() {
		JFileChooser fileChooser = new JFileChooser(".xml");
		fileChooser.setFileFilter(new OgerDialogFilter());

		int result = fileChooser.showOpenDialog(null);
		switch (result) {

		case JFileChooser.APPROVE_OPTION:

			optionFrame.setVisible(false);

			loadReview(fileChooser);

		}
	}

	private static void loadReview(JFileChooser fileChooser) {
		Scanner scanner = null;
		ArrayList<Review> reviews = new ArrayList<Review>();
		try {

			scanner = new Scanner(fileChooser.getSelectedFile(),
					ENCODING.name());
		} catch (IOException io) {
			JOptionPane.showMessageDialog(null, "Fehler beim Öffnen der Datei",
					"Öffnen fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
		} catch (IllegalArgumentException iae) {
			JOptionPane.showMessageDialog(null, "Encoding falsch",
					"Einlesen fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
		}

		Review review = null;
		Room room = null;
		int i = 0;
		
		while (scanner.hasNextLine()) {
			i++;
			System.out.println(i);
			String currentLine = scanner.nextLine();


			
			if (!currentLine.startsWith("*x")) {

				if (currentLine.startsWith("Review")) {

					String[] splitReview = currentLine.split(" ");
					SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
					// review group number room number from time clock to time
					// clock
					Date begin;
					try {
						begin = timeFormat.parse(splitReview[6]);
						Date end = timeFormat.parse(splitReview[9]);
						room = new Room(splitReview[4], false, begin, end);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					//role first last email group number
					String[] split = currentLine.split(" ");
					
					Participant current = new Participant(split[1], split[2],
							split[3], Integer.parseInt(split[5]));

					if (currentLine.startsWith("Author")) {
						review = new Review(current);
						review.setGroupNumber(current.getGroupNumber());
						review.setAssignedRoom(room);
					}

					if (currentLine.startsWith("Moderator")) {
						review.setModerator(current);
					}

					if (currentLine.startsWith("Notar")) {
						review.setScribe(current);
					}

					if (currentLine.startsWith("Gutachter")) {
						review.addReviewer(current);
					}
				}
			} else {
				// separator

				reviews.add(review);
			}

		}

		deliverEmail(reviews);

	}
}
