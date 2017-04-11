package jammy.gui;

import jammy.catalogModel.*;
import jammy.gui.dialogs.EditDialog;
import jammy.gui.dialogs.LoadDialog;

import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MediaListPopupMenu extends JPopupMenu implements ActionListener {
	
	private JMenuItem openMenu = new JMenuItem("Open");
	private JMenuItem importMenu = new JMenuItem("Import new Media");
	private JMenuItem editMenu = new JMenuItem("Edit");
	private JMenuItem removeMenu = new JMenuItem("Remove");
	private MainWindow mw;
	private int mediaID;
	
	public MediaListPopupMenu(MainWindow mw,int mediaID){
		super();
		add(openMenu);
		openMenu.addActionListener(this);
		add(importMenu);
		importMenu.addActionListener(this);
		add(editMenu);
		editMenu.addActionListener(this);
		add(removeMenu);
		removeMenu.addActionListener(this);
		this.mw = mw;
		this.mediaID = mediaID;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == openMenu){
			mw.rightPanel.openNewTab(mediaID,"",null);
		}
		
		if(e.getSource() == importMenu){
			new LoadDialog(mw.catalog,mw.leftPanel);
		}
		if(e.getSource() == editMenu){
			new EditDialog(mw.catalog,mediaID,mw.leftPanel);
		}
		if(e.getSource() == removeMenu){
			Object[] options = {"Yep!","Of course not..."};
			int ret = JOptionPane.showOptionDialog(this, "Are you sure to remove?", "Remove", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
			if(ret == 0){
				Media m = mw.catalog.getMedia(mediaID);
				mw.catalog.removeMedia(m);
				mw.leftPanel.refreshList();
			}
		}
		
	}
}
