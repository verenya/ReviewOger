/**
 * This class provides a GUI for editing a slot
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import main.Main;
import data.Slot;

public class EditSlotAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3746794815943075198L;

	@Override
	public void actionPerformed(ActionEvent arg0) {
		final JFrame roomFrame = new JFrame();

		final DateFormat timeFormat = new SimpleDateFormat("HH:mm");
		final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");

		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) Gui
				.getRoomTree().getLastSelectedPathComponent();

		if (Slot.class.isInstance(selectedNode.getUserObject())) {

			final Slot selectedSlot = (Slot) selectedNode.getUserObject();

			roomFrame.setLayout(new GridLayout(5, 2));
			roomFrame.setTitle("Slot bearbeiten");
			Dimension minimumSize = new Dimension(250, 200);
			roomFrame.setMinimumSize(minimumSize);

			JLabel dateLabel = new JLabel("Datum:");
			roomFrame.add(dateLabel);

			final JTextField dateField = new JTextField(
					dateFormat.format(selectedSlot.getDate()));
			roomFrame.add(dateField);

			JLabel beginLabel = new JLabel("Beginn:");
			roomFrame.add(beginLabel);

			final JTextField beginField = new JTextField(
					timeFormat.format(selectedSlot.getBeginTime()));
			roomFrame.add(beginField);

			JLabel endLabel = new JLabel("Ende:");
			roomFrame.add(endLabel);

			final JTextField endField = new JTextField(
					timeFormat.format(selectedSlot.getEndTime()));
			roomFrame.add(endField);

			JButton doneButton = new JButton("OK");
			doneButton.addActionListener(new AbstractAction() {

				private static final long serialVersionUID = 2483720398415164553L;

				@Override
				public void actionPerformed(ActionEvent arg0) {

					boolean parseFailed = false;

					Date date = null;
					Date beginTime = null;
					Date endTime = null;

					try {
						date = dateFormat.parse(dateField.getText());
					} catch (ParseException e) {
						JOptionPane.showMessageDialog(null,
								"Datum muss Form tt.mm.jj haveb",
								"Datum nicht erkannt",
								JOptionPane.ERROR_MESSAGE);
						parseFailed = true;
					}

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
						selectedSlot.setDate(date);
						selectedSlot.setBeginTime(beginTime);
						selectedSlot.setEndTime(endTime);

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
			JOptionPane.showMessageDialog(null, "Kann nur Slot bearbeiten");
		}
	}

}
