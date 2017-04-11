package jammy.catalogModel;

import java.util.LinkedList;
import java.io.File;
import java.util.Iterator;
import jammy.catalogModel.*;

public class Media implements Comparable{

	public static final int DVD = 1;
	public static final int CD = 2;
	public static final int HDD = 3;
	public static int nextMediaID = 1;
	
	private int typeCode;
	public int mediaID;
	private String mediaName = "";
	private String discName = "";
	private long size = 0;
	public LinkedList children = new LinkedList();
	private String sourceDirectory = new String();
	
	public Media(int id, int code){
		this.typeCode = code;
		this.mediaID = id;
		
	}
	
	public Media(int id, int code, String mediaName){
		this(id,code);
		this.mediaName = mediaName;
	}
	
	public Media(int id, int code, String mediaName, String discName){
		this(id,code,mediaName);
		this.discName = discName;
	}
	
	public void addChild(FolderEntry folder){
		children.add(folder);
	}
	
	public void addChild(FileEntry file){
		children.add(file);
	}
	
	public void setSourceDir(String sourceDirectory){
		this.sourceDirectory = sourceDirectory;
	}
	
	public String getSourceDir(){
		return sourceDirectory;
	}
	
	public int getType(){
		return typeCode;
	}
	
	public String getMediaName(){
		return mediaName;
	}
	
	public void setMediaName(String name){
		this.mediaName = name;
	}
	
	public void setDiscName(String name){
		this.discName = name;
	}
	
	public String getDiscName(){
		return discName;
	}
	
	public void setTypeCode(int typeCode){
		this.typeCode = typeCode; 
	}
	
	public long getSize(){
		return size;
	}
	
	public void setSize(long size){
		this.size = size;
	}
	
	public void addSize(long size){
		this.size = this.size + size;
	}	
	
	public void setID(int mediaid){
		this.mediaID = mediaid;
	}
	
	public Object[] getChildren(){
		Object[] children = new Object[this.children.size()];
		Iterator it = this.children.iterator();
		int i = 0;
		while(it.hasNext()){
			children[i++] = it.next();
		}
		return children;
	}
	
	public LinkedList getFileList(){
		LinkedList content = new LinkedList();
		Iterator it = children.iterator();
		
		while(it.hasNext()){
			Object child = it.next();
			if(child instanceof FolderEntry){
				FolderEntry c = (FolderEntry)child;
				content.addAll(c.getContentList());
			}
			if(child instanceof FileEntry){
				FileEntry c = (FileEntry)child;
				content.add(c.getName());
			}
		}
		return content;
	}

	@Override
	public int compareTo(Object o) {
		Media m = (Media)o;
		return this.discName.compareToIgnoreCase(m.discName);
	}
}
