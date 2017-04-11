package jammy.gui.dialogs;

import jammy.catalogModel.Media;
import jammy.gui.MediaListPane;
import jammy.catalogModel.MediaCatalog;
import jammy.mediaBuilder.MediaFactory;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.File;
import java.text.DecimalFormat;

import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import jammy.mediaBuilder.*;

public class EditDialog extends JFrame implements ActionListener{
	
	JPanel mainPanel;
	InputFieldPanel inputFieldPanel;
	JPanel groupPanel;
	JPanel buttonPanel;

	JButton applyButton;
	JButton closeButton;
	ButtonGroup mediaTypeGroup;
	JRadioButton dvdRadio;
	JRadioButton cdRadio;
	JRadioButton hddRadio;

	MediaCatalog catalog;
	MediaListPane mediaListPane;
	int mediaID;

	
	public EditDialog(MediaCatalog catalog,int mediaID, MediaListPane mediaListPane){
		this.catalog = catalog;
		this.mediaID = mediaID;
		this.mediaListPane = mediaListPane;
		
		setTitle("Edit Media");
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
		
		// input Field panel
		inputFieldPanel = new InputFieldPanel();
		inputFieldPanel.editMode();
		
		// buttons
		closeButton = new JButton("Close");
		closeButton.addActionListener(this);
		applyButton = new JButton("Apply");
		applyButton.addActionListener(this);
			
		// group Panel
		dvdRadio = new JRadioButton("DVD");
		dvdRadio.setActionCommand(new Integer(Media.DVD).toString());
		dvdRadio.addActionListener(this);
	
		cdRadio = new JRadioButton("CD");
		cdRadio.setActionCommand(new Integer(Media.CD).toString());
		dvdRadio.addActionListener(this);
		
		hddRadio = new JRadioButton("HDD");
		hddRadio.setActionCommand(new Integer(Media.HDD).toString());
		dvdRadio.addActionListener(this);
	
		mediaTypeGroup = new ButtonGroup();
		mediaTypeGroup.add(dvdRadio);
		mediaTypeGroup.add(cdRadio);
		mediaTypeGroup.add(hddRadio);
		
		
		// close button
		buttonPanel = new JPanel();
		buttonPanel.add(applyButton);
		buttonPanel.add(closeButton);
		
		// add all
		groupPanel = new JPanel();
		groupPanel.add(dvdRadio);
		groupPanel.add(cdRadio);
		groupPanel.add(hddRadio);
		
		mainPanel.add(inputFieldPanel);
		mainPanel.add(groupPanel);
		mainPanel.add(buttonPanel);
	
		add(mainPanel);
		
		Media media = catalog.getMedia(mediaID);
		setFields(media.getMediaName(),media.getDiscName(),media.getType());
		pack();
		setLocation(GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint());
		setVisible(true);
		setResizable(false);
	}
	
	public void setFields(String mediaName, String discName, int mediaType){
		inputFieldPanel.mediaNameTextField.setText(mediaName);
		inputFieldPanel.discNameTextField.setText(discName);
		switch(mediaType){
			case Media.DVD:	dvdRadio.setSelected(true);break;
			case Media.CD:	cdRadio.setSelected(true);break;
			case Media.HDD:	hddRadio.setSelected(true);break;
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		
		//System.out.println("+++ ActionEvent +++");
		
		// handle importbutton click. read subfolders and import the created media to the catalog
		if(e.getSource() == applyButton){
			Media media = catalog.getMedia(mediaID);
			media.setDiscName(inputFieldPanel.discNameTextField.getText());
			media.setMediaName(inputFieldPanel.mediaNameTextField.getText());
			media.setTypeCode(Integer.parseInt(mediaTypeGroup.getSelection().getActionCommand()));
			catalog.unSaved();
			mediaListPane.refreshList();
			setVisible(false);
			dispose();
		}
		
		if(e.getSource() == closeButton){
			setVisible(false);
			dispose();
		}
		
		
	}
}
