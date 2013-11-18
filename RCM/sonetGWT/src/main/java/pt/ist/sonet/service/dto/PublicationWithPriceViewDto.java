/**
 * 
 */
package pt.ist.sonet.service.dto;

/**
 * @author ES Grupo 8
 *
 */
public class PublicationWithPriceViewDto extends PublicationViewDto {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int price;

	/**
	 * @param id
	 * @param label
	 * @param ownerUsername
	 */
	public PublicationWithPriceViewDto(int id, String type, String ownerUsername, String label, String text, int positive, int negative, int coments, int price) {
		super(id, type, ownerUsername, label, text, positive, negative, coments);
		this.price = price;
	}
	
	public PublicationWithPriceViewDto(){}

	/**
	 * @return the text
	 */
	public int getPrice() {
		return price;
	}

}