/**
 * 
 */
package pt.ist.sonet.service.dto;

/**
 * @author ES Grupo 8
 *
 */
public class PublicationViewDto extends PublicationDto {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String text;

	/**
	 * @param id
	 * @param label
	 * @param ownerUsername
	 */
	public PublicationViewDto(int id, String type, String ownerUsername, String label, String text, int positive, int negative, int coments) {
		super(id, ownerUsername, label, positive, negative, coments);
		this.setType(type);
		this.text=text;
	}
	
	public PublicationViewDto(){}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

}
