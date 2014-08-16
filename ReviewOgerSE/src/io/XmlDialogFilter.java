package io;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * A filter for xml files only
 * 
 * @author Verena Käfer
 * 
 */
public class XmlDialogFilter extends FileFilter {

	/*
	 * shows only xml files and directories
	 * 
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
	 */
	@Override
	public boolean accept(File arg0) {
		if (arg0.getName().endsWith(".xml") || arg0.isDirectory()) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.filechooser.FileFilter#getDescription()
	 */
	@Override
	public String getDescription() {
		return ".xml";
	}

}
