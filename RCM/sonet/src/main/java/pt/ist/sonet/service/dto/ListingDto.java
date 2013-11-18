
package pt.ist.sonet.service.dto;

import java.io.Serializable;

/**
 * DTO para representar a listagem de toda a SoNet.
 * @author ES Grupo 8
 *
 */
public class ListingDto implements Serializable{
	
	/**
	 * 
	 */
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