package jammy.gui;


import jammy.catalogModel.Media;
import jammy.catalogModel.MediaCatalog;
import jammy.mediaBuilder.MediaFactory;
import jammy.gui.dialogs.*;
import jammy.gui.dialogs.*;


import javax.swing.*;
import java.awt.*;



import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.FocusListener;
import java.io.File;




public class MainWindow extends JFrame implements ComponentListener{
	
	Container canvas;
	JSplitPane splitPane; 
	MediaTreeTabbedPane rightPanel;
	MediaListPane leftPanel;
	MediaCatalog catalog;
	
	
	
	
	public MainWindow(String header){
		catalog = new MediaCatalog();
		this.addWindowListener(new MainWindowAdapter());
		//this.addFocusListener(this);
		//addComponentListener(this);
		setTitle(header);
		createAndShowGUI();
		setSize(800, 300);
		setLocation(GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint());
		addComponentListener(this);
		//test();
		//new TestWindow();
	}
	
	
	private void test(){

		Media m = MediaFactory.generateMedia(new File("/tmp/bruhaha.xml"));
		//Media m = MediaFactory.generateMedia("/home/konti/a/", Media.HDD, "home of Konti");
		this.catalog.addNewMedia(m);
		//XMLWriter.writeXML(catalog, new File("/tmp/bruhaha.xml"));
		/*
		Media m = MediaFactory.generateMedia("/home/konti/", Media.HDD, "home of Konti");
		leftPanel.mediaCatalog.addNewMedia(m);
		XMLWriter.writeXML(catalog, new File("/tmp/bruhaha.xml"));
		leftPanel.refreshList();
		
		/*
		System.out.println("load ready");
		SimpleSearchEngine se = new SimpleSearchEngine(mc);
		System.out.println("search engine is ready and has: " + se.size + " elements");
		LinkedList hs = se.getIDsHavingFileLike("kaka");
		
		
		Iterator it = hs.iterator();
		while(it.hasNext()){
			Integer i = (Integer)it.next();
			System.out.println(i);
		}*/
		pack();


	}

	
	public void createAndShowGUI(){
		// set up the gui elements
		leftPanel = new MediaListPane();
		leftPanel.setMediaCatalog(catalog);
		rightPanel = new MediaTreeTabbedPane(catalog);
		rightPanel.setPreferredSize(new Dimension(300,100));
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,leftPanel,rightPanel);
		splitPane.setDividerSize(5);
		canvas = getContentPane();
		canvas.add(splitPane);
		
		
		//set menu bar
		setJMenuBar(new Menu());
		
		// show GUI
		setLocationRelativeTo(null);
		//pack();
		setVisible(true);
		
		// disable close operation
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
	}
	
	public void registerNewCatalog(MediaCatalog catalog){
		this.catalog = null;
		this.catalog = catalog;
		leftPanel.setMediaCatalog(catalog);
		rightPanel.setMediaCatalog(catalog);
	}
	
	

	public int showAskWindow(String msg){
		
		return JOptionPane.showConfirmDialog(this, msg);

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
		ComponentEvent ev = new ComponentEvent(leftPanel,ComponentEvent.COMPONENT_RESIZED);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(ev);
		
	}


	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

}

class MainWindowAdapter extends WindowAdapter{
	
	public void windowClosing(WindowEvent w){
		boolean exit = true;
		
		MainWindow mainWindow = (MainWindow)w.getSource();
		if(mainWindow.catalog.isSaveNeeded()){
			switch(mainWindow.showAskWindow("Database not saved! Save it?")){
			case 0: mainWindow.catalog.save();break;
			case 1: ;break;
			case 2: exit = false;
			}
		}
		if(exit)System.exit(0);
	}
}