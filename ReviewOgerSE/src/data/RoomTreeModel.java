/**
 * This class holds the root node of the room tree model. A singleton.
 */
package data;

import gui.Gui;

import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

public class RoomTreeModel extends DefaultTreeModel{

	private RoomTreeModel(TreeNode name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static RoomTreeModel instance = null;



	/**
	 * @return the current model
	 */
	public static RoomTreeModel getInstance() {
		if (instance == null) {
			instance = new RoomTreeModel(new SlotNode());
		}
		return instance;

	}

	/**
	 * @return true, if any review exists
	 */
	public static boolean hasReviews() {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) instance
				.getRoot();
		Enumeration<DefaultMutableTreeNode> currentNode = root
				.preorderEnumeration();
		while (currentNode.hasMoreElements()) {
			if ((currentNode.nextElement()).isLeaf()) {
				return true;
			}
		}
		return false;
	}
	
	public void clear(){
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) instance
				.getRoot();
		root.removeAllChildren();
		Gui.getRoomTree().updateUI();
	}

}
