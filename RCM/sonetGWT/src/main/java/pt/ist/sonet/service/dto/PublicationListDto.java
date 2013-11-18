
package pt.ist.sonet.service.dto;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * DTO para representar a listagem usernames de agentes.
 * @author ES Grupo 8
 *
 */
public class PublicationListDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<PublicationDto> list;
	
	public PublicationListDto(){
		list=new ArrayList<PublicationDto>();
	}
		
	public ArrayList<PublicationDto> getlisting(){
		return list;
	}
	
	public void addTolisting(PublicationDto l){
		list.add(l);
	}

}
