/**
 * This class implements an abstract action that is called when a participant should be edited
 */
package gui.actions;

import gui.Gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.Main;
import data.Participant;
import data.ParticipantTableModel;

public class EditParticipantAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3746794815943075198L;

	@Override
	public void actionPerformed(ActionEvent arg0) {
		ParticipantTableModel model = ParticipantTableModel.getInstance();
		int[] rows = Gui.getselectedParticipantRows();

		if (rows.length >1) {
			JOptionPane.showMessageDialog(null,
					"Es kann nur ein Teilnehmer gleichzeitig geändert werden");
		} else {

			final Participant selectedParticipant = model
					.getParticipantAt(rows[0]);

			final JFrame participantFrame = new JFrame();
			participantFrame.setLayout(new GridLayout(2, 1));
			participantFrame.setTitle("Neuer Teilnehmer");
			Dimension minimumSize = new Dimension(250, 200);
			participantFrame.setMinimumSize(minimumSize);

			JPanel inputPane = new JPanel();
			inputPane.setLayout(new GridLayout(4, 2));
			participantFrame.add(inputPane);

			JLabel firstNameLabel = new JLabel("Vorname:");
			inputPane.add(firstNameLabel);

			final JTextField firstNameField = new JTextField();
			firstNameField.setText(selectedParticipant.getFirstName());
			inputPane.add(firstNameField);

			JLabel lastNameLabel = new JLabel("Nachname:");
			inputPane.add(lastNameLabel);

			final JTextField lastNameField = new JTextField();
			lastNameField.setText(selectedParticipant.getLastName());
			inputPane.add(lastNameField);

			JLabel emailLabel = new JLabel("E-Mail:");
			inputPane.add(emailLabel);

			final JTextField emailField = new JTextField();
			emailField.setText(selectedParticipant.geteMailAdress());
			inputPane.add(emailField);

			JLabel groupLabel = new JLabel("Gruppe");
			inputPane.add(groupLabel);

			final JTextField groupField = new JTextField();
			groupField.setText(Integer.toString(selectedParticipant
					.getGroupNumber()));
			inputPane.add(groupField);

			JButton doneButton = new JButton("OK");
			participantFrame.add(doneButton);

			doneButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					String firstName = firstNameField.getText();
					String lastName = lastNameField.getText();
					String email = emailField.getText();
					int group = 0;
					Boolean numberError = false;
					try {
						group = Integer.parseInt(groupField.getText());
					} catch (NumberFormatException e1) {
						numberError = true;
					}
					if (numberError) {
						JOptionPane.showMessageDialog(null,
								"Die Gruppe muss eine Zahl sein!", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						selectedParticipant.setFirstName(firstName);
						selectedParticipant.setLastName(lastName);
						selectedParticipant.seteMailAdress(email);
						selectedParticipant.setGroupNumber(group);
						ParticipantTableModel model = ParticipantTableModel
								.getInstance();
						model.fireTableDataChanged();
						Main.setSaved(false);
						participantFrame.dispose();
					}
				}

			});

			participantFrame.setVisible(true);
			participantFrame.pack();
		}
	}

}
