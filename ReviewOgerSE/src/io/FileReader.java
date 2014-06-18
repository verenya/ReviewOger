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
import java.util.Scanner;

import javax.swing.JOptionPane;

import data.Participant;
import data.ParticipantTableModel;

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
			}catch(NumberFormatException nfe){
				logText = "Gruppennummer keine Zahl";
			}catch(Exception e){
				logText = "Unbekannter Fehler";
			} finally {
				// error occured
				if (!logText.equals("")) {
					// log file if error occurs
					errorOccured = true;
					String logPath = filePath.getParent() + "/log" + timeStamp + ".txt";
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
			JOptionPane.showMessageDialog(null,
					"Es ist ein Fehler aufgetreten. Details in " + filePath.getParent().toString() + "/log" + timeStamp + ".txt",
					"Error", JOptionPane.ERROR_MESSAGE);
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
}
