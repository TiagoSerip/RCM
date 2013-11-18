/**
 * 
 */
package pt.ist.sonet.service.dto;

/**
 * @author ES Grupo 8
 *
 */
public class AgentDto {
	
	private String username;
	private String name;
	private String email;
	private String pass;
	private String city;
	private String nation;
	private String permission = "amigo";
	private String type;
	
	/**
	 * @param username
	 * @param name
	 * @param email
	 * @param pass
	 * @param city
	 * @param nation
	 */
	public AgentDto(String type, String username, String name, String email, String pass,
			String city, String nation) {

		this.username = username;
		this.name = name;
		this.email = email;
		this.pass = pass;
		this.city = city;
		this.nation = nation;
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the pass
	 */
	public String getPass() {
		return pass;
	}

	/**
	 * @param pass the pass to set
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the nation
	 */
	public String getNation() {
		return nation;
	}

	/**
	 * @param nation the nation to set
	 */
	public void setNation(String nation) {
		this.nation = nation;
	}

	/**
	 * @return the permission
	 */
	public String getPermission() {
		return permission;
	}

	/**
	 * @param permission the permission to set
	 */
	public void setPermission(String permission) {
		this.permission = permission;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	
	
}
