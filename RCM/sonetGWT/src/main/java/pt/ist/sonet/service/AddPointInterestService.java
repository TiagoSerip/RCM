package pt.ist.sonet.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.sonet.domain.*;
import pt.ist.sonet.exception.*;
import pt.ist.sonet.service.dto.*;

public class AddPointInterestService extends SonetService {
		
		private PIDto piDto;
		private int apID;
		
		/**
		 * Construtor
		 * 
		 * @param String user - username do agente individual que quer comentar
		 * @param String text - texto do comentario
		 * @param int pubId - identificador da publicacao que se quer comentar
		 */
		public AddPointInterestService(PIDto pi, int ap) {
			this.piDto = pi;
			this.apID = ap;
		}
		
		/**
		 * 
		 * Faz o envio (dispatch) do servico 
		 * 
		 * @throws SoNetException
		 * @throws AgentUsernameDoesNotExistsException
		 * @throws CanNotCommentException
		 */
		@Override
		protected void dispatch() throws SoNetException, ApIdDoesNotExistsException {
			SoNet network = FenixFramework.getRoot();
			PI pi = network.getPIByID(piDto.getId());
			if(pi == null)
				throw new PIIdDoesNotExistsException(id);
			AP ap = network.getApById(apID);
			if(ap == null)
				throw new ApIdDoesNotExistsException(apID);
//			try {
				network.addPI(pi, ap);
//			} catch (YouArentConnected e) {
//				throw new CanNotCommentException(commentator.getUsername(), dto.getApId(), commentator.getName());
//			}
		}
}
	

