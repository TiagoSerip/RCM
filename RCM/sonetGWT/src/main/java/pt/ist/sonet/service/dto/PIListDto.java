package pt.ist.sonet.service.dto;

import java.io.Serializable;
import java.util.ArrayList;

public class PIListDto implements Serializable{
	
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	private ArrayList<PIDto> PIlist;
	
	public PIListDto(){
		PIlist = new ArrayList<PIDto>();
	}
			
	public ArrayList<PIDto> getlisting(){
		return PIlist;
	}
	
	public void addTolisting(PIDto pi){
		PIlist.add(pi);
	}
}
