/**
 * This class holds the methods to read a list of participants 
 */
package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import data.Participant;
import data.ParticipantTableModel;
import data.Review;

public class FileReader {

	private Path filePath;
	private final static Charset ENCODING = StandardCharsets.UTF_8;

	/**
	 * counts the read lines if an error occurs
	 */
	int lineCount = 1;

	/**
	 * @param filePath
	 *            the path to the selected file
	 */
	public void readParticipantList(String filePath) {
		this.filePath = Paths.get(filePath);
		processLineByLine();
	}

	/**
	 * This method processes line by line of the given document and scan sit
	 */
	public final void processLineByLine() {
		Scanner scanner = null;
		try {
			scanner = new Scanner(filePath, ENCODING.name());
		} catch (IOException io) {
			JOptionPane.showMessageDialog(null, "Fehler beim Öffnen der Datei",
					"Öffnen fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
		} catch (IllegalArgumentException iae) {
			JOptionPane.showMessageDialog(null, "Encoding falsch",
					"Einlesen fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
		}

		lineCount = 1;
		Boolean errorOccured = false;
		long timeStamp = System.currentTimeMillis();
		while (scanner.hasNextLine()) {
			String logText = "";
			try {
				// line by line
				processLine(scanner.nextLine());
			} catch (ArrayIndexOutOfBoundsException e) {
				logText = "Zu wenig Argumente";
			} catch (NumberFormatException nfe) {
				logText = "Gruppennummer keine Zahl";
			} catch (Exception e) {
				logText = "Unbekannter Fehler";
			} finally {
				// error occured
				if (!logText.equals("")) {
					// log file if error occurs
					errorOccured = true;
					String logPath = filePath.getParent() + "/log" + timeStamp
							+ ".txt";
					FileWriter writer;
					try {
						writer = new FileWriter(new File(logPath), true);
						writer.append("\nFehler in Zeile "
								+ Integer.toString(lineCount) + " " + logText);

						writer.close();
					} catch (FileNotFoundException e1) {
						JOptionPane.showMessageDialog(null,
								"Konnte log-Datei nicht schreiben", "Error",
								JOptionPane.ERROR_MESSAGE);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null,
								"Konnte log-Datei nicht schreiben", "Error",
								JOptionPane.ERROR_MESSAGE);
					}

				}

			}
			lineCount++;
		}

		if (errorOccured) {
			JOptionPane
					.showMessageDialog(
							null,
							"Es ist ein Fehler aufgetreten. Bitte verwenden Sie ein \";\" als Separator. Details in "
									+ filePath.getParent().toString()
									+ "/log"
									+ timeStamp + ".txt", "Error",
							JOptionPane.ERROR_MESSAGE);
		}

	}

	/**
	 * This method splits the current line by the split character and parses it
	 * into a new participant
	 * 
	 * @param line
	 *            the current line
	 */
	protected void processLine(String line) {
		String[] splitResult = line.split(";");
		String firstName = splitResult[0];
		String lastName = splitResult[1];
		String email = splitResult[2];
		int group = Integer.parseInt(splitResult[3]);

		Participant newParticipant = new Participant(firstName, lastName,
				email, group);
		ParticipantTableModel.getInstance().addParticipant(newParticipant);
	}

	public void printResult(ArrayList<Review> reviews, String path) {

		FileWriter writer;

		try {
			writer = new FileWriter(new File(path), true);

			for (Review r : reviews) {
				String result = "Review Gruppe " + r.getGroupNumber() + " am "
						+ r.getAssignedRoom().getSlot().getFormatedDate().trim() + " in Raum " + r.getAssignedRoom().getRoomID();
				writer.append(result);
				writer.append((char) Character.LINE_SEPARATOR);

				if (r.getAuthor() != null) {
					Participant author = r.getAuthor();
					result = "Author: " + author.getFirstName().replace(" ", "").trim() + " "
							+ author.getLastName().replace(" ", "").trim() + " "
							+ author.geteMailAdress().replace(" ", "").trim() + " Gruppe "
							+ author.getGroupNumber();
					writer.append(result);
					writer.append((char) Character.LINE_SEPARATOR);
				}

				if (r.getModerator() != null) {
					Participant moderator = r.getModerator();
					result = "Moderator: " + moderator.getFirstName().replace(" ", "").trim() + " "
							+ moderator.getLastName().replace(" ", "").trim() + " "
							+ moderator.geteMailAdress().replace(" ", "").trim() + " Gruppe "
							+ moderator.getGroupNumber();
					writer.append(result);
					writer.append((char) Character.LINE_SEPARATOR);
				}

				if (r.getScribe() != null) {
					Participant scribe = r.getScribe();
					result = "Notar: " + scribe.getFirstName().replace(" ", "").trim() + " "
							+ scribe.getLastName().replace(" ", "").trim() + " "
							+ scribe.geteMailAdress().replace(" ", "").trim() + " Gruppe "
							+ scribe.getGroupNumber();
					writer.append(result);
					writer.append((char) Character.LINE_SEPARATOR);
				}

				for (Participant reviewer : r.getReviewers()) {
					result = "Gutachter: " + reviewer.getFirstName().replace(" ", "").trim() + " "
							+ reviewer.getLastName().replace(" ", "").trim() + " "
							+ reviewer.geteMailAdress().replace(" ", "").trim() + " Gruppe "
							+ reviewer.getGroupNumber();
					writer.append(result);
					writer.append((char) Character.LINE_SEPARATOR);
				}

				result = "*x*x*x*x*x*x*x*x*x*x*x*x*x*x*x*x*x*x*x*x*x*x*x*x*x*x*x*x*x*x*x*x*x*x*x*x*x*x*x*x";
				writer.append(result);
				writer.append((char) Character.LINE_SEPARATOR);
			}

			writer.close();
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null,
					"Konnte Ergebnis-Datei nicht schreiben", "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null,
					"Konnte Ergebnis-Datei nicht schreiben", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
