package gui.actions;

import java.awt.event.ActionEvent;

import gui.Gui;

import javax.swing.AbstractAction;
import javax.swing.tree.DefaultMutableTreeNode;

import data.RoomTreeModel;

public class deleteTreeAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2434755481535090453L;

	@Override
	public void actionPerformed(ActionEvent arg0) {
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) Gui
				.getRoomTree().getLastSelectedPathComponent();

		
		//there must be a selected node and it must not be root
		if (selectedNode != null && !selectedNode.isRoot()) {
			RoomTreeModel.getInstance().removeNodeFromParent(selectedNode);
			Gui.getRoomTree().updateUI();
		}
	}
}
