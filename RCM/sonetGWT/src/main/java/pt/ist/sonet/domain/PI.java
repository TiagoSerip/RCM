package pt.ist.sonet.domain;

public class PI extends PI_Base {
    
    public  PI() {
    	super();
    }

    public void init(int id, String name, String location, String description, String link, AP ap) {
    	setId(id);
    	setName(name);
    	setLocation(location);
    	setDescription(description);
    	setLink(link);
    	setAp(ap);
    	setPositive(0);
    	setNegative(0);
    }
    
    public void positiveVote(Agent a){
    	int vote = getPositive();
    	setPositive(vote+1);
    }
    
    
    public void negativeVote(Agent a){
    	int vote = getNegative();
    	setNegative(vote+1);
    }
    
//    public String toString() {
//    	return this.getName() + " in " + this.getLocation() + " || " + this.getDescription();
//    }
}