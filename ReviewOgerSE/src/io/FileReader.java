/**
 * This class holds the methods to read a list of participants 
 */
package io;

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
	 * @param filePath the path to the selected file
	 */
	public void readParticipantList(String filePath) {
		this.filePath = Paths.get(filePath);
		processLineByLine();
	}

	/**
	 * This method processes line by line of the given document and scan sit 
	 */
	public final void processLineByLine() {
		try (Scanner scanner = new Scanner(filePath, ENCODING.name())) {
			lineCount = 1;
			while (scanner.hasNextLine()) {
				processLine(scanner.nextLine());
				lineCount++;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
				    "Einlesen fehlgeschlagen. Fehler in Zeile" + Integer.toString(lineCount),
				    "Einlesen fehlgeschlagen",
				    JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * This method splits the current line by the split character and parses it into a new participant
	 * 
	 * @param line the current line
	 */
	protected void processLine(String line) {
		String[] splitResult = line.split(";");
		String firstName = splitResult[0];
		String lastName = splitResult[1];
		String email = splitResult[2];
		int group = Integer.parseInt(splitResult[3]);
		
		Participant newParticipant = new Participant(firstName, lastName, email, group);
		ParticipantTableModel.getInstance().addParticipant(newParticipant);
	}
}
