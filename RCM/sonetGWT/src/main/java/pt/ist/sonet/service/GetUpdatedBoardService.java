package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Board;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.ApIdDoesNotExistsException;
import pt.ist.sonet.exception.BoardIdDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.dto.BoardDto;

public class GetUpdatedBoardService extends SonetService{
	
	private BoardDto boardDto;
	private int boardId;
	
	public GetUpdatedBoardService(int boardId) {
		this.boardId = boardId;	
	}
	
	@Override
	protected void dispatch() throws SoNetException, ApIdDoesNotExistsException{
		
		SoNet network = FenixFramework.getRoot();
		Board board = network.getBoardById(boardId);
		if(board == null)
			throw new BoardIdDoesNotExistsException(boardId);
		
		boardDto = new BoardDto(board.getId(), board.getMatrixLine1(), board.getMatrixLine2(), board.getMatrixLine3());
	}
	
	public BoardDto getUpdatedBoard(){
		return boardDto;
	}

}
