package junit;

import static org.junit.Assert.*;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

import logic.Matcher;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.junit.Test;

import data.Participant;
import data.ParticipantTableModel;
import data.Review;
import data.Room;
import data.RoomNode;
import data.RoomTreeModel;
import data.Slot;
import data.SlotNode;

public class MatcherTest {

	int errorCount = 0;

	@Test
	public void testCalculatedResults() {

		// prepare background
		load();

		assertEquals(ParticipantTableModel.getInstance().getParticipants()
				.size(), 108);

		// scribe != author
		// moderator != reviewed group
		Matcher matcher = new Matcher("manueller Abbruch", false, true);
		for (int i = 0; i < 50; i++) {
			ArrayList<Review> result = matcher.MatchReview();

			// we must have a result
			if (result != null) {
				for (Review review : result) {
					// scribe != author
					assertNotSame(review.getScribe(), review.getAuthor());

					// moderator != reviewer group
					for (Participant p : review.getReviewers()) {
						assertNotEquals(review.getModerator().getGroupNumber(),
								p.getGroupNumber());
					}
				}
			} else {
				errorCount++;
			}
		}

		System.out.print(errorCount);
		assertFalse(errorCount == 50);

	}

	public static boolean load() {

		ParticipantTableModel tableModel = ParticipantTableModel.getInstance();
		Document document = new Document();
		Element root = new Element("root");

		SAXBuilder saxBuilder = new SAXBuilder();

		try {
			// Create a new JDOM document from a oger file
			File file = new File("/home/verena/Oger/reviews.oger");
			document = saxBuilder.build(file);
		} catch (Exception e) {
			JOptionPane
					.showMessageDialog(null, "Fehler beim Parsen der Datei!");
		}

		// Initialize the root Element with the document root Element
		try {
			root = document.getRootElement();
		} catch (NullPointerException e) {
			JOptionPane
					.showMessageDialog(null, "Fehler beim Parsen der Datei!");
		}

		// participants
		Element participantsElement = root.getChild("allParticipants");

		for (Element participant : participantsElement
				.getChildren("participant")) {
			String firstName = participant.getAttributeValue("firstName");
			String lastName = participant.getAttributeValue("lastName");
			String mail = participant.getAttributeValue("mail");
			int group = Integer
					.parseInt(participant.getAttributeValue("group"));

			Participant newParticipant = new Participant(firstName, lastName,
					mail, group);
			tableModel.addParticipant(newParticipant);
		}

		// slots
		final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
		final DateFormat timeFormat = new SimpleDateFormat("HH:mm");
		boolean parseFailed = false;

		Element slotsElement = root.getChild("Slots");
		for (Element slotElement : slotsElement.getChildren("Slot")) {
			Date date = null;
			Date beginTime = null;
			Date endTime = null;

			try {
				date = dateFormat.parse(slotElement.getAttributeValue("Datum"));
			} catch (ParseException e) {
				JOptionPane.showMessageDialog(null,
						"Konnte Slot-Datum nicht parsen",
						"Datum nicht erkannt", JOptionPane.ERROR_MESSAGE);
				parseFailed = true;
			}

			try {
				beginTime = timeFormat.parse(slotElement
						.getAttributeValue("Beginn"));
			} catch (ParseException e) {
				JOptionPane.showMessageDialog(null,
						"Konnte Slot-Angfangszeit nicht parsen",
						"Anfangszeit nicht erkannt", JOptionPane.ERROR_MESSAGE);
				parseFailed = true;
			}

			try {
				endTime = timeFormat.parse(slotElement
						.getAttributeValue("Ende"));
			} catch (ParseException e) {
				JOptionPane.showMessageDialog(null,
						"Konnte Slot-Endzeit nicht parsen",
						"Endzeit nicht erkannt", JOptionPane.ERROR_MESSAGE);
				parseFailed = true;
			}

			if (!parseFailed) {
				Slot slot = new Slot(date, beginTime, endTime);
				SlotNode newSlotNode = new SlotNode();
				newSlotNode.setUserObject(slot);
				((DefaultMutableTreeNode) RoomTreeModel.getInstance().getRoot())
						.add(newSlotNode);

				// Rooms
				Element roomsElement = slotElement.getChild("AlleRäume");
				for (Element roomElement : roomsElement.getChildren("Raum")) {
					parseFailed = false;

					String roomString = roomElement.getAttributeValue("ID");
					Date roomBeginTime = null;
					Date roomEndTime = null;

					Boolean hasBeamer = false;
					String hasBeamerString = roomElement
							.getAttributeValue("Beamer");
					if (hasBeamerString.equals("false")) {
						hasBeamer = false;
					} else if (hasBeamerString.equals("true")) {
						hasBeamer = true;
					} else {
						JOptionPane.showMessageDialog(null,
								"Beamer nicht erkannt", "Beamer nicht erkannt",
								JOptionPane.ERROR_MESSAGE);
						parseFailed = true;
					}

					try {
						roomBeginTime = timeFormat.parse(roomElement
								.getAttributeValue("Beginn"));
					} catch (ParseException e) {
						JOptionPane.showMessageDialog(null,
								"Konnte Slot-Angfangszeit nicht parsen",
								"Anfangszeit nicht erkannt",
								JOptionPane.ERROR_MESSAGE);
						parseFailed = true;
					}

					try {
						roomEndTime = timeFormat.parse(roomElement
								.getAttributeValue("Ende"));
					} catch (ParseException e) {
						JOptionPane.showMessageDialog(null,
								"Konnte Slot-Endzeit nicht parsen",
								"Endzeit nicht erkannt",
								JOptionPane.ERROR_MESSAGE);
						parseFailed = true;
					}

					if (!parseFailed) {
						Room room = new Room(roomString, hasBeamer,
								roomBeginTime, roomEndTime);

						RoomNode newRoomNode = new RoomNode();
						newRoomNode.setUserObject(room);

						newSlotNode.add(newRoomNode);

					}
				}

			}
		}

		return true;

	}

}
