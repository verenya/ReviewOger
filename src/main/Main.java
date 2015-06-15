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
package main;

import gui.Gui;
import java.awt.EventQueue;

/**
 * the main class
 */
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

	/**
	 * @param saved
	 *            sets if the current status is saved
	 */
	public static void setSaved(boolean saved) {
		isSaved = saved;
		if (saved == true) {
			gui.setTitle("Gespeichert");
		} else {
			gui.setTitle("Nicht gespeichert");
		}
	}
}
