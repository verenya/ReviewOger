package main;

import gui.Gui;
import java.awt.EventQueue;

public class Main {

	private static boolean isSaved = true;
	static Gui gui;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui = Gui.getInstance();
					gui.setVisible(true);
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
		if (saved == true) {
			gui.setTitle("Gespeichert");
		} else {
			gui.setTitle("Nicht gespeichert");
		}
	}
}
