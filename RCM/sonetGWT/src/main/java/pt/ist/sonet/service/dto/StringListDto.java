
package pt.ist.sonet.service.dto;

import java.io.Serializable;
import java.util.ArrayList;

public class StringListDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private ArrayList<String> list;
	
	public StringListDto(){
		list=new ArrayList<String>();
	}
		
	public ArrayList<String> getlisting(){
		return list;
	}
	
	public void addTolisting(String l){
		list.add(l);
	}

}
