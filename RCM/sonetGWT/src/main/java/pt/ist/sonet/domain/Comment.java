package pt.ist.sonet.domain;

/**
 * Classe Comment que herda de Comment_Base
 */
public class Comment extends Comment_Base {

	/**
	 * Construtor
	 * 
	 * @param Agent comentador - agente que faz o comentario
	 * @param Publication pub - publicacao onde e feito o comentario
	 * @param int id - identificador do comentario
	 * @param String text - texto do comentario
	 */
	public  Comment(Agent comentador, AP ap, String text) {
		super();
		setAgent(comentador);
		setAp(ap);
		setText(text);
	}

	/**
	 * 
	 * Metodo que apresenta o comentario
	 * 
	 * @return uma string com o id do comentario, o nome do agente que fez o comentario e o texto do comentario
	 */
	public String toString() {
		return this.getAgent().getName() + ": " + this.getText();
	}

}
