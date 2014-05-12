/**
 * This class holds the root node of the room tree model
 */
package data;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class RoomTreeModel {

	static DefaultTreeModel model = new DefaultTreeModel(
			new DefaultMutableTreeNode("slots"));

	/**
	 * @return the current model
	 */
	public static DefaultTreeModel getModel() {
		return model;
	}

}
