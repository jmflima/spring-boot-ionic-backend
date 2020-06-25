package com.esoltecnologia.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.esoltecnologia.cursomc.domain.Cliente;
import com.esoltecnologia.cursomc.dto.ClienteDTO;
import com.esoltecnologia.cursomc.repositories.ClienteRepository;
import com.esoltecnologia.cursomc.resources.exception.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	
	@Autowired
	private HttpServletRequest request; /*para pegar o id do cliente passado na uri*/
	
	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id")); /*pega o id do cliente passado na uri*/
		
		List<FieldMessage> list = new ArrayList<>();
		
// inserindo erros na lista
		
		Cliente aux = repo.findByEmail(objDto.getEmail());
		if (aux != null && !aux.getId().equals(uriId)) {
			/*email já existe para um outro cliente*/
			list.add(new FieldMessage("email", "ALTERAÇÃO não efetuada: Este Email já existe"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMensagem()).addPropertyNode(e.getCampo())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}