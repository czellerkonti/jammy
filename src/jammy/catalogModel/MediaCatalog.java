package jammy.catalogModel;

import java.io.File;
import java.util.LinkedList;
import java.util.Comparator;
import java.util.Iterator;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import jammy.catalogModel.Media;
import jammy.mediaBuilder.XMLWriter;

public class MediaCatalog extends LinkedList {

	private boolean isSaveNeeded = false;
	private String filename = "";
	
	public MediaCatalog(){
		super();
	}
	
	public MediaCatalog(Media media){
		super();
		add(media);
		
	}
	
	public void addNewMedia(Media media){
		super.add(media);
		//debug();
		unSaved();
	}
	
	public void removeMedia(Media media){
		Iterator it = iterator();
		while(it.hasNext()){
			Media m = (Media)it.next();
			if(m.mediaID == media.mediaID){
				it.remove();
			}
		}
		
		isSaveNeeded = true;
	}
	
	public boolean isSaveNeeded(){
		return isSaveNeeded;
	}
	
	public void saved(){
		isSaveNeeded = false;
	}
	
	public void unSaved(){
		isSaveNeeded = true;
	}
	
	public void save(){
		if(filename.equals("")){
			saveAs();
		}else{
			XMLWriter.writeXML(this, (new File(filename)));
		}
			saved();
	}
	
	public void saveAs(){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new XMLFileFilter());
		int ret = fileChooser.showSaveDialog(null);
		if(ret == JFileChooser.APPROVE_OPTION){
			filename = fileChooser.getSelectedFile().getAbsolutePath();
			XMLWriter.writeXML(this, (new File(filename))); 
			saved();
		}
	}
	
	public void setFileName(String filename){
		this.filename = filename;
	}
	
	public String getFileName(){
		return filename;
	}
	
	public Media getMedia(int mediaID){
		Media media = null;
		Iterator it = iterator();
		while(it.hasNext()){
			media = (Media)it.next();
			if(media.mediaID == mediaID){
					return media;
			}
		}
		return media;
	}
	
	public void debug(){
		Media media = (Media)this.getLast();
		System.out.println("");
		System.out.println("### DEBUG MediaCatalog ###");
		System.out.println("Added media: ");
		System.out.println("ID: "+media.mediaID);
		System.out.println("Name: "+media.getMediaName());
		System.out.println("TypeCode "+media.getType());
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
