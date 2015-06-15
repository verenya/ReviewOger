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
package io;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * This class holds a dialog for the selection of files
 */
public class IODialog {
	/**
	 * Shows the save dialog and checks if file already exists
	 * 
	 * @return the selected file
	 */
	public static File showSaveDialog(String ending, boolean save) {
		JFileChooser fc = new JFileChooser();
		File file = null;
		if (ending.equals(".txt")) {
			fc.setFileFilter(new TxtDialogFilter());
		} else {
			fc.setFileFilter(new OgerDialogFilter());
		}
		int returnValue = fc.showSaveDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
			// correct ending
			if (!file.getAbsolutePath().endsWith(ending)) {
				file = new File(file.getAbsolutePath() + ending);
			}
			// if file exists, ask if overwrite
			if (file.exists()) {
				int result;
				if (save) {
					result = JOptionPane
							.showOptionDialog(
									null,
									"Die Datei existiert bereits. Möchten Sie überschreiben?",
									"Warnung", JOptionPane.YES_NO_OPTION,
									JOptionPane.INFORMATION_MESSAGE, null,
									null, null);
				} else {
					result = JOptionPane
							.showOptionDialog(
									null,
									"Die Datei existiert bereits. Möchten Sie überschreiben? Vorsicht: Es wird auch die Latex-Datei mit überschrieben!",
									"Warnung", JOptionPane.YES_NO_OPTION,
									JOptionPane.INFORMATION_MESSAGE, null,
									null, null);
				}
				if (result == JOptionPane.YES_OPTION) {
					return file;
				} else {
					// no overwriting -> show open dialog again
					return showSaveDialog(ending, save);
				}
			} else {
				return file;
			}
		} else {
			// canceled
			return null;
		}
	}

}
