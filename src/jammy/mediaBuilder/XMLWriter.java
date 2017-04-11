package jammy.mediaBuilder;

import jammy.catalogModel.MediaCatalog;
import jammy.catalogModel.Media;
import jammy.catalogModel.FolderEntry;
import jammy.catalogModel.FileEntry;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.io.IOException;

import org.w3c.dom.Element;
import org.w3c.dom.Document;


import org.apache.xml.serialize.XMLSerializer;
import org.apache.xml.serialize.OutputFormat; 

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLWriter {

	static Document dom;
	
	
	public static void writeXML(MediaCatalog catalog, File f){
		System.out.println("start");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.newDocument();
		}catch(ParserConfigurationException pce) {
			System.out.println("Error while trying to instantiate DocumentBuilder " + pce);
		}
		createMediaCatalogElement(catalog);
		printToFile(f);
	}
	
	private static Element createMediaCatalogElement(MediaCatalog catalog){
		Element mediaCatalogElement = dom.createElement("MediaCatalog");
		
		dom.appendChild(mediaCatalogElement);

		Iterator it  = catalog.iterator();
		while(it.hasNext()) {
			Media media = (Media)it.next();
			Element mediaElement = createMediaElement(media);
			mediaCatalogElement.appendChild(mediaElement);
		}
		return mediaCatalogElement;
	}
	
	private static Element createMediaElement(Media media){
		Element mediaElement = dom.createElement("Media");
		// set attributes
		mediaElement.setAttribute("typeCode", Integer.toString(media.getType()));
		mediaElement.setAttribute("mediaName", media.getMediaName());
		mediaElement.setAttribute("discName", media.getDiscName());
		mediaElement.setAttribute("size", "0");
		
		Iterator it = media.children.iterator();

		while(it.hasNext()){
				Object childOfFolderEntry = it.next();
				if(childOfFolderEntry instanceof FolderEntry){
					mediaElement.appendChild(createFolderEntryElement((FolderEntry)childOfFolderEntry));
				}else{
					mediaElement.appendChild(createFileEntryElement((FileEntry)childOfFolderEntry));
				}
		}
		
		return mediaElement;
	}
	
	private static  Element createFolderEntryElement(FolderEntry folder){
		Element folderEntryElement = dom.createElement("FolderEntry");
	
		// set attributes
		//folderEntryElement.setAttribute("path", folder.getPath());
		folderEntryElement.setAttribute("name", folder.getName());
		
		Iterator it = folder.getChildren().iterator();
		while(it.hasNext()){
			Object childOfFolderEntry = it.next();
			if(childOfFolderEntry instanceof FolderEntry){
				folderEntryElement.appendChild(createFolderEntryElement((FolderEntry)childOfFolderEntry));	
			}else{
				folderEntryElement.appendChild(createFileEntryElement((FileEntry)childOfFolderEntry));
			}
		}
		return folderEntryElement;
	}
	
	private static  Element createFileEntryElement(FileEntry file){
		Element fileEntryElement = dom.createElement("FileEntry");
		// set attributes
		fileEntryElement.setAttribute("name", file.getName());
		fileEntryElement.setAttribute("size", Long.toString(file.getSize()));
		
		return fileEntryElement;
	}
	
	private static void printToFile(File f){

		try
		{
			//print
			OutputFormat format = new OutputFormat(dom);
			format.setIndenting(true);

			//to generate output to console use this serializer
			//XMLSerializer serializer = new XMLSerializer(System.out, format);


			//to generate a file output use fileoutputstream instead of system.out
			XMLSerializer serializer = new XMLSerializer(
			new FileOutputStream(f), format);

			serializer.serialize(dom);

		} catch(IOException ie) {
		    ie.printStackTrace();
		}
	}

}
