package com.esoltecnologia.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esoltecnologia.cursomc.domain.Categoria;
import com.esoltecnologia.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired /*instancia a dependêndia automaticamente por INJEÇÃO DE DEPÊNCIA ou INVERSÃO DE CONTROLE*/
	private CategoriaRepository repo; /*chama uma operação no objeto dados (Repository) */
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElse(null);
	}
}
