package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Board;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.BoardIdDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;
import pt.ist.sonet.service.dto.BooleanDto;

public class IsBoardFullService extends SonetService{

	private BooleanDto dto;
	private int boardId;
	
	public IsBoardFullService(int id, BooleanDto dto) {
		this.dto = dto;
		boardId = id;
	}	
	
	@Override
	public void dispatch() throws SoNetException, BoardIdDoesNotExistsException {
		
		SoNet network = FenixFramework.getRoot();
		Board board = network.getBoardById(boardId);
		if(board == null)
			throw new BoardIdDoesNotExistsException(boardId);
		if(network.boardIsFull(board))
			dto.setValue(true);
		else
			dto.setValue(false);
	}

}
