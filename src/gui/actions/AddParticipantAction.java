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

import main.Main;
import data.Participant;
import data.ParticipantTableModel;

/**
 * This class holds an abstract action which is called when a new participant
 * should be added
 */
public class AddParticipantAction extends AbstractAction {

	private JFrame parent;

	public AddParticipantAction(JFrame parent) {
		this.parent = parent;
	}

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
				String group = groupField.getText();

				Participant addedParticipant = new Participant(firstName, lastName, email, group);
				ParticipantTableModel model = ParticipantTableModel.getInstance();
				model.addParticipant(addedParticipant);
				Main.setSaved(false);
				participantFrame.dispose();

			}

		});

		participantFrame.setLocationRelativeTo(parent);
		participantFrame.setVisible(true);
		participantFrame.pack();

	}

}
