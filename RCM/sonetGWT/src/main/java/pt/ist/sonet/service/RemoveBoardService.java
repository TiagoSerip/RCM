package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.Board;
import pt.ist.sonet.domain.SoNet;
import pt.ist.sonet.exception.BoardIdDoesNotExistsException;
import pt.ist.sonet.exception.SoNetException;

public class RemoveBoardService extends SonetService {

	private int boardId;
	
	public RemoveBoardService(int boardID) {
		this.boardId = boardID;
	}
	
	@Override
	protected void dispatch() throws SoNetException{
		
		SoNet sonet = FenixFramework.getRoot();		
		Board board = sonet.getBoardById(boardId);
		if(board == null)
			throw new BoardIdDoesNotExistsException(boardId);
//		board.setGuest(null);
//		board.setHost(null);
		board.removeGuest();
		board.removeHost();
		sonet.removeBoard(board);
	}

}
