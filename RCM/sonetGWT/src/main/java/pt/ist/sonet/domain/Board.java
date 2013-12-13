package pt.ist.sonet.domain;

public class Board extends Board_Base {
    
	public String[][] matrix;
	public String[] matrixLine1;
	public String[] matrixLine2;
	public String[] matrixLine3;
	
    public  Board() {}
    
	public void init(int id, Agent host, Agent guest) {
		setId(id);
		setHost(host);
		setGuest(guest);
		setPos00(null);
		setPos01(null);
		setPos02(null);
		setPos10(null);
		setPos11(null);
		setPos12(null);
		setPos20(null);
		setPos21(null);
		setPos22(null);
		setTurn(host.getUsername());
	}
	
	public void setMatrixPosition(int[] jogada, String player) {
		if(jogada[0] == 0 && jogada[1] == 0)
			setPos00(player);
		if(jogada[0] == 0 && jogada[1] == 1)
			setPos01(player);
		if(jogada[0] == 0 && jogada[1] == 2)
			setPos02(player);
		if(jogada[0] == 1 && jogada[1] == 0)
			setPos10(player);
		if(jogada[0] == 1 && jogada[1] == 1)
			setPos11(player);
		if(jogada[0] == 1 && jogada[1] == 2)
			setPos12(player);
		if(jogada[0] == 2 && jogada[1] == 0)
			setPos20(player);
		if(jogada[0] == 2 && jogada[1] == 1)
			setPos21(player);
		if(jogada[0] == 2 && jogada[1] == 2)
			setPos22(player);			
	}
	
	public String[][] getMatrix() {
		this.matrix[0][0] = getPos00();
		this.matrix[0][1] = getPos01();
		this.matrix[0][2] = getPos02();
		this.matrix[1][0] = getPos10();
		this.matrix[1][1] = getPos11();
		this.matrix[1][2] = getPos12();
		this.matrix[2][0] = getPos20();
		this.matrix[2][1] = getPos21();
		this.matrix[2][2] = getPos22();
		return this.matrix;
	}
	
	public String[] getMatrixLine1() {
		this.matrixLine1[0] = getPos00();
		this.matrixLine1[1] = getPos01();
		this.matrixLine1[2] = getPos02();
		return this.matrixLine1;
	}
	
	public String[] getMatrixLine2() {
		this.matrixLine2[0] = getPos10();
		this.matrixLine2[1] = getPos11();
		this.matrixLine2[2] = getPos12();
		return this.matrixLine2;
	}
	
	public String[] getMatrixLine3() {
		this.matrixLine3[0] = getPos20();
		this.matrixLine3[1] = getPos21();
		this.matrixLine3[2] = getPos22();
		return this.matrixLine3;
	}
    
}
