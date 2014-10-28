/**
 *An abstract action to delete the whole slot room tree 
 */
package gui.actions;

import java.awt.event.ActionEvent;

import gui.Gui;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

import main.Main;
import data.RoomTreeModel;

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
