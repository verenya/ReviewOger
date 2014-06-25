package gui;

import gui.actions.AddParticipantAction;
import gui.actions.DeleteParticipantAction;
import gui.actions.EditParticipantAction;
import gui.actions.EditRoomAction;
import gui.actions.ReadParticipantsAction;
import gui.actions.deleteTreeAction;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;

import logic.Matcher;
import data.ParticipantTableModel;
import data.RoomTreeModel;

public class Gui extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField reviewerNumberTextField;
	private static JTable participantTable;
	private static JTree roomTree;
	private JTextField amountRoundsTextField;
	private final JComboBox<String> exitOptionComboBox;
	private JTextField hourTextField;
	private JTextField minuteTextField;
	private JCheckBox scribeIsAuthorCheckbox;
	private JCheckBox moderatorNotReviewerGroupCheckbox;

	/**
	 * Creates the main frame. There are five main components. The menu bar. The
	 * participant panel which shows the list of current participants and
	 * methods to edit it. The room panel which shows the current rooms. The
	 * option panel with available options. The start button.
	 */
	public Gui() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 414);

		// Menu
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("Datei");
		menuBar.add(mnFile);

		JMenuItem menuSave = new JMenuItem("Speichern");
		mnFile.add(menuSave);

		JMenuItem menuLoadParticipant = new JMenuItem(
				new ReadParticipantsAction());
		menuLoadParticipant.setText("Teilnehmer einlesen");
		mnFile.add(menuLoadParticipant);

		// participant panel
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 276, 296, 314 };
		gridBagLayout.rowHeights = new int[] { 43, 56, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0 };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, 0.0,
				Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		JPanel participantPanel = new JPanel();
		GridBagConstraints gbc_participantPanel = new GridBagConstraints();
		gbc_participantPanel.insets = new Insets(0, 0, 5, 5);
		gbc_participantPanel.gridx = 0;
		gbc_participantPanel.gridy = 0;
		getContentPane().add(participantPanel, gbc_participantPanel);
		GridBagLayout gbl_participantPanel = new GridBagLayout();
		gbl_participantPanel.columnWidths = new int[] { 193, 0 };
		gbl_participantPanel.rowHeights = new int[] { 211, 65, 0 };
		gbl_participantPanel.columnWeights = new double[] { 1.0,
				Double.MIN_VALUE };
		gbl_participantPanel.rowWeights = new double[] { 1.0, 0.0,
				Double.MIN_VALUE };
		participantPanel.setLayout(gbl_participantPanel);

		JScrollPane participantScrollPane = new JScrollPane();
		GridBagConstraints gbc_participantScrollPane = new GridBagConstraints();
		gbc_participantScrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_participantScrollPane.fill = GridBagConstraints.BOTH;
		gbc_participantScrollPane.gridx = 0;
		gbc_participantScrollPane.gridy = 0;
		participantPanel.add(participantScrollPane, gbc_participantScrollPane);

		participantTable = new JTable(ParticipantTableModel.getInstance());
		participantTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		participantScrollPane.setViewportView(participantTable);

		JPanel participantButtonPanle = new JPanel();
		GridBagConstraints gbc_participantButtonPanle = new GridBagConstraints();
		gbc_participantButtonPanle.fill = GridBagConstraints.BOTH;
		gbc_participantButtonPanle.gridx = 0;
		gbc_participantButtonPanle.gridy = 1;
		participantPanel
				.add(participantButtonPanle, gbc_participantButtonPanle);
		GridBagLayout gbl_participantButtonPanle = new GridBagLayout();
		gbl_participantButtonPanle.columnWidths = new int[] { 115, 136, 0 };
		gbl_participantButtonPanle.rowHeights = new int[] { 25, 25, 0 };
		gbl_participantButtonPanle.columnWeights = new double[] { 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_participantButtonPanle.rowWeights = new double[] { 0.0, 0.0,
				Double.MIN_VALUE };
		participantButtonPanle.setLayout(gbl_participantButtonPanle);

		JButton participantAddButton = new JButton(new AddParticipantAction());
		GridBagConstraints gbc_participantAddButton = new GridBagConstraints();
		gbc_participantAddButton.fill = GridBagConstraints.BOTH;
		gbc_participantAddButton.insets = new Insets(0, 0, 5, 5);
		gbc_participantAddButton.gridx = 0;
		gbc_participantAddButton.gridy = 0;
		participantButtonPanle.add(participantAddButton,
				gbc_participantAddButton);

		participantAddButton.setText("Hinzufügen");

		JButton participantDeleteButton = new JButton(
				new DeleteParticipantAction());
		GridBagConstraints gbc_participantDeleteButton = new GridBagConstraints();
		gbc_participantDeleteButton.anchor = GridBagConstraints.NORTH;
		gbc_participantDeleteButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_participantDeleteButton.insets = new Insets(0, 0, 5, 0);
		gbc_participantDeleteButton.gridx = 1;
		gbc_participantDeleteButton.gridy = 0;
		participantButtonPanle.add(participantDeleteButton,
				gbc_participantDeleteButton);

		participantDeleteButton.setText("Löschen");

		JButton participantEditButton = new JButton(new EditParticipantAction());
		GridBagConstraints gbc_participantEditButton = new GridBagConstraints();
		gbc_participantEditButton.fill = GridBagConstraints.BOTH;
		gbc_participantEditButton.gridwidth = 2;
		gbc_participantEditButton.gridx = 0;
		gbc_participantEditButton.gridy = 1;
		participantButtonPanle.add(participantEditButton,
				gbc_participantEditButton);

		participantEditButton.setText("Bearbeiten");

		// room panel
		JPanel roomPanel = new JPanel();
		GridBagConstraints gbc_roomPanel = new GridBagConstraints();
		gbc_roomPanel.fill = GridBagConstraints.VERTICAL;
		gbc_roomPanel.insets = new Insets(0, 0, 5, 5);
		gbc_roomPanel.gridx = 1;
		gbc_roomPanel.gridy = 0;
		getContentPane().add(roomPanel, gbc_roomPanel);
		GridBagLayout gbl_roomPanel = new GridBagLayout();
		gbl_roomPanel.columnWidths = new int[] { 317, 0 };
		gbl_roomPanel.rowHeights = new int[] { 0, 0 };
		gbl_roomPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_roomPanel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		roomPanel.setLayout(gbl_roomPanel);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		roomPanel.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 193, 0 };
		gbl_panel.rowHeights = new int[] { 211, 65, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JScrollPane roomScrollPane = new JScrollPane();
		GridBagConstraints gbc_roomScrollPane = new GridBagConstraints();
		gbc_roomScrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_roomScrollPane.fill = GridBagConstraints.BOTH;
		gbc_roomScrollPane.gridx = 0;
		gbc_roomScrollPane.gridy = 0;
		panel.add(roomScrollPane, gbc_roomScrollPane);

		roomTree = new JTree(RoomTreeModel.getInstance());
		roomScrollPane.setViewportView(roomTree);

		// TODO edit slot

		JPanel roomButtonPanel = new JPanel();
		GridBagConstraints gbc_roomButtonPanel = new GridBagConstraints();
		gbc_roomButtonPanel.insets = new Insets(0, 0, 5, 0);
		gbc_roomButtonPanel.fill = GridBagConstraints.BOTH;
		gbc_roomButtonPanel.gridx = 0;
		gbc_roomButtonPanel.gridy = 1;
		panel.add(roomButtonPanel, gbc_roomButtonPanel);
		GridBagLayout gbl_roomButtonPanel = new GridBagLayout();
		gbl_roomButtonPanel.columnWidths = new int[] { 115, 136, 0 };
		gbl_roomButtonPanel.rowHeights = new int[] { 25, 25, 0, 0 };
		gbl_roomButtonPanel.columnWeights = new double[] { 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_roomButtonPanel.rowWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		roomButtonPanel.setLayout(gbl_roomButtonPanel);

		JButton newSlotButton = new JButton("Neuer Slot");
		newSlotButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewSlotGui.addSlot();
			}
		});

		GridBagConstraints gbc_newSlotButton = new GridBagConstraints();
		gbc_newSlotButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_newSlotButton.insets = new Insets(0, 0, 5, 5);
		gbc_newSlotButton.gridx = 0;
		gbc_newSlotButton.gridy = 0;
		roomButtonPanel.add(newSlotButton, gbc_newSlotButton);

		JButton newRoomButton = new JButton("Neuer Raum");
		newRoomButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewRoomGui.addRoom();
			}
		});
		GridBagConstraints gbc_newRoomButton = new GridBagConstraints();
		gbc_newRoomButton.fill = GridBagConstraints.BOTH;
		gbc_newRoomButton.insets = new Insets(0, 0, 5, 0);
		gbc_newRoomButton.gridx = 1;
		gbc_newRoomButton.gridy = 0;
		roomButtonPanel.add(newRoomButton, gbc_newRoomButton);

		JButton roomDeleteButton = new JButton(new deleteTreeAction());
		roomDeleteButton.setText("Löschen");
		GridBagConstraints gbc_roomDeleteButton = new GridBagConstraints();
		gbc_roomDeleteButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_roomDeleteButton.insets = new Insets(0, 0, 5, 5);
		gbc_roomDeleteButton.gridx = 0;
		gbc_roomDeleteButton.gridy = 1;
		roomButtonPanel.add(roomDeleteButton, gbc_roomDeleteButton);

		JButton roomEditButton = new JButton(new EditRoomAction());
		roomEditButton.setText("Raum Bearbeiten");
		GridBagConstraints gbc_roomEditButton = new GridBagConstraints();
		gbc_roomEditButton.insets = new Insets(0, 0, 5, 0);
		gbc_roomEditButton.fill = GridBagConstraints.BOTH;
		gbc_roomEditButton.gridx = 1;
		gbc_roomEditButton.gridy = 1;
		roomButtonPanel.add(roomEditButton, gbc_roomEditButton);

		// option panel
		JPanel optionPanel = new JPanel();
		GridBagConstraints gbc_optionPanel = new GridBagConstraints();
		gbc_optionPanel.insets = new Insets(0, 0, 5, 0);
		gbc_optionPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_optionPanel.gridx = 2;
		gbc_optionPanel.gridy = 0;
		getContentPane().add(optionPanel, gbc_optionPanel);
		GridBagLayout gbl_optionPanel = new GridBagLayout();
		gbl_optionPanel.columnWidths = new int[] { 123, 39, -18, 0 };
		gbl_optionPanel.rowHeights = new int[] { 43, 0, 0, 0, 0, 0, 0, 32, 0, 0 };
		gbl_optionPanel.columnWeights = new double[] { 1.0, 1.0, 1.0,
				Double.MIN_VALUE };
		gbl_optionPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		optionPanel.setLayout(gbl_optionPanel);

		JLabel reviewerLabel = new JLabel("  Anzahl Gutachter: ");
		GridBagConstraints gbc_reviewerLabel = new GridBagConstraints();
		gbc_reviewerLabel.fill = GridBagConstraints.BOTH;
		gbc_reviewerLabel.insets = new Insets(0, 0, 5, 5);
		gbc_reviewerLabel.gridx = 0;
		gbc_reviewerLabel.gridy = 1;
		optionPanel.add(reviewerLabel, gbc_reviewerLabel);

		reviewerNumberTextField = new JTextField();
		reviewerNumberTextField.setColumns(2);
		GridBagConstraints gbc_reviewerNumberTextField = new GridBagConstraints();
		gbc_reviewerNumberTextField.insets = new Insets(0, 0, 5, 5);
		gbc_reviewerNumberTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_reviewerNumberTextField.gridx = 1;
		gbc_reviewerNumberTextField.gridy = 1;
		optionPanel.add(reviewerNumberTextField, gbc_reviewerNumberTextField);

		scribeIsAuthorCheckbox = new JCheckBox("Notar ist Author");
		GridBagConstraints gbc_scribeIsAuthorCheckbox = new GridBagConstraints();
		gbc_scribeIsAuthorCheckbox.fill = GridBagConstraints.HORIZONTAL;
		gbc_scribeIsAuthorCheckbox.insets = new Insets(0, 0, 5, 5);
		gbc_scribeIsAuthorCheckbox.gridwidth = 2;
		gbc_scribeIsAuthorCheckbox.gridx = 0;
		gbc_scribeIsAuthorCheckbox.gridy = 2;
		optionPanel.add(scribeIsAuthorCheckbox, gbc_scribeIsAuthorCheckbox);

		moderatorNotReviewerGroupCheckbox = new JCheckBox(
				"Moderator nicht aus Gutachter-Gruppe");
		GridBagConstraints gbc_moderatorNotReviewerGroupCheckbox = new GridBagConstraints();
		gbc_moderatorNotReviewerGroupCheckbox.fill = GridBagConstraints.HORIZONTAL;
		gbc_moderatorNotReviewerGroupCheckbox.insets = new Insets(0, 0, 5, 5);
		gbc_moderatorNotReviewerGroupCheckbox.gridwidth = 2;
		gbc_moderatorNotReviewerGroupCheckbox.gridx = 0;
		gbc_moderatorNotReviewerGroupCheckbox.gridy = 3;
		optionPanel.add(moderatorNotReviewerGroupCheckbox,
				gbc_moderatorNotReviewerGroupCheckbox);

		JLabel exitOptionLabel = new JLabel("  Abbruchbedingung:");
		GridBagConstraints gbc_exitOptionLabel = new GridBagConstraints();
		gbc_exitOptionLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_exitOptionLabel.insets = new Insets(0, 0, 5, 5);
		gbc_exitOptionLabel.gridx = 0;
		gbc_exitOptionLabel.gridy = 4;
		optionPanel.add(exitOptionLabel, gbc_exitOptionLabel);

		final JLabel amountRoundsLabel = new JLabel("  Anzahl Ausführungen:");
		GridBagConstraints gbc_amountRoundsLabel = new GridBagConstraints();
		gbc_amountRoundsLabel.fill = GridBagConstraints.BOTH;
		gbc_amountRoundsLabel.insets = new Insets(0, 0, 5, 5);
		gbc_amountRoundsLabel.gridx = 0;
		gbc_amountRoundsLabel.gridy = 6;

		amountRoundsTextField = new JTextField();
		GridBagConstraints gbc_amountRoundsTextField = new GridBagConstraints();
		gbc_amountRoundsTextField.insets = new Insets(0, 0, 5, 5);
		gbc_amountRoundsTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_amountRoundsTextField.gridx = 1;
		gbc_amountRoundsTextField.gridy = 6;

		amountRoundsTextField.setColumns(10);

		final JLabel fixedTimeLabel = new JLabel("  Abbruchzeit:");
		GridBagConstraints gbc_fixedTimeLabel = new GridBagConstraints();
		gbc_fixedTimeLabel.fill = GridBagConstraints.BOTH;
		gbc_fixedTimeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_fixedTimeLabel.gridx = 0;
		gbc_fixedTimeLabel.gridy = 7;

		exitOptionComboBox = new JComboBox<String>();
		exitOptionComboBox.setModel(new DefaultComboBoxModel(new String[] {
				"feste Anzahl", "manueller Abbruch", "feste Zeit" }));
		GridBagConstraints gbc_exitOptionComboBox = new GridBagConstraints();
		gbc_exitOptionComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_exitOptionComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_exitOptionComboBox.gridwidth = 2;
		gbc_exitOptionComboBox.gridx = 0;
		gbc_exitOptionComboBox.gridy = 5;
		optionPanel.add(exitOptionComboBox, gbc_exitOptionComboBox);

		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 7;
		optionPanel.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 26, 31, 21, 25, 0 };
		gbl_panel_1.rowHeights = new int[] { 19, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		final JLabel hourLabel = new JLabel(" h:");
		GridBagConstraints gbc_hourLAbel = new GridBagConstraints();
		gbc_hourLAbel.fill = GridBagConstraints.HORIZONTAL;
		gbc_hourLAbel.insets = new Insets(0, 0, 0, 5);
		gbc_hourLAbel.gridx = 0;
		gbc_hourLAbel.gridy = 0;
		panel_1.add(hourLabel, gbc_hourLAbel);

		hourTextField = new JTextField();
		hourTextField.setText("01");
		GridBagConstraints gbc_hourTextField = new GridBagConstraints();
		gbc_hourTextField.fill = GridBagConstraints.BOTH;
		gbc_hourTextField.insets = new Insets(0, 0, 0, 5);
		gbc_hourTextField.gridx = 1;
		gbc_hourTextField.gridy = 0;
		panel_1.add(hourTextField, gbc_hourTextField);
		hourTextField.setColumns(10);

		final JLabel minuteLabel = new JLabel(" m:");
		GridBagConstraints gbc_minuteLabel = new GridBagConstraints();
		gbc_minuteLabel.fill = GridBagConstraints.BOTH;
		gbc_minuteLabel.insets = new Insets(0, 0, 0, 5);
		gbc_minuteLabel.gridx = 2;
		gbc_minuteLabel.gridy = 0;
		panel_1.add(minuteLabel, gbc_minuteLabel);

		minuteTextField = new JTextField();
		minuteTextField.setText("00");
		GridBagConstraints gbc_minuteTextField = new GridBagConstraints();
		gbc_minuteTextField.fill = GridBagConstraints.BOTH;
		gbc_minuteTextField.gridx = 3;
		gbc_minuteTextField.gridy = 0;
		panel_1.add(minuteTextField, gbc_minuteTextField);
		minuteTextField.setColumns(10);

		fixedTimeLabel.setEnabled(false);
		hourLabel.setEnabled(false);
		minuteLabel.setEnabled(false);
		hourTextField.setEnabled(false);
		minuteTextField.setEnabled(false);

		exitOptionComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// only for selection events
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					String selectedString = exitOptionComboBox
							.getSelectedItem().toString();
					// select and reselect gui elements
					if (selectedString.equals("feste Anzahl")) {
						amountRoundsLabel.setEnabled(true);
						amountRoundsTextField.setEditable(true);
						fixedTimeLabel.setEnabled(false);
						hourLabel.setEnabled(false);
						minuteLabel.setEnabled(false);
						hourTextField.setEnabled(false);
						minuteTextField.setEnabled(false);

					} else if (selectedString.equals("manueller Abbruch")) {
						amountRoundsLabel.setEnabled(false);
						amountRoundsTextField.setEditable(false);
						fixedTimeLabel.setEnabled(false);
						hourLabel.setEnabled(false);
						minuteLabel.setEnabled(false);
						hourTextField.setEnabled(false);
						minuteTextField.setEnabled(false);

					} else if (selectedString.equals("feste Zeit")) {
						amountRoundsLabel.setEnabled(false);
						amountRoundsTextField.setEditable(false);
						fixedTimeLabel.setEnabled(true);
						hourLabel.setEnabled(true);
						minuteLabel.setEnabled(true);
						hourTextField.setEnabled(true);
						minuteTextField.setEnabled(true);
					}
				}
			}
		});

		optionPanel.add(amountRoundsLabel, gbc_amountRoundsLabel);
		optionPanel.add(amountRoundsTextField, gbc_amountRoundsTextField);

		optionPanel.add(fixedTimeLabel, gbc_fixedTimeLabel);

		// start button
		JButton startButton = new JButton("Berechnung beginnen");
		GridBagConstraints gbc_startButton = new GridBagConstraints();
		gbc_startButton.insets = new Insets(0, 0, 5, 0);
		gbc_startButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_startButton.gridwidth = 3;
		gbc_startButton.gridx = 0;
		gbc_startButton.gridy = 1;
		getContentPane().add(startButton, gbc_startButton);

		startButton.addActionListener(new AbstractAction() {
			private static final long serialVersionUID = -6539453133180730039L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean optionsOk = checkOptions();
				if (optionsOk) {
					String selectedString = exitOptionComboBox
							.getSelectedItem().toString();
					Matcher matcher = new Matcher(selectedString,
							scribeIsAuthorCheckbox.isSelected(),
							moderatorNotReviewerGroupCheckbox.isSelected(),
							Integer.parseInt(reviewerNumberTextField.getText()));
					ExecutingFrame frame = new ExecutingFrame();
					frame.showFrame(matcher);
				}

			}

		});

	}

	/**
	 * @return the selected row in the table of the participants
	 */
	public static int getselectedParticipantRow() {
		return participantTable.getSelectedRow();
	}

	public static JTree getRoomTree() {
		return roomTree;
	}

	/**
	 * This method checks if all necessary fields/table are filled
	 * 
	 * @return true if everything is ok
	 */
	private boolean checkOptions() {
		String selectedString = exitOptionComboBox.getSelectedItem().toString();

		// no rounds
		if (selectedString.equals("feste Anzahl")) {
			try {
				int rounds = Integer.parseInt(amountRoundsTextField.getText());
				if (rounds <= 0) {
					JOptionPane.showMessageDialog(null,
							"Anzahl Runden muss >0 sein!",
							"Anzahl Runden Error", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			} catch (NumberFormatException fne) {
				JOptionPane.showMessageDialog(null,
						"Anzahl Runden muss eine Zahl sein!",
						"Anzahl Runden Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}

		}

		// no time
		if (selectedString.equals("feste Zeit")) {
			try {
				int hours = Integer.parseInt(hourTextField.getText());
				int minutes = Integer.parseInt(minuteTextField.getText());

				if (hours == 0 && minutes == 0) {
					JOptionPane.showMessageDialog(null,
							"Zeit darf nicht 0 sein!", "Zeit 0",
							JOptionPane.ERROR_MESSAGE);
					return false;
				}

			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null,
						"Gewählte Zeit muss eine Zahl sein!", "Zeit Error",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}

		// no participants
		if (ParticipantTableModel.getInstance().isEmpty()) {
			JOptionPane.showMessageDialog(null,
					"Es muss mindestens einen Teilnehmer geben!",
					"Teilnehmerliste leer", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// no reviews
		if (!RoomTreeModel.hasReviews()) {
			JOptionPane.showMessageDialog(null,
					"Es muss mindestens ein Review geben!", "Keine Reviews",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// no reviewers
		String reviewerText = reviewerNumberTextField.getText();
		try {
			int reviewerNumber = Integer.parseInt(reviewerText);
			if (reviewerNumber <= 0) {
				JOptionPane.showMessageDialog(null,
						"Anzahl Gutachter muss >0 sein!",
						"Anzahl Gutachter Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(null,
					"Anzahl Gutachter muss eine Zahl sein!",
					"Anzahl Gutachter Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

}
