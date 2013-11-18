package pt.ist.sonet.service;

import pt.ist.sonet.exception.SoNetException;
import jvstm.Atomic;

public abstract class SonetService {

	@Atomic
	public void execute() throws SoNetException { 
		dispatch();
	}
	
	protected abstract void dispatch() throws SoNetException;
}
