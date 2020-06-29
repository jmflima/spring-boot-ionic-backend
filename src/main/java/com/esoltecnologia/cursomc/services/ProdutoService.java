package com.esoltecnologia.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.esoltecnologia.cursomc.domain.Categoria;
import com.esoltecnologia.cursomc.domain.Produto;
import com.esoltecnologia.cursomc.repositories.CategoriaRepository;
import com.esoltecnologia.cursomc.repositories.ProdutoRepository;
import com.esoltecnologia.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired /*instancia a dependêndia automaticamente por INJEÇÃO DE DEPÊNCIA ou INVERSÃO DE CONTROLE*/
	private ProdutoRepository produtoRepo; /*chama uma operação no objeto dados (Repository) */
	
	@Autowired
	private CategoriaRepository categoriaRepo;
	
	public Produto buscar(Integer id) {
		Optional<Produto> obj = produtoRepo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}
	
	public Page<Produto> pesquisaProd(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepo.findAllById(ids);
		return produtoRepo.pesquisar(nome, categorias, pageRequest);
		
		//return produtoRepo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}
}

