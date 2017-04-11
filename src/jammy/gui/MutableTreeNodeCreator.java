package jammy.gui;

import jammy.catalogModel.Media;

import jammy.catalogModel.FileEntry;
import jammy.catalogModel.FolderEntry;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;

import java.util.Arrays;
import java.util.Iterator;

public class MutableTreeNodeCreator {
	
	static DefaultMutableTreeNode root;
	
	public static DefaultMutableTreeNode generateNode(Media media){
		String rootName;
		if(media.getDiscName().equals("")){
			rootName = media.getMediaName();
		}else{
			rootName = media.getDiscName();
		}
		root = new DefaultMutableTreeNode(rootName);
		Object[] children = media.getChildren();
		
		for(int i=0;i<children.length;i++){
				walkThrough(children[i],root);
		}
		return root;
	}
	
	public static void walkThrough(Object parentEntry, DefaultMutableTreeNode parentNode){
		
		if(parentEntry instanceof FolderEntry){
			FolderEntry folder = (FolderEntry)parentEntry;
			DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(((FolderEntry) parentEntry).getName());
			
			parentNode.add(childNode);
			Iterator it = folder.getChildren().iterator();
			while(it.hasNext()){
				Object childEntry = it.next();
				walkThrough(childEntry,childNode);
			}
			
		}else{
			FileEntry file = (FileEntry)parentEntry; 
			parentNode.add(new DefaultMutableTreeNode(file.getName()));
		}
		
		/*
		if(){
			DefaultMutableTreeNode child;
			
			
			String internalNames[] = fo.list();
			Arrays.sort(internalNames);
			
			for(int i=0; i<internalNames.length; i++){
				child = new DefaultMutableTreeNode(internalNames[i]); 
				parent.add(child);
				System.out.println("dir: "+fo.getName());
				walkThrough(new File(fo.getAbsolutePath() + "/" + internalNames[i]),child);
			}
		}
		else{
			//System.out.println("reached a file: " + fo.getName());
			parent.add(new DefaultMutableTreeNode(fo.getName()));
			System.out.println(fo.getName());
		}
		*/
	}
}
