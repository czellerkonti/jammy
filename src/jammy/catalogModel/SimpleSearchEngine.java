package jammy.catalogModel;

import java.util.LinkedList;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.HashSet;


public class SimpleSearchEngine extends LinkedList {

	public long size = 0;
	private HashSet lastSearch;
	
	
	public SimpleSearchEngine(MediaCatalog catalog){
		
		Iterator it = catalog.iterator();
		
		while(it.hasNext()){
			Media m = (Media)it.next();
			LinkedList content = m.getFileList();
			Iterator i = content.iterator();
			
			while(i.hasNext()){
				Entry e = new Entry(m.mediaID,(String)i.next());
				add(e);
				size++;
			}
		}
	}
	
	public HashSet getLastSearchResults(){
		System.out.println("returning: "+lastSearch);
		return lastSearch;
	}
	
	public LinkedList getIDsHavingFileLike(String expression){
		LinkedList hs = new LinkedList();
		Iterator it = iterator();
		
		lastSearch = new HashSet();
		
		while(it.hasNext()){
			Entry entry;
			entry = (Entry)it.next();

			if(entry.entryName.toLowerCase().contains(expression.toLowerCase())){
				//System.out.println("found string: "+expression+" in "+entry.entryName);
				//System.out.println("adding id to the result: "+entry.mediaID);
				hs.add(new Integer(entry.mediaID));
				lastSearch.add(entry.entryName);
			}
		}
		return hs;
		
	}

}

class Entry{
	
	public int mediaID;
	public String entryName;
	
	public Entry(int mediaID, String entryName){
		this.mediaID = mediaID;
		this.entryName = entryName;
	}
}
