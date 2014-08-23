/**
 * This class provides a gui for editing a room
 */
package gui.actions;

import gui.Gui;

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

public class EditRoomAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3746794815943075198L;

	@Override
	public void actionPerformed(ActionEvent arg0) {
		final JFrame roomFrame = new JFrame();

		final DateFormat timeFormat = new SimpleDateFormat("HH:mm");

		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) Gui
				.getRoomTree().getLastSelectedPathComponent();

		if (Room.class.isInstance(selectedNode.getUserObject())) {

			final Room selectedRoom = (Room) selectedNode.getUserObject();

			roomFrame.setLayout(new GridLayout(5, 2));
			roomFrame.setTitle("Raum bearbeiten");
			Dimension minimumSize = new Dimension(250, 200);
			roomFrame.setMinimumSize(minimumSize);

			JLabel RoomLabel = new JLabel("Raum:");
			roomFrame.add(RoomLabel);

			final JTextField roomField = new JTextField(
					selectedRoom.getRoomID());
			roomFrame.add(roomField);

			JLabel beginLabel = new JLabel("Beginn:");
			roomFrame.add(beginLabel);

			final JTextField beginField = new JTextField(
					timeFormat.format(selectedRoom.getBeginTime()));
			roomFrame.add(beginField);

			JLabel endLabel = new JLabel("Ende:");
			roomFrame.add(endLabel);

			final JTextField endField = new JTextField(
					timeFormat.format(selectedRoom.getEndTime()));
			roomFrame.add(endField);

			JLabel hasBeamerLabel = new JLabel("Beamer: ");
			roomFrame.add(hasBeamerLabel);

			final JCheckBox hasBeamerBox = new JCheckBox();
			hasBeamerBox.setSelected(selectedRoom.isHasBeamer());
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
						selectedRoom.setHasBeamer(hasBeamer);
						selectedRoom.setRoomID(roomString);
						selectedRoom.setBeginTime(beginTime);
						selectedRoom.setEndTime(endTime);

						Gui.getRoomTree().updateUI();
						Main.setSaved(false);
						roomFrame.dispose();
					}
				}

			});
			roomFrame.add(doneButton);

			roomFrame.setVisible(true);
			roomFrame.pack();

		} else {
			JOptionPane.showMessageDialog(null, "Kann nur Raum bearbeiten");
		}
	}
}