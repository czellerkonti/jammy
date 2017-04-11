package jammy.mediaBuilder;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

import jammy.catalogModel.FolderEntry;
import jammy.catalogModel.Media;
import jammy.catalogModel.FileEntry;

public class MediaFactory {
	
	
	public static Media generateMedia(File xmlFile){
		
		XMLParser xmlParser = new XMLParser(xmlFile);
		Media m = null;
		return m;
	}
	
	public static Media generateMedia(String folderPath, int mediaTypeCode){
		
		
		File f = new File(folderPath);
		String childNames[] = f.list();
		Media media = new Media(Media.nextMediaID++,mediaTypeCode);
		
		sortFoldersAndFiles(childNames,f.getAbsolutePath());
		//System.out.println("root: "+f.getAbsolutePath());
		for(int i=0; i<childNames.length; i++){
			File fl = new File(folderPath + childNames[i]);
			if(fl.isDirectory()){
				FolderEntry fe = new FolderEntry(fl,0);
				walkThrough(fl, fe, 1);
				media.addChild(fe);
			}else{
				media.addChild(new FileEntry(fl));
			}
		}
		return media;
		
	}
	
	public static Media generateMedia(String folderPath, int mediaTypeCode, String mediaName){
		Media m = MediaFactory.generateMedia(folderPath, mediaTypeCode);
		m.setMediaName(mediaName);
		return m;
	}	
	public static Media generateMedia(String folderPath, int mediaTypeCode, String mediaName, String discName){
		Media m = MediaFactory.generateMedia(folderPath, mediaTypeCode, mediaName);
		m.setDiscName(discName);
		return m;
	}

	private static void walkThrough(File fo, FolderEntry parent, int depthLevel){
		
			
			String childNames[] = fo.list();
			sortFoldersAndFiles(childNames,fo.getAbsolutePath());
			for(int i=0; i<childNames.length; i++){
				File childFile = new File(fo.getAbsolutePath()+"/"+childNames[i]);
				if(childFile.isDirectory()){
					FolderEntry childFolderEntry = new FolderEntry(childFile, depthLevel);
					//System.out.println("folder: "+childFolderEntry.getName());
					parent.addChild(childFolderEntry);
					walkThrough(childFile,childFolderEntry, depthLevel++);
				}else{
					FileEntry childFileEntry = new FileEntry(childFile);
					parent.addChild(childFileEntry);
					//System.out.println("file: "+childFileEntry.getName());
				}
					
			}
		}
	
	private static void sortFoldersAndFiles(String[] childNames,String path){
		// sort folders and files. if it is ready, put folders in the beginning
	
		
		path = path+"/";
		
		String [] folders;
		String [] files;
		
		HashSet tmpFolders = new HashSet();
		HashSet tmpFiles = new HashSet();

		for(int i = 0;i<childNames.length;i++){
			if(new File(path+childNames[i]).isDirectory()){
				tmpFolders.add(childNames[i]);
			}else{
				tmpFiles.add(childNames[i]);
			}
		}
		
		folders = hashSetToStringArray(tmpFolders);
		files = hashSetToStringArray(tmpFiles);
		Arrays.sort(folders);
		Arrays.sort(files);
		
		for(int i = 0;i<folders.length;i++){
			childNames[i] = folders[i];
		}
		for(int i = folders.length;i<childNames.length;i++){
			childNames[i] = files[i-folders.length];
		}		
	}
	
	private static String[] hashSetToStringArray(HashSet hashset){
		String[] ret = new String[hashset.size()];
		Iterator it = hashset.iterator();
		int counter = 0;
		while(it.hasNext()){
			ret[counter++] = (String)it.next();
		}
		return ret;
	}
}
