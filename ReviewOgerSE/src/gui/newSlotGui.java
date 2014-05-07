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
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import data.RoomTreeModel;
import data.Slot;

public class newSlotGui {

	public static void addSlot() {
		final JFrame slotFrame = new JFrame();

		final DateFormat df = new SimpleDateFormat("dd.MM.yy");


		slotFrame.setLayout(new GridLayout(2, 1));
		slotFrame.setTitle("Neuer Slot");
		Dimension minimumSize = new Dimension(250, 200);
		slotFrame.setMinimumSize(minimumSize);

		final JTextField dateField = new JTextField();
		slotFrame.add(dateField);

		JButton doneButton = new JButton("OK");
		doneButton.addActionListener(new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					Date date = df.parse(dateField.getText());
					Slot slot = new Slot(date);
					((DefaultMutableTreeNode) RoomTreeModel.getModel().getRoot())
                    .add(new DefaultMutableTreeNode(slot.getDate()));
					Gui.getRoomTree().updateUI();
					slotFrame.dispose();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
		slotFrame.add(doneButton);

		slotFrame.setVisible(true);
		slotFrame.pack();

	}

}
