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
package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import main.Main;
import data.Room;
import data.RoomNode;
import data.Slot;

/**
 * This class shows a dialog in which the user can enter the room number and the
 * exact start and end time for a new room
 */
public class NewRoomGui {

	/**
	 * This method shows a dialog for the slot data and generates a new slot.
	 */
	public static void addRoom(JFrame parent) {

		final DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) Gui
				.getRoomTree().getLastSelectedPathComponent();

		if (selectedNode == null) {
			JOptionPane.showMessageDialog(null, "Bitte Slot markieren",
					"Error", JOptionPane.ERROR_MESSAGE);
		}

		else if (selectedNode.isRoot()) {
			JOptionPane.showMessageDialog(null,
					"Kann keine Räume zur Wurzel hinzufügen", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

		// not possible to add something to a leaf
		else if (!selectedNode.isLeaf()) {

			final JFrame roomFrame = new JFrame();

			final DateFormat timeFormat = new SimpleDateFormat("HH:mm");

			roomFrame.setLayout(new GridLayout(5, 2));
			roomFrame.setTitle("Neuer Raum");
			Dimension minimumSize = new Dimension(250, 200);
			roomFrame.setMinimumSize(minimumSize);

			JLabel RoomLabel = new JLabel("Raum:");
			roomFrame.add(RoomLabel);

			final JTextField roomField = new JTextField();
			roomFrame.add(roomField);

			JLabel beginLabel = new JLabel("Beginn:");
			roomFrame.add(beginLabel);

			final JTextField beginField = new JTextField();
			beginField.setText(timeFormat.format(((Slot) selectedNode
					.getUserObject()).getBeginTime()));
			roomFrame.add(beginField);

			JLabel endLabel = new JLabel("Ende:");
			roomFrame.add(endLabel);

			final JTextField endField = new JTextField();
			endField.setText(timeFormat.format(((Slot) selectedNode
					.getUserObject()).getEndTime()));
			roomFrame.add(endField);

			JLabel hasBeamerLabel = new JLabel("Beamer: ");
			roomFrame.add(hasBeamerLabel);

			final JCheckBox hasBeamerBox = new JCheckBox();
			hasBeamerBox.setSelected(true);
			roomFrame.add(hasBeamerBox);

			JButton doneButton = new JButton("OK");
			doneButton.addActionListener(new AbstractAction() {

				private static final long serialVersionUID = 2483720398415164553L;

				@Override
				public void actionPerformed(ActionEvent arg0) {

					boolean parseFailed = false;

					String roomString = roomField.getText();
					Date beginTime = null;
					Date endTime = null;

					Boolean hasBeamer = hasBeamerBox.isSelected();

					try {
						beginTime = timeFormat.parse(beginField.getText());
					} catch (ParseException e) {
						JOptionPane.showMessageDialog(null,
								"Angfangszeit muss die Form HH:mm haben",
								"Anfangszeit nicht erkannt",
								JOptionPane.ERROR_MESSAGE);
						parseFailed = true;
					}

					try {
						endTime = timeFormat.parse(endField.getText());
					} catch (ParseException e) {
						JOptionPane.showMessageDialog(null,
								"Endzeit muss die Form HH:mm haben",
								"Endzeit nicht erkannt",
								JOptionPane.ERROR_MESSAGE);
						parseFailed = true;
					}

					if (!parseFailed) {
						Room room = new Room(roomString, hasBeamer, beginTime,
								endTime);

						RoomNode newRoomNode = new RoomNode();
						newRoomNode.setUserObject(room);

						selectedNode.add(newRoomNode);
						Gui.getRoomTree().updateUI();
						Main.setSaved(false);
						roomFrame.dispose();
					}
				}

			});
			roomFrame.add(doneButton);

			roomFrame.setLocationRelativeTo(parent);
			roomFrame.setVisible(true);
			roomFrame.pack();
		} else {
			JOptionPane.showMessageDialog(null,
					"Kann keine Räume zu Räumen hinzufügen", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}

}
