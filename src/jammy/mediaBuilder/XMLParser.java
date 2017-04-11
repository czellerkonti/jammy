package jammy.mediaBuilder;

import jammy.catalogModel.*;

import java.io.*;
import java.util.Stack;
import java.util.Iterator;
import org.xml.sax.Attributes;
import org.xml.sax.XMLReader;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.helpers.DefaultHandler;

public class XMLParser extends DefaultHandler {

	MediaCatalog catalog;
	Media media;
	FolderEntryStack folderEntryStack = new FolderEntryStack();
	
	
	public XMLParser(){
	}
	
	public MediaCatalog getCatalog(){
		return catalog;
	}
	
	public XMLParser(File xmlFile) {
		catalog = new MediaCatalog();
		catalog.setFileName(xmlFile.getAbsolutePath());
		 	// create the parser
		 try{
	        XMLReader reader = XMLReaderFactory.createXMLReader();
	        InputStream file = new FileInputStream(xmlFile);
	        InputSource source = new InputSource(file);
	        reader.setContentHandler(this);
	        reader.setFeature("http://xml.org/sax/features/validation", true);
	        reader.parse(source);
		 }catch(Exception e){
			 e.printStackTrace();
		 }
	 }
	
	public void startDocument(){
    }

    public void endDocument(){
        
    }
    
    public void startElement(String uri, String name, 
                             String qName, Attributes attrs)
    {
    	if(qName.equals("Media")){
    		String aDiscName = attrs.getValue("discName");
    		String aMediaName = attrs.getValue("mediaName");
    		int aTypeCode = Integer.parseInt(attrs.getValue("typeCode"));
    		Media m = new Media(Media.nextMediaID++,aTypeCode,aMediaName,aDiscName);
    		this.media = m;
    		catalog.addNewMedia(m);
    	}
    	
    	if(qName.equals("FolderEntry")){
    	
    		String aName = attrs.getValue("name");
    		String aPath = folderEntryStack.getCurrentPath();
    		FolderEntry childFolderEntry = new FolderEntry(aName,aPath);
    		
    		if(folderEntryStack.size() == 0){
    			// we are on the root level
    			media.addChild(childFolderEntry);
    			folderEntryStack.push(childFolderEntry);
    		}else{
    			// we are on one level deeper
    			FolderEntry parentOfThis = (FolderEntry)folderEntryStack.lastElement();
    			parentOfThis.addChild(childFolderEntry);
    			folderEntryStack.push(childFolderEntry);
    		}
    	}
    	if(qName.equals("FileEntry")){
    		//System.out.println("#file#");
    		String aName = attrs.getValue("name");
    		Long aSize = Long.parseLong(attrs.getValue("size"));
    		media.setSize(media.getSize()+aSize);
    		
    		FileEntry childFileEntry = new FileEntry(aName,folderEntryStack.getCurrentPath(),aSize); 
    		
    		if(folderEntryStack.size() == 0){
    			// we are on the root level
    			media.addChild(childFileEntry);
    		}else{
    			FolderEntry f = (FolderEntry)folderEntryStack.lastElement();
    			f.addChild(childFileEntry);
    		}
    	}	
    	
    }

    public void endElement(String uri, String name, 
                           String qName)
    {/*
    	Iterator it = folderEntryStack.iterator();
    	while(it.hasNext()){
    		//System.out.println(it.next());
    	}
    	*/
    	if(qName.equals("FolderEntry")){
    		folderEntryStack.pop();
    	}
    }
    
    public void characters(char[] chars, int start, int length)
    {
      //  for (int i = start; i < start + length; i++)
       //     System.out.print(chars[i]);
    }

   

	
}
