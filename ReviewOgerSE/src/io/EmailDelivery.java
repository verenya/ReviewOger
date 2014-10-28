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
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import data.Review;
import data.Participant;
import data.Room;
import data.Slot;

/**
 * This class holds the functionality for the delivery of the emails to every
 * moderator
 * 
 */
public class EmailDelivery {

	private final static Charset ENCODING = StandardCharsets.UTF_8;
	static String finalEmailText = new String("<html>");

	final static JFrame optionFrame = new JFrame();

	static ArrayList<Review> reviews;

	/**
	 * Opens the e-mails in the standard email program
	 * 
	 * @param reviews
	 *            the list of reviews
	 */
	public static void deliverEmail(final ArrayList<Review> reviewList) {
		reviews = reviewList;

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
						textFrame.dispose();
						showGreetingFrame();

					}

				});

				textFrame.setVisible(true);
				textFrame.pack();
			}

		});

	}

	/**
	 * Shows a frame to insert a greeting and a name
	 */
	private static void showGreetingFrame() {
		final JFrame greetingFrame = new JFrame();

		greetingFrame.setLayout(new GridLayout(5, 1));

		JLabel greetingLabel = new JLabel(
				"Bitte geben Sie eine Grußformel ein:");
		greetingFrame.add(greetingLabel);

		final JTextField greetingField = new JTextField();
		greetingFrame.add(greetingField);

		JLabel nameLabel = new JLabel("Bitte geben Sie Ihren Namen ein:");
		greetingFrame.add(nameLabel);

		final JTextField nameField = new JTextField();
		greetingFrame.add(nameField);

		JButton nextButton = new JButton("Weiter");
		greetingFrame.add(nextButton);

		nextButton.addActionListener(new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (nameField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Bitte geben Sie einen Namen ein");
				} else {
					greetingFrame.dispose();
					sendMails(greetingField.getText(), nameField.getText());
				}
			}

		});

		greetingFrame.pack();
		greetingFrame.setVisible(true);
	}

	/**
	 * Shows a frame on how many miliseconds delay the emails should be opened
	 * with
	 * 
	 * @param greeting
	 *            the selected greeting
	 * @param name
	 *            the selected name
	 */
	private static void sendMails(final String greeting, final String name) {

		final JFrame delayFrame = new JFrame();
		delayFrame.setLayout(new GridLayout(4, 1));

		JLabel delayLabel1 = new JLabel(
				"Bei zu schnellem Öffnen der E-Mails kann es zu Prolemen mit dem E-Mail-Programm kommen.");
		JLabel delayLabel2 = new JLabel(
				"Bitte geben Sie die Verzögerung zwischen den einzelnen E-Mails in Millisekunden ein:");
		delayFrame.add(delayLabel1);
		delayFrame.add(delayLabel2);

		final JTextField delayField = new JTextField("500");
		delayFrame.add(delayField);

		JButton button = new JButton("Weiter");
		delayFrame.add(button);

		button.addActionListener(new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// check field
				int delay = 0;
				try {
					delay = Integer.parseInt(delayField.getText());
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null,
							"Verzögerung muss eine Zahl sein");
				}
				if (delay < 0) {
					JOptionPane.showMessageDialog(null,
							"Verzögerung muss eine Zahl > 0 sein");
				} else {
					// Send mails
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
											.getRoomID()
									+ " am "
									+ currentReview.getAssignedRoom().getSlot()
											.getFormatedDate()
									+ "<br>"
									+ "Author: "
									+ currentReview.getAuthor().getFirstName()
									+ " "
									+ currentReview.getAuthor().getLastName()
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
									+ currentReview.getScribe().getFirstName()
									+ " "
									+ currentReview.getScribe().getLastName()
									+ " "
									+ currentReview.getScribe()
											.geteMailAdress() + "<br>";

							for (Participant reviewer : currentReview
									.getReviewers()) {
								separateFinalEmailText = separateFinalEmailText
										+ "Gutachter:  "
										+ reviewer.getFirstName() + " "
										+ reviewer.getLastName() + " "
										+ reviewer.geteMailAdress() + "<br>";
							}

							// greeting
							separateFinalEmailText = separateFinalEmailText
									+ "<br>";
							if (!greeting.equals("")) {
								separateFinalEmailText = separateFinalEmailText
										+ greeting + "<br>";
							}
							separateFinalEmailText = separateFinalEmailText
									+ name + "<br>";

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

								try {
									Thread.sleep(delay);
								} catch (InterruptedException ex) {
									Thread.currentThread().interrupt();
								}

							} catch (URISyntaxException e1) {
								JOptionPane.showMessageDialog(null,
										e1.getLocalizedMessage());
							} catch (IOException e2) {
								JOptionPane.showMessageDialog(null,
										e2.getLocalizedMessage());
							}

						}
						delayFrame.dispose();
						showEndFrame(delayFrame);

					} else {
						JOptionPane.showMessageDialog(null,
								"Konnte kein E-Mail-Prgramm öffnen");
						optionFrame.setVisible(true);
					}
				}
			}
		});

		delayFrame.setVisible(true);
		delayFrame.pack();

	}

	/**
	 * Shows the frame after trying to send the emails
	 * 
	 * @param delayFrame
	 *            the previous opened frame
	 */
	private static void showEndFrame(final JFrame delayFrame) {
		final JFrame endFrame = new JFrame();
		endFrame.setLayout(new GridLayout(1, 2));

		JButton endButton = new JButton("Beenden");
		endFrame.add(endButton);

		endButton.addActionListener(new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				endFrame.dispose();

			}
		});

		JButton againButton = new JButton("Neuer Versuch");
		endFrame.add(againButton);

		againButton.addActionListener(new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				endFrame.dispose();
				delayFrame.setVisible(true);

			}
		});

		endFrame.setVisible(true);
		endFrame.pack();
	}

	/**
	 * This function loads a saved result and sends the e-mails
	 * 
	 * @return true if the delivery was successful
	 */
	public static boolean sendLoadedReviews() {
		JFileChooser fileChooser = new JFileChooser(".xml");
		fileChooser.setFileFilter(new TxtDialogFilter());

		int result = fileChooser.showOpenDialog(null);
		switch (result) {

		case JFileChooser.APPROVE_OPTION:

			optionFrame.setVisible(false);
			boolean noError = loadReview(fileChooser);
			if (noError) {
				return true;
			}
			return false;

		default:
			return false;
		}
	}

	/**
	 * @param fileChooser
	 *            the filechoser for the file
	 * @return true if the loading was successful
	 */
	private static boolean loadReview(JFileChooser fileChooser) {
		Scanner scanner = null;
		ArrayList<Review> reviews = new ArrayList<Review>();
		boolean error = false;
		try {

			scanner = new Scanner(fileChooser.getSelectedFile(),
					ENCODING.name());
		} catch (IOException io) {
			JOptionPane.showMessageDialog(null, "Fehler beim Öffnen der Datei",
					"Öffnen fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
			error = true;
		} catch (IllegalArgumentException iae) {
			JOptionPane.showMessageDialog(null, "Encoding falsch",
					"Einlesen fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
			error = true;
		}

		Review review = null;
		Room room = null;

		while (scanner.hasNextLine() && !error) {

			String currentLine = scanner.nextLine();

			if (!currentLine.startsWith("*x")) {

				if (currentLine.startsWith("Review")) {

					String[] splitReview = currentLine.split(" ");
					SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"mm.dd.yy");
					// review group number room number from time clock to time
					// clock
					Date begin;
					try {
						Date date = dateFormat.parse(splitReview[4]);
						begin = timeFormat.parse(splitReview[6]);
						Date end = timeFormat.parse(splitReview[9]);
						room = new Room(splitReview[13], false, begin, end);
						room.setSlot(new Slot(date, begin, end));
					} catch (ParseException e) {
						JOptionPane.showMessageDialog(null,
								"Fehler beim Einlesen");
						error = true;
					}

				} else if (currentLine.startsWith("Beamer")) {
					// do nothing

				} else {
					// role first last email group number
					String[] split = currentLine.split(" ");

					try {

						Participant current = new Participant(split[1],
								split[2], split[3], Integer.parseInt(split[5]));

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
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null,
								"Fehler beim Einlesen");
						error = true;
					}

				}
			} else {
				// separator

				reviews.add(review);
			}

		}
		if (!error) {
			deliverEmail(reviews);
			return true;
		}
		return false;
	}
}
