package jammy.gui;


import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import jammy.catalogModel.MediaCatalog;
import jammy.catalogModel.Media;
import jammy.catalogModel.SimpleSearchEngine;
import javax.swing.JScrollPane;


public class MediaListPane extends JPanel implements KeyListener,ComponentListener, FocusListener{
	
	
	
	// search field plus button
	private JPanel searchPane = new JPanel();
	protected JTextField searchField = new JTextField(10);
	private JButton searchButton = new JButton("search");

	// pane for list of DVDs, CDs and HDDs
	private JPanel listPane = new JPanel();
	private JLabel dvdLabel = new JLabel("DVD");
	private JLabel cdLabel = new JLabel("CD");
	private JLabel hddLabel = new JLabel("HDD");
	private Font mediaTypeFont = new Font("Dialog",Font.BOLD,12);
	private Font mediaElementFont = new Font("Dialog",Font.PLAIN,12);
	private JPanel dvdPane = new JPanel();
	private JPanel cdPane = new JPanel();
	private JPanel hddPane = new JPanel();
	MediaCatalog mediaCatalog;
	private JSplitPane verticalSplitPane;
	private HashSet mediaLabels = new HashSet();
	SimpleSearchEngine searchEngine;
	private JScrollPane jScrollPane;
	

	
	public MediaListPane(){
		prepareAndShowGUI();
		
	}
	
	public MediaListPane(MediaCatalog mc){
		this();
		setMediaCatalog(mc);
		SimpleSearchEngine se = new SimpleSearchEngine(mediaCatalog);
	}
	
	private void prepareAndShowGUI(){
		jScrollPane = new JScrollPane(listPane);
		// prepare search pane
		//searchButton.addActionListener(this);
		searchField.addKeyListener(this);
		searchField.addFocusListener(this);
		
		searchPane.add(searchField);
		//searchPane.add(searchButton);
		//this.setPreferredSize(new Dimension(150,10));
		// prepare media list pane
		dvdPane.setLayout(new BoxLayout(dvdPane,BoxLayout.Y_AXIS));
		cdPane.setLayout(new BoxLayout(cdPane,BoxLayout.Y_AXIS));
		hddPane.setLayout(new BoxLayout(hddPane,BoxLayout.Y_AXIS));
		
		//listPane.setLayout(new BoxLayout(listPane, BoxLayout.Y_AXIS));
		listPane.setLayout(new BoxLayout(listPane,BoxLayout.Y_AXIS));
		dvdLabel.setFont(mediaTypeFont);
		cdLabel.setFont(mediaTypeFont);
		hddLabel.setFont(mediaTypeFont);
		listPane.add(dvdLabel);
		listPane.add(dvdPane);
		listPane.add(cdLabel);
		listPane.add(cdPane);
		listPane.add(hddLabel);
		listPane.add(hddPane);
		
		// add listPane and searchPane to this
		setLayout(new FlowLayout());
		
		jScrollPane.setBorder(null);
		verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,searchPane,jScrollPane);
		verticalSplitPane.setBorder(null);
		verticalSplitPane.setDividerSize(0);
		
		
		this.addComponentListener(this);
		add(verticalSplitPane);
	}
	
	public void setMediaCatalog(MediaCatalog mediaCatalog){
		// set the new catalog
		this.mediaCatalog = mediaCatalog;
		// get the new searchEngine
		searchEngine = new SimpleSearchEngine(mediaCatalog);
		//refresh Medialist
		refreshList();
	}
	
	public void refreshList(){
		// first clear out the labels. yeah.....it's nasty
		dvdPane.removeAll();
		cdPane.removeAll();
		hddPane.removeAll();
		
		Iterator it = mediaCatalog.iterator();
		LinkedList ll = new LinkedList();
		while(it.hasNext()){
			ll.add(it.next());
		}
		Collections.sort(ll);
		it = ll.iterator();
			
		while(it.hasNext()){
			Media m = (Media)it.next();
			//debug(m);
			switch(m.getType()){
			case Media.DVD:
				MediaListLabel dvd = new MediaListLabel(m.mediaID,m.getMediaName());
				dvd.setFont(this.mediaElementFont);
				dvd.setSearchResults(searchEngine.getLastSearchResults());
				dvdPane.add(dvd);
				break;
			case Media.CD:
				MediaListLabel cd = new MediaListLabel(m.mediaID,m.getMediaName());
				cd.setFont(this.mediaElementFont);
				cd.setSearchResults(searchEngine.getLastSearchResults());
				System.out.println(searchEngine.getLastSearchResults());
				cdPane.add(cd);
				break;
			case Media.HDD:
				MediaListLabel hdd = new MediaListLabel(m.mediaID,m.getMediaName());
				hdd.setFont(this.mediaElementFont);
				hdd.setSearchResults(searchEngine.getLastSearchResults());
				hddPane.add(hdd);
				break;
			}
		}
		// get the MainWindow to redraw
		MainWindow mw = (MainWindow)this.getTopLevelAncestor();
		// at initialisation it could be null
		if(mw != null){
			mw.invalidate();
			mw.validate();
		}
		
	}
	
	public void debug(Media m){
		// DEBUG
		System.out.println("");
		System.out.println("### DEBUG MediaListPane ###");
		System.out.println("Added media: ");
		System.out.println("ID: "+m.mediaID);
		System.out.println("Name: "+m.getMediaName());
		System.out.println("TypeCode "+m.getType());
		//
	}

	
	private void setVisibilityforAllLMediaListLabels(boolean visible){
		// set all Media name labelst to hidden
		for(int i=0;i<dvdPane.getComponentCount();i++){
		
			MediaListLabel label = (MediaListLabel)dvdPane.getComponent(i);
			label.setVisible(visible);
		}
		for(int i=0;i<cdPane.getComponentCount();i++){
		
			MediaListLabel label = (MediaListLabel)cdPane.getComponent(i);
			label.setVisible(visible);
		}
		for(int i=0;i<hddPane.getComponentCount();i++){
			
			MediaListLabel label = (MediaListLabel)hddPane.getComponent(i);
			label.setVisible(visible);
		}
	}
	
	private MediaListLabel[] getMediaListLabels(){
		int size = 0;
		for(int i=0;i<dvdPane.getComponentCount();i++){
			size++;
		}
		for(int i=0;i<cdPane.getComponentCount();i++){
			size++;
		}
		for(int i=0;i<hddPane.getComponentCount();i++){
			size++;
		}
		MediaListLabel labels[] = new MediaListLabel[size];
		size = 0;
		
		for(int i=0;i<dvdPane.getComponentCount();i++){
			labels[size++] = (MediaListLabel)dvdPane.getComponent(i); 
		}
		for(int i=0;i<cdPane.getComponentCount();i++){
			labels[size++] = (MediaListLabel)cdPane.getComponent(i);
		}
		for(int i=0;i<hddPane.getComponentCount();i++){
			labels[size++] = (MediaListLabel)hddPane.getComponent(i);
		}
		return labels;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// hide all
	
		if(searchField.getText().equals("")){
			setVisibilityforAllLMediaListLabels(true);
			
		}else{
			setVisibilityforAllLMediaListLabels(false);
		
			
			LinkedList list;
			list = searchEngine.getIDsHavingFileLike(searchField.getText());
			Iterator it = list.iterator();
			while(it.hasNext()){
				MediaListLabel labels[] = getMediaListLabels();
				
				Integer id = (Integer)it.next();
				
				for(int i=0;i<labels.length;i++){
					if(labels[i].getMediaID() == id)labels[i].setVisible(true);
				}
			}
		}
		
		
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentResized(ComponentEvent e) {
		jScrollPane.setPreferredSize(new Dimension(130,this.getHeight()-35));
	}

	@Override
	public void componentShown(ComponentEvent e) {
		System.out.println("shown");
	}

	@Override
	public void focusGained(FocusEvent e) {
		searchField.setSelectionStart(0);
		searchField.setSelectionEnd(searchField.getText().length());
		
	}
	@Override
	public void focusLost(FocusEvent e) {
		searchField.setSelectionStart(0);
		searchField.setSelectionEnd(0);
		
	}
}
