package jammy.gui;

import java.awt.FlowLayout;
import java.util.HashSet;

import jammy.catalogModel.MediaCatalog;
import jammy.catalogModel.Media;


import javax.swing.JTabbedPane;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.util.Iterator;

public class MediaTreeTabbedPane extends JTabbedPane {

	MediaCatalog catalog;
	
	
	public MediaTreeTabbedPane(MediaCatalog catalog){
		this.catalog = catalog;
		setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		this.setSize(300, 200);
	}
	
	protected HitInfoPanel generateHitInfoPanel(String expression,HashSet results){
		HitInfoPanel panel = new HitInfoPanel(expression);
		Iterator it = results.iterator();
		while(it.hasNext()){
			panel.add(new JLabel((String)it.next()));
		}
		return panel;
	}
	
	public void openNewTab(int mediaID,String expression,HashSet results){
		Media m = catalog.getMedia(mediaID);
	
		MediaTree tree = new MediaTree(m);
		//JScrollPane  scrollpane = new JScrollPane(tab,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		

		//addTab(m.getName(),new JPanel().add(new MediaTree(m)));
		JPanel tab = new JPanel();
		if(!expression.equals("")){
			tab.add(generateHitInfoPanel(expression,results));
		}
		tab.add(tree.getScrollPane());
		tab.setLayout(new FlowLayout(FlowLayout.LEFT));
		addTab(m.getMediaName(),tab);
		this.setTabComponentAt(this.getTabCount()-1, new TabComponent(this));
	}
	
	public void setMediaCatalog(MediaCatalog catalog){
		this.catalog = catalog;
		this.removeAll();
	}
	
}
