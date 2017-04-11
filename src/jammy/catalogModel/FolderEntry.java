package jammy.catalogModel;

import java.io.File;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Comparator;

public class FolderEntry {

	private long size;
	private String name;
	private String path;
	private LinkedList children = new LinkedList();

	
	public FolderEntry(String name, String path){
		this.name = name; 
		this.path = path;
		this.size = size;
	}
	
	public FolderEntry(File file,int level){
		name = file.getName();
		path = file.getParent()+"/";
		size = file.length();
	}
	

	public String getName(){
		return name;
	}

	public long getSize(){
		return size;
	}

	public String getPath(){
		return path;
	}
	
	public void addChild(FolderEntry folder){
		children.add(folder);
	}
	
	public void addChild(FileEntry file){
		children.add(file);
	}
	
	public LinkedList getChildren(){
		return children;
	}
	
	
	public LinkedList getContentList(){
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
				content.add(c.getPath()+c.getName());
			}
		}
		return content;
	}
	
	
}

