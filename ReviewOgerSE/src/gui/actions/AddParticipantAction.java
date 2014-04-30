package gui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import data.Participant;
import data.ParticipantTableModel;

public class AddParticipantAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2467904837184600786L;

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Participant testParticipant = new Participant("Max", "Mustermann", "ich.da@dort.de", 1);
		ParticipantTableModel model = ParticipantTableModel.getInstance();
		model.addParticipant(testParticipant);
	}

}
