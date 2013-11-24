
package pt.ist.sonet.service.dto;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * DTO para representar a listagem usernames de agentes.
 * @author ES Grupo 8
 *
 */
public class ApListDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<ApDto> list;
	
	public ApListDto(){
		list=new ArrayList<ApDto>();
	}
			
	public ArrayList<ApDto> getlisting(){
		return list;
	}
	
	public void addTolisting(ApDto l){
		list.add(l);
	}

}
