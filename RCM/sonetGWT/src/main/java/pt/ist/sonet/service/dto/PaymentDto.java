package pt.ist.sonet.service.dto;

/**
 * DTO para o servico ProcessPaymentService.
 * 
 * @author ES Grupo 8
 *
 */
public class PaymentDto {
	
	private String from;
	private String to;
	private String description;
	private Integer amount;
	
	public PaymentDto(String from, String to, String description, Integer amount) {
		this.from = from;
		this.to = to;
		this.description = description;
		this.amount = amount;
	}

	/**
	 * @return the form
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the amount
	 */
	public Integer getAmount() {
		return amount;
	}
	
	
	

}
