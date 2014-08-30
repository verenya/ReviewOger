package io;

import gui.Gui;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import main.Main;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import data.Participant;
import data.ParticipantTableModel;
import data.Room;
import data.RoomNode;
import data.RoomTreeModel;
import data.Slot;
import data.SlotNode;

public class LoadSave {
	/**
	 * Saves the current status to a oger file
	 */
	public static void save() {
		// get the selected file
		File file = IODialog.showSaveDialog(".oger");
		// not canceled
		if (file != null) {
			DefaultTreeModel treeModel = RoomTreeModel.getInstance();
			ParticipantTableModel participantModel = ParticipantTableModel
					.getInstance();

			// Create the XML root Element
			Element root = new Element("root");

			// Create a JDOM Document based on the root Element
			Document document = new Document(root);

			List<Participant> participants = participantModel.getParticipants();

			// Participants
			Element allParticipantsElement = new Element("allParticipants");

			for (Participant p : participants) {
				Element participantElement = new Element("participant");

				Attribute firstNameAttribute = new Attribute("firstName",
						p.getFirstName());
				participantElement.setAttribute(firstNameAttribute);

				Attribute lastNameAttribute = new Attribute("lastName",
						p.getLastName());
				participantElement.setAttribute(lastNameAttribute);

				Attribute mailAttribute = new Attribute("mail",
						p.geteMailAdress());
				participantElement.setAttribute(mailAttribute);

				Attribute groupAttribute = new Attribute("group",
						Integer.toString(p.getGroupNumber()));
				participantElement.setAttribute(groupAttribute);

				allParticipantsElement.addContent(participantElement);
			}
			root.addContent(allParticipantsElement);

			// Slots with reviews
			Element allSlotsElement = new Element("Slots");

			// all slots
			SlotNode currentSlotNode;
			RoomNode currentRoomNode;
			DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) treeModel
					.getRoot();

			DateFormat dateFormatter = new SimpleDateFormat("dd.MM.yy");
			DateFormat beginFormatter = new SimpleDateFormat("HH:mm");
			DateFormat endFormatter = new SimpleDateFormat("HH:mm");
			;

			for (Enumeration<SlotNode> enumSlots = rootNode.children(); enumSlots
					.hasMoreElements();) {
				currentSlotNode = (SlotNode) enumSlots.nextElement();
				Slot currentSlot = (Slot) currentSlotNode.getUserObject();

				Element slotElement = new Element("Slot");

				String date = dateFormatter.format(currentSlot.getDate()
						.getTime());
				Attribute dateAttribute = new Attribute("Datum", date);
				slotElement.setAttribute(dateAttribute);

				String begin = beginFormatter.format(currentSlot.getBeginTime()
						.getTime());
				Attribute beginAttribute = new Attribute("Beginn", begin);
				slotElement.setAttribute(beginAttribute);

				String end = endFormatter.format(currentSlot.getEndTime()
						.getTime());
				Attribute endAttribute = new Attribute("Ende", end);
				slotElement.setAttribute(endAttribute);

				// all rooms
				Element allRoomsElement = new Element("AlleRäume");

				for (Enumeration<RoomNode> enumRooms = currentSlotNode
						.children(); enumRooms.hasMoreElements();) {
					currentRoomNode = (RoomNode) enumRooms.nextElement();
					Room currentRoom = (Room) currentRoomNode.getUserObject();

					Element roomElement = new Element("Raum");

					Attribute beamerAttribute;
					if (currentRoom.isHasBeamer()) {
						beamerAttribute = new Attribute("Beamer", "true");
					} else {
						beamerAttribute = new Attribute("Beamer", "false");
					}
					roomElement.setAttribute(beamerAttribute);

					Attribute idAttribute = new Attribute("ID",
							currentRoom.getRoomID());
					roomElement.setAttribute(idAttribute);

					String beginRoom = beginFormatter.format(currentRoom
							.getBeginTime().getTime());
					Attribute beginRoomAttribute = new Attribute("Beginn",
							beginRoom);
					roomElement.setAttribute(beginRoomAttribute);

					String endRoom = endFormatter.format(currentRoom
							.getEndTime().getTime());
					Attribute endRoomAttribute = new Attribute("Ende", endRoom);
					roomElement.setAttribute(endRoomAttribute);

					allRoomsElement.addContent(roomElement);

				}
				slotElement.addContent(allRoomsElement);

				allSlotsElement.addContent(slotElement);

			}

			root.addContent(allSlotsElement);

			try {
				// output
				XMLOutputter xmlOutputter = new XMLOutputter(
						Format.getPrettyFormat());
				xmlOutputter.output(document, new FileOutputStream(file));
				Main.setSaved(true);
			} catch (java.io.IOException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
			}
		}
	}

	public static boolean load() {

		ParticipantTableModel tableModel = ParticipantTableModel.getInstance();
		RoomTreeModel slotModel = RoomTreeModel.getInstance();

		if (!Main.isSaved()) {

			// current game must be saved
			int saveResult = JOptionPane.showOptionDialog(null,
					"Möchten Sie vorher speichern?", "Speichern?",
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
					null, null, null);
			if (saveResult == JOptionPane.YES_OPTION) {
				save();
			}
		}

		Document document = new Document();
		Element root = new Element("root");

		SAXBuilder saxBuilder = new SAXBuilder();

		// Dialog to choose the oger file to parse
		JFileChooser fileChoser = new JFileChooser(".xml");
		fileChoser.setFileFilter(new OgerDialogFilter());

		int result = fileChoser.showOpenDialog(null);
		switch (result) {

		case JFileChooser.CANCEL_OPTION:
			return false;
		case JFileChooser.ERROR_OPTION:
			return false;
		case JFileChooser.APPROVE_OPTION:
			try {
				// clear models
				tableModel.clear();
				slotModel.clear();

				// Create a new JDOM document from a oger file
				File file = fileChoser.getSelectedFile();
				document = saxBuilder.build(file);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Fehler beim Parsen der Datei!");
			}

			// Initialize the root Element with the document root Element
			try {
				root = document.getRootElement();
			} catch (NullPointerException e) {
				JOptionPane.showMessageDialog(null,
						"Fehler beim Parsen der Datei!");
			}

			// participants
			Element participantsElement = root.getChild("allParticipants");

			for (Element participant : participantsElement
					.getChildren("participant")) {
				String firstName = participant.getAttributeValue("firstName");
				String lastName = participant.getAttributeValue("lastName");
				String mail = participant.getAttributeValue("mail");
				int group = Integer.parseInt(participant
						.getAttributeValue("group"));

				Participant newParticipant = new Participant(firstName,
						lastName, mail, group);
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
					date = dateFormat.parse(slotElement
							.getAttributeValue("Datum"));
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
							"Anfangszeit nicht erkannt",
							JOptionPane.ERROR_MESSAGE);
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
					((DefaultMutableTreeNode) RoomTreeModel.getInstance()
							.getRoot()).add(newSlotNode);

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
									"Beamer nicht erkannt",
									"Beamer nicht erkannt",
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
							Gui.getRoomTree().updateUI();
						}
					}

					Gui.getRoomTree().updateUI();
				}
			}

		}
		return true;

	}
}
