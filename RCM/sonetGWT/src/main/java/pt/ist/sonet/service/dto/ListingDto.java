
package pt.ist.sonet.service.dto;

import java.io.Serializable;

public class ListingDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private String list;
	
	public ListingDto(){
		list="";
	}
		
	public String getlisting(){
		return list;
	}
	
	public void addTolisting(String l){
		list += l;
	}

}