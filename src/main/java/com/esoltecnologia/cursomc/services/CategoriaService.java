package com.esoltecnologia.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.esoltecnologia.cursomc.domain.Categoria;
import com.esoltecnologia.cursomc.repositories.CategoriaRepository;
import com.esoltecnologia.cursomc.services.exceptions.DataIntegrityException;
import com.esoltecnologia.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired /*instancia a dependêndia automaticamente por INJEÇÃO DE DEPÊNCIA ou INVERSÃO DE CONTROLE*/
	private CategoriaRepository repo; /*chama uma operação no objeto dados (Repository) */
	
	/*Pesquisando uma categoria*/
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
		}
	
	/*Inserindo uma categoria*/
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		buscar(obj.getId());
		return repo.save(obj);
	}

	public void delete(Integer id) {
		buscar(id);
		try {
			repo.deleteById(id);
		}catch(DataIntegrityViolationException e ){
			throw new DataIntegrityException("Não é possível excluir uma Categoria com Produtos");
			
		}
	}
	
	public List<Categoria> buscarTodos() {
		return repo.findAll();
	}
	
	public Page<Categoria> buscaPagina(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

}

