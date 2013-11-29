package pt.ist.sonet.domain;

public class PI extends PI_Base {
    
    public  PI() {
    	super();
    }
    
	/**
	 * Inicializa os dados da instancia criada.
	 * @param id
	 * @param name
	 * @param location
	 * @param description
	 */
    public void init(int id, String name, String location, String description, AP ap) {
    	setId(id);
    	setName(name);
    	setLocation(location);
    	setDescription(description);
    	setAp(ap);
    }
    
//    public String toString() {
//    	return this.getName() + " in " + this.getLocation() + " || " + this.getDescription();
//    }
}