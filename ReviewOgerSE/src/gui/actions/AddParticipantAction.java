/**
 * This class holds an abstract action which is called when a new participant shall be added
 */
package gui.actions;

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

import data.Participant;
import data.ParticipantTableModel;

public class AddParticipantAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2467904837184600786L;

	@Override
	public void actionPerformed(ActionEvent arg0) {
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
		inputPane.add(firstNameField);

		JLabel lastNameLabel = new JLabel("Nachname:");
		inputPane.add(lastNameLabel);

		final JTextField lastNameField = new JTextField();
		inputPane.add(lastNameField);

		JLabel emailLabel = new JLabel("E-Mail:");
		inputPane.add(emailLabel);

		final JTextField emailField = new JTextField();
		inputPane.add(emailField);

		JLabel groupLabel = new JLabel("Gruppe");
		inputPane.add(groupLabel);

		final JTextField groupField = new JTextField();
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
							"Die Gruppe muss eine Zahl sein", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					Participant addedParticipant = new Participant(firstName,
							lastName, email, group);
					ParticipantTableModel model = ParticipantTableModel
							.getInstance();
					model.addParticipant(addedParticipant);
					participantFrame.dispose();
				}
			}

		});

		participantFrame.setVisible(true);
		participantFrame.pack();

	}

}
