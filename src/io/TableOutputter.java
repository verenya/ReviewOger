/*******************************************************************************
 * Copyright (c) 2014 Verena Käfer.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU General Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/copyleft/gpl.html
 *
 * Contributors:
 * Verena Käfer - initial version
 *******************************************************************************/
package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;

import logic.Matcher;
import data.Review;
import data.Room;
import data.Slot;
import data.SortSlot;

/**
 * This class parses the reviews to an overview table in Latex
 */
public class TableOutputter {

	public static void createTable(String pathToFile,
			ArrayList<Review> reviews, Matcher matcher) {
		FileWriter writer;

		ArrayList<Slot> tempSlots = matcher.getSlots();
		ArrayList<SortSlot> slots = new ArrayList<SortSlot>();

		for (Slot s : tempSlots) {
			SortSlot newSlot = new SortSlot(s.getDate(), s.getBeginTime(),
					s.getEndTime());
			newSlot.setRooms(s.getRooms());
			slots.add(newSlot);
		}

		Collections.sort(slots);

		ArrayList<String> rooms = getRoomNumbers(slots);

		try {
			writer = new FileWriter(new File(pathToFile), true);

			String result = "\\documentclass[10pt,a4paper]{article}";
			writer.append(result);
			writer.append((char) Character.LINE_SEPARATOR);
			result = "\\usepackage[utf8]{inputenc}";
			writer.append(result);
			writer.append((char) Character.LINE_SEPARATOR);
			result = "\\usepackage[german]{babel}";
			writer.append(result);
			writer.append((char) Character.LINE_SEPARATOR);
			result = "\\usepackage{multirow}";
			writer.append(result);
			writer.append((char) Character.LINE_SEPARATOR);
			result = "\\usepackage[landscape]{geometry}";
			writer.append(result);
			writer.append((char) Character.LINE_SEPARATOR);
			result = "\\usepackage{a4wide}";
			writer.append(result);
			writer.append((char) Character.LINE_SEPARATOR);
			result = "\\begin{document}";
			writer.append(result);
			writer.append((char) Character.LINE_SEPARATOR);

			// table
			result = "\\begin{table}[ht]";
			writer.append(result);
			writer.append((char) Character.LINE_SEPARATOR);
			result = "\\begin{center}";
			writer.append(result);
			writer.append((char) Character.LINE_SEPARATOR);

			// definition
			result = "\\begin{tabular}{|p{1,2cm}|p{1,5cm}|p{1,3cm}|";
			for (String r : rooms) {
				result = result + "p{1cm}|";
			}
			result = result + "}";
			writer.append(result);
			writer.append((char) Character.LINE_SEPARATOR);

			result = "\\hline";
			writer.append(result);
			writer.append((char) Character.LINE_SEPARATOR);

			result = " \\multicolumn{3}{|c|}{\\multirow{2}{*}{\\textbf{Planung}}}& \\multicolumn{"
					+ Integer.toString(rooms.size())
					+ "}{|c|}{\\textbf{Raum}}\\\\";
			writer.append(result);
			writer.append((char) Character.LINE_SEPARATOR);

			result = "\\cline{4-" + Integer.toString(rooms.size() + 3) + "}";
			writer.append(result);
			writer.append((char) Character.LINE_SEPARATOR);

			result = "\\multicolumn{3}{|c|}{} ";
			for (String s : rooms) {
				result = result + "& \\textbf{" + s + "}";
			}
			result = result + "\\\\";
			writer.append(result);
			writer.append((char) Character.LINE_SEPARATOR);

			result = "\\hline";
			writer.append(result);
			writer.append((char) Character.LINE_SEPARATOR);

			// slot by slot
			DateFormat dateFormatter = new SimpleDateFormat("dd.MM.yy");
			DateFormat timeFormatter = new SimpleDateFormat("HH:mm");

			String[][] array = createArray(rooms, slots);
			int slotCounter = 0;

			for (Slot s : slots) {
				
				String dateString = dateFormatter.format(s.getDate());
				result = dateString;
				String begin = timeFormatter.format(s.getBeginTime());
				result = result + " & " + begin + " - ";
				String end = timeFormatter.format(s.getEndTime());
				result = result + end + " Uhr & Review: ";

				// review letters
				System.out.println(array[0].length);
				for (int i = 0; i < array[1].length ; i++) {
					System.out.println(slotCounter + ";" + i);
					if (array[slotCounter][i] == null) {
						result = result + "& - ";
					} else {
						result = result + " & " + array[slotCounter][i];
					}
				}

				result = result + "\\\\";

				writer.append(result);
				writer.append((char) Character.LINE_SEPARATOR);

				result = "\\hline";
				writer.append(result);
				writer.append((char) Character.LINE_SEPARATOR);

				slotCounter++;
			}

			// end
			result = "\\end{tabular}";
			writer.append(result);
			writer.append((char) Character.LINE_SEPARATOR);

			result = "\\end{center}";
			writer.append(result);
			writer.append((char) Character.LINE_SEPARATOR);

			result = "\\end{table}";
			writer.append(result);
			writer.append((char) Character.LINE_SEPARATOR);

			result = "\\end{document}";
			writer.append(result);

			writer.close();

		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null,
					"Konnte Ergebnis-Tabelle nicht schreiben", "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null,
					"Konnte Ergebnis-Tabelle nicht schreiben", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * @param slots
	 *            the sorted slot list
	 * @return a list of all room numbers
	 */
	private static ArrayList<String> getRoomNumbers(ArrayList<SortSlot> slots) {
		ArrayList<String> roomNumbers = new ArrayList<String>();

		for (Slot s : slots) {
			for (Room r : s.getRooms()) {
				String id;
				if (!r.hasBeamer()) {
					id = r.getRoomID() + "*";
				} else {
					id = r.getRoomID();
				}

				if (!roomNumbers.contains(id)) {
					roomNumbers.add(id);
				}
			}
		}

		return roomNumbers;
	}

	/**
	 * @param rooms
	 *            the list of room IDs
	 * @param slots
	 *            the sorted slot list
	 * @return an array which has the match between the slot and the rooms
	 */
	public static String[][] createArray(ArrayList<String> rooms,
			ArrayList<SortSlot> slots) {
		String[][] array = new String[slots.size()][rooms.size()];
		int slotCounter = 0;

		for (Slot slot : slots) {
			for (Room roomInSlot : slot.getRooms()) {
				String roomToSearch;

				if (roomInSlot.hasBeamer()) {
					roomToSearch = roomInSlot.getRoomID();
				} else {
					roomToSearch = roomInSlot.getRoomID() + "*";
				}
				int index = rooms.indexOf(roomToSearch);

				if (roomInSlot.getReview() == null) {
					array[slotCounter][index] = "-";
				} else {
					array[slotCounter][index] = roomInSlot.getReview()
							.getLetter();

				}
			}
			slotCounter++;
		}

		return array;
	}

}
