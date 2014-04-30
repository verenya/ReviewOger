package data;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ParticipantTableModel extends AbstractTableModel {

	private static List<Participant> participants = new ArrayList<Participant>();

	private static ParticipantTableModel instance;

	private ParticipantTableModel() {
	}

	public static ParticipantTableModel getInstance() {
		if (ParticipantTableModel.instance == null) {
			ParticipantTableModel.instance = new ParticipantTableModel();
		}
		return ParticipantTableModel.instance;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5928928394242503836L;

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

	public static List<Participant> getParticipants() {
		return participants;
	}

	public void addParticipant(Participant newParticipant) {
		participants.add(newParticipant);
		fireTableDataChanged();
	}
	
	public void deleteParticipant(int row){
		participants.remove(row);
		fireTableDataChanged();
	}

}
