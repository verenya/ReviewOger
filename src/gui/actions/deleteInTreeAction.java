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

import java.awt.event.ActionEvent;

import gui.Gui;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

import main.Main;
import data.RoomTreeModel;

/**
 * An abstract action to delete the whole slot room tree
 */
public class deleteInTreeAction extends AbstractAction {

	/**
	 * Deletes the whole review tree
	 */
	private static final long serialVersionUID = -2434755481535090453L;

	@Override
	public void actionPerformed(ActionEvent arg0) {
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) Gui
				.getRoomTree().getLastSelectedPathComponent();

		if (selectedNode.isRoot()) {
			JOptionPane.showMessageDialog(null,
					"Die Wurzel kann nicht gelöscht werden");
		} else {
			RoomTreeModel.getInstance().removeNodeFromParent(selectedNode);
			Gui.getRoomTree().updateUI();
			Main.setSaved(false);
		}
	}
}
