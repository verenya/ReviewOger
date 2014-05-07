package data;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class RoomTreeModel{
	
	static DefaultTreeModel model = new DefaultTreeModel(new DefaultMutableTreeNode("root"));
	
	public static DefaultTreeModel getModel(){
		return model;
	}
	
}
