package main;

import gui.Gui;
import java.awt.EventQueue;

public class Main {
	
	private static boolean isSaved = true;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public static boolean isSaved() {
		return isSaved;
	}
	public static void setSaved(boolean saved) {
		isSaved = saved;
	}
}
