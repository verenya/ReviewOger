package io;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import data.Participant;
import data.ParticipantTableModel;

public class FileReader {

	private Path fileName;
	private final static Charset ENCODING = StandardCharsets.UTF_8;

	public void readParticipantList(String fileName) throws IOException {
		this.fileName = Paths.get(fileName);
		processLineByLine();
	}

	public final void processLineByLine() throws IOException {
		try (Scanner scanner = new Scanner(fileName, ENCODING.name())) {
			while (scanner.hasNextLine()) {
				processLine(scanner.nextLine());
			}
		}
	}

	protected void processLine(String line) {
		String[] splitResult = line.split(";");
		String name = splitResult[0];
		String email = splitResult[1];
		int group = Integer.parseInt(splitResult[2]);
		//TODO vor- und nachname trennen
		Participant newParticipant = new Participant(name, name, email, group);
		ParticipantTableModel.getInstance().addParticipant(newParticipant);
	}
}
