package pt.ist.largacaixa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonalContents {
	
	/**
	 * Storage of the of the contents according to content id. 
	 */
	private Map<String, Content> personalBox;
	
	public PersonalContents() {
		this.personalBox = new HashMap<String, Content>();
	}
	
	public Content getContent(String id) {
		
		// check content on box
		if (this.personalBox.get(id) == null) {
			// initialize instead of throwing InexistentBoxContentException
			addContent(new Content(id));
		}

		return this.personalBox.get(id);
	}

	public void addContent(Content content) {
		// could check for replacements
		this.personalBox.put(content.getID(), content);
	}

	public List<Content> getAllContents() {
		List<Content> result = new ArrayList<Content>();
		result.addAll(this.personalBox.values());
		return result;
	}

}
