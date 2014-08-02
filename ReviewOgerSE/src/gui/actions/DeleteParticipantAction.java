/**
 * This class has an abstract action which is called when a participant should be deleted
 */
package gui.actions;

import gui.Gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import data.ParticipantTableModel;

public class DeleteParticipantAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2467904837184600786L;

	@Override
	public void actionPerformed(ActionEvent arg0) {
		ParticipantTableModel model = ParticipantTableModel.getInstance();
		int[] rows = Gui.getselectedParticipantRows();
		//not empty
		if (rows.length != 0) {
			model.deleteParticipants(rows);
		}
	}

}
