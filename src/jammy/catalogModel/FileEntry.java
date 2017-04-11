package jammy.catalogModel;

import java.io.File;


public class FileEntry{
	
	private long size;
	private String name;
	private String path;
	
	public FileEntry(String name, String path, long size){
		this.name = name;
		this.path = path;
		this.size = size;
		
	}
	
	public FileEntry(File file){
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

}
