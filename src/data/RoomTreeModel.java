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
package data;

import gui.Gui;

import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/**
 * This class holds the root node of the room tree model. A singleton.
 */
public class RoomTreeModel extends DefaultTreeModel {

	private RoomTreeModel(TreeNode name) {
		super(name);
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
		Enumeration<TreeNode> currentNode = root
				.preorderEnumeration();
		while (currentNode.hasMoreElements()) {
			if ((currentNode.nextElement()).isLeaf()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * delete all slots and rooms in the model
	 */
	public void clear() {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) instance
				.getRoot();
		root.removeAllChildren();
		Gui.getRoomTree().updateUI();
	}

}
