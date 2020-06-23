package com.esoltecnologia.cursomc.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> erros = new ArrayList<>();
	
	public ValidationError(Integer status, String msg, Long timeStamp) {
		super(status, msg, timeStamp);
	}

	public List<FieldMessage> getErrors() {
		return erros;
	}

	public void addError(String campo, String mensagem) {
		erros.add(new FieldMessage(campo, mensagem));
	}

	

}
