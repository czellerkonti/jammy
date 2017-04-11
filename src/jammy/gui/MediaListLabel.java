package jammy.gui;

import jammy.MediaSizeFormatter;
import jammy.catalogModel.Media;

import java.util.HashSet;

import javax.swing.JLabel;
import javax.swing.JPopupMenu;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MediaListLabel extends JLabel implements MouseListener {
	
	private int mediaID;
	private MediaListPopupMenu popup;
	private MainWindow mw;
	private HashSet searchResults;
	
	public MediaListLabel(int mediaID,String text){
		addMouseListener(this);
		setText(text);
		this.mediaID = mediaID;
	}
	
	public void setSearchResults(HashSet hs){
		searchResults = hs;
	}
	
	public int getMediaID(){
		return mediaID;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1){
			MediaListPane lp = (MediaListPane) getParent().getParent().getParent().getParent().getParent().getParent();
				mw.rightPanel.openNewTab(mediaID,lp.searchField.getText(),this.searchResults);
			
				
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		mw = (MainWindow) getTopLevelAncestor();
		popup = new MediaListPopupMenu(mw,mediaID);
		Media m = mw.catalog.getMedia(mediaID);
		this.setToolTipText("Size: "+MediaSizeFormatter.getText(m.getSize()));
		//this.setToolTipText(Long.toString(m.getSize()));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		maybeShowPopup(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		maybeShowPopup(e);
	}
	
	private void maybeShowPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            popup.show(e.getComponent(),
                       e.getX(), e.getY());
        }
    }
	

}
