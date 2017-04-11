package jammy.gui;

import jammy.gui.dialogs.LoadDialog;
import jammy.catalogModel.Media;
import jammy.catalogModel.MediaCatalog;
import jammy.mediaBuilder.XMLParser;
import jammy.mediaBuilder.XMLWriter;
import jammy.gui.dialogs.WarningMessageWindow;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.JDialog;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.File;


public class Menu extends JMenuBar implements ActionListener{
	JMenuBar menuBar;
	JMenu fileMenu, helpMenu;
	JMenuItem about,openDB,saveDB,saveDBas,exit,newDB,importMedia;
	MainWindow mainWindow;
	
	public Menu(){
		//file menu
		fileMenu = new JMenu("File");
		newDB = new JMenuItem("New database");
		newDB.addActionListener(this);
		openDB = new JMenuItem("Open database");
		openDB.addActionListener(this);
		saveDB = new JMenuItem("Save database");
		saveDB.addActionListener(this);
		saveDBas = new JMenuItem("Save database As");
		saveDBas.addActionListener(this);
		importMedia = new JMenuItem("Import Media");
		importMedia.addActionListener(this);
		exit = new JMenuItem("Exit");
		exit.addActionListener(this);
		
		fileMenu.add(newDB);
		fileMenu.add(openDB);
		fileMenu.add(saveDB);
		fileMenu.add(saveDBas);
		fileMenu.add(importMedia);
		fileMenu.add(exit);
		add(fileMenu);
		
		
		
		//help menu
		helpMenu = new JMenu("Help");
		about = new JMenuItem("About");
		about.addActionListener(this);
		helpMenu.add(about);
		
		add(helpMenu);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JPopupMenu popupMenu = (JPopupMenu) newDB.getParent();  
		JMenu invoker = (JMenu) popupMenu.getInvoker();  
		JComponent invokerAsJComponent = (JComponent) invoker;  
		this.mainWindow = (MainWindow)invokerAsJComponent.getTopLevelAncestor();
		if(e.getSource() == importMedia){
			 
			new LoadDialog(mainWindow.catalog,mainWindow.leftPanel);
		}
		if(e.getSource() == exit){
			boolean ex = true;
			if(mainWindow.catalog.isSaveNeeded()){
				switch(mainWindow.showAskWindow("Database not saved! Save it?")){
				case 0: mainWindow.catalog.save();break;
				case 1: ;break;
				case 2: ex = false;
				}
			}
			if(ex)System.exit(0);
		}
		
		if(e.getSource() == newDB){
			boolean doNew = true;
			
			if(mainWindow.catalog.isSaveNeeded()){
				switch(mainWindow.showAskWindow("Database not saved! Save it?")){
				case 0: mainWindow.catalog.save();break;
				case 1: ;break;
				case 2: doNew = false;
				}
			}
			if(doNew)mainWindow.registerNewCatalog(new MediaCatalog());
		}
		
		if(e.getSource() == openDB){
			boolean doOpen = true;
			
			if(mainWindow.catalog.isSaveNeeded()){
				switch(mainWindow.showAskWindow("Database not saved! Save it?")){
				case 0: mainWindow.catalog.save();break;
				case 1: ;break;
				case 2: doOpen = false;
				}
			}
			if(doOpen){
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new XMLFileFilter());
			
				int ret = fileChooser.showOpenDialog(null);
				if(ret == JFileChooser.APPROVE_OPTION){
					XMLParser parser = new XMLParser(new File(fileChooser.getSelectedFile().getAbsolutePath()));
					mainWindow.registerNewCatalog(parser.getCatalog());
					mainWindow.catalog.saved();
					mainWindow.leftPanel.refreshList();
				}
			}
		}
		
		if(e.getSource() == saveDBas){
			mainWindow.catalog.saveAs();
		}
		
		if(e.getSource() == saveDB){
			 mainWindow.catalog.save();
		}
		if(e.getSource() == about){
			 new AboutWindow();
		}
		
		
	}

}

class XMLFileFilter extends FileFilter{

	@Override
	public boolean accept(File file) {
		// TODO Auto-generated method stub
		return file.isDirectory() || file.getName().toLowerCase().endsWith(".xml");
	}
	
	public String getDescription(){
		return ".xml files";
	}

}
