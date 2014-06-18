/**
 * This class holds the root node of the room tree model
 */
package data;

import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class RoomTreeModel {

	private static DefaultTreeModel instance = null;

	private RoomTreeModel() {
	}

	/**
	 * @return the current model
	 */
	public static DefaultTreeModel getInstance() {
		if (instance == null) {
			instance = new DefaultTreeModel(new SlotNode());
		}
		return instance;

	}

	/**
	 * @return true, if any reviews exist
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

}
