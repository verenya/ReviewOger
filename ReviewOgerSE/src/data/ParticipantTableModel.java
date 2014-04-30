/**
 * This class is the table model of the participant table
 */
package data;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ParticipantTableModel extends AbstractTableModel {

	/**
	 * All participants
	 */
	private static List<Participant> participants = new ArrayList<Participant>();

	/**
	 * an instance of the model to use the singleton pattern
	 */
	private static ParticipantTableModel instance;

	private ParticipantTableModel() {
	}

	/**
	 * An implementation of the singleton pattern
	 * 
	 * @return an instance of the model
	 */
	public static ParticipantTableModel getInstance() {
		if (ParticipantTableModel.instance == null) {
			ParticipantTableModel.instance = new ParticipantTableModel();
		}
		return ParticipantTableModel.instance;
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public int getRowCount() {
		return participants.size();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return "Vorname";
		case 1:
			return "Nachname";
		case 2:
			return "E-Mail";
		case 3:
			return "Gruppe";
		default:
			return null;
		}
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		if (arg1 == 0) {
			return participants.get(arg0).getFirstName();
		} else if (arg1 == 1) {
			return participants.get(arg0).getLastName();
		} else if (arg1 == 2) {
			return participants.get(arg0).geteMailAdress();
		} else {
			return participants.get(arg0).getGroupNumber();
		}
	}

	/**
	 * @return the list with all participants
	 */
	public static List<Participant> getParticipants() {
		return participants;
	}

	/**
	 * adds a new participant
	 * 
	 * @param newParticipant
	 */
	public void addParticipant(Participant newParticipant) {
		participants.add(newParticipant);
		fireTableDataChanged();
	}

	/**
	 * deletes a participant
	 * 
	 * @param index
	 *            the index of the participant
	 */
	public void deleteParticipant(int index) {
		participants.remove(index);
		fireTableDataChanged();
	}

	/**
	 * @param index
	 *            the index of the wanted participant
	 * @return the participant at the given index
	 */
	public Participant getParticipantAt(int index) {
		return participants.get(index);
	}

}
