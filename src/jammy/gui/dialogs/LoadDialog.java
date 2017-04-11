package jammy.gui.dialogs;

import jammy.catalogModel.Media;
import jammy.gui.MediaListPane;
import jammy.catalogModel.MediaCatalog;
import jammy.mediaBuilder.MediaFactory;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.File;
import java.text.DecimalFormat;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import jammy.mediaBuilder.*;


public class LoadDialog extends JFrame implements ActionListener{

	JPanel mainPanel;
	JPanel openPanel;
	InputFieldPanel inputFieldPanel;
	JPanel groupPanel;

	JButton openButton;
	JButton importButton;
	JButton closeButton;
	ButtonGroup mediaTypeGroup;
	JRadioButton dvdRadio;
	JRadioButton cdRadio;
	JRadioButton hddRadio;

	
	int mediaType = Media.DVD;
	MediaCatalog catalog;
	MediaListPane mediaListPane;
	DecimalFormat decimalFormatter = new DecimalFormat("0000");

	
	public LoadDialog(MediaCatalog catalog,MediaListPane mediaListPane,int mediaType){
		this(catalog,mediaListPane);
	}
	
	public LoadDialog(MediaCatalog catalog,MediaListPane mediaListPane){
		this.catalog = catalog;
		this.mediaListPane = mediaListPane;
		
		setTitle("Import new Media");
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
		
		// inutfield panel
		inputFieldPanel = new InputFieldPanel();
		
		// open Panel
		openButton = new JButton("Open");
		closeButton = new JButton("Close");
		openButton.addActionListener(this);
		importButton = new JButton("Import");
		importButton.addActionListener(this);
		openPanel = new JPanel();

		openPanel.add(inputFieldPanel);
		openPanel.add(openButton);
		openPanel.add(importButton);
		
		// group Panel
		dvdRadio = new JRadioButton("DVD");
		dvdRadio.setActionCommand(new Integer(Media.DVD).toString());
		dvdRadio.setSelected(true);
		dvdRadio.addActionListener(this);
		
		cdRadio = new JRadioButton("CD");
		cdRadio.setActionCommand(new Integer(Media.CD).toString());
		cdRadio.addActionListener(this);
		
		hddRadio = new JRadioButton("HDD");
		hddRadio.setActionCommand(new Integer(Media.HDD).toString());
		hddRadio.addActionListener(this);
		
		mediaTypeGroup = new ButtonGroup();
		mediaTypeGroup.add(dvdRadio);
		mediaTypeGroup.add(cdRadio);
		mediaTypeGroup.add(hddRadio);
		
		
		// close button
		closeButton.addActionListener(this);
		
		// add all
		groupPanel = new JPanel();
		groupPanel.add(dvdRadio);
		groupPanel.add(cdRadio);
		groupPanel.add(hddRadio);
		
		mainPanel.add(openPanel);
		mainPanel.add(groupPanel);
		mainPanel.add(closeButton);
		
		add(mainPanel);
		pack();
		setLocationRelativeTo(mediaListPane.getTopLevelAncestor());
		setVisible(true);
		setResizable(false);
	}

	public void actionPerformed(ActionEvent e) {
		
		//System.out.println("+++ ActionEvent +++");
		
		// handle importbutton click. read subfolders and import the created media to the catalog
		if(e.getSource() == importButton){
			this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			catalog.addNewMedia(MediaFactory.generateMedia(inputFieldPanel.sourceTextField.getText()+File.separator, 
														   mediaType,
														   inputFieldPanel.mediaNameTextField.getText(),
														   inputFieldPanel.discNameTextField.getText()));
			// increment media name
			this.setCursor(Cursor.getDefaultCursor());
			inputFieldPanel.mediaNameTextField.setText(decimalFormatter.format(Media.nextMediaID));
			mediaListPane.refreshList();
			
		}
		
		// handle groupbutton action
		if(e.getSource() == cdRadio ||e.getSource() == dvdRadio||e.getSource() == hddRadio){
			System.out.println("group changed"+" "+e.getActionCommand());
			mediaType = Integer.parseInt(e.getActionCommand());
		}
		
		// open button pressed, start filechooser
		if(e.getSource() == openButton){
			JFileChooser dirChooser = new JFileChooser();
			dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int retval = dirChooser.showOpenDialog(null); 
			if(retval == JFileChooser.APPROVE_OPTION){
				inputFieldPanel.sourceTextField.setText(dirChooser.getSelectedFile().getAbsolutePath());
			}
		}
		
		if(e.getSource() == closeButton){
			setVisible(false);
			dispose();
		}
	}


}