package com.esoltecnologia.cursomc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.esoltecnologia.cursomc.domain.Categoria;
import com.esoltecnologia.cursomc.domain.Produto;

/*Consultar documentação do spring data
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
 * */
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer>{
	
	@Transactional
	@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
	Page<Produto> pesquisar(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);
	
	//Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria> categorias, Pageable pageRequest);
}
