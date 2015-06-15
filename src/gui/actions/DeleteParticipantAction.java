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

import gui.Gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import main.Main;
import data.ParticipantTableModel;

/**
 * This class has an abstract action which is called when a participant should
 * be deleted
 */
public class DeleteParticipantAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2467904837184600786L;

	@Override
	public void actionPerformed(ActionEvent arg0) {
		ParticipantTableModel model = ParticipantTableModel.getInstance();
		int[] rows = Gui.getselectedParticipantRows();
		// not empty
		if (rows.length != 0) {
			model.deleteParticipants(rows);
			Main.setSaved(false);
		}
	}

}
