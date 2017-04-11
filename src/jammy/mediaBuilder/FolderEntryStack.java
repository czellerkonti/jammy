package jammy.mediaBuilder;

import jammy.catalogModel.FolderEntry;

import java.io.File;
import java.util.Stack;

public class FolderEntryStack extends Stack{

	private String currentPath = new String(File.separator);
	
	
	public FolderEntryStack(){
		super();
	}
	
	public String getCurrentPath(){
		return currentPath;
	}
	
	public Object push(FolderEntry item){
		super.push(item);
		currentPath = currentPath.concat(File.separator+item.getName());
		return item;
	}
	
	public FolderEntry pop(){
		FolderEntry fe = (FolderEntry)super.pop();
		int endIndex = currentPath.lastIndexOf(File.separator);
		currentPath = currentPath.substring(0, endIndex);
		return fe;
		
	}
}
