package com.esoltecnologia.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.esoltecnologia.cursomc.domain.Cliente;
import com.esoltecnologia.cursomc.dto.ClienteDTO;
import com.esoltecnologia.cursomc.repositories.ClienteRepository;
import com.esoltecnologia.cursomc.services.exceptions.DataIntegrityException;
import com.esoltecnologia.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired /*
				 * instancia a dependêndia automaticamente por INJEÇÃO DE DEPÊNCIA ou INVERSÃO
				 * DE CONTROLE
				 */
	private ClienteRepository repo; /* chama uma operação no objeto dados (Repository) */

	public Cliente buscar(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	/* Inserindo uma categoria */
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public Cliente update(Cliente obj) {
		Cliente novoObj = buscar(obj.getId());
		atualizarDados(novoObj, obj);	/*adiciona as alterações de nome e email ao restante do objeto */
		return repo.save(novoObj);
	}

	public void delete(Integer id) {
		buscar(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um Cliente com Pedidos");

		}
	}

	public List<Cliente> buscarTodos() {
		return repo.findAll();
	}

	public Page<Cliente> buscaPagina(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	/* A partir de um DTO vou construir um objeto Cliente */
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
	
	private void atualizarDados(Cliente novoObj, Cliente obj) {
		novoObj.setNome(obj.getNome());
		novoObj.setEmail(obj.getEmail());
	}

}
