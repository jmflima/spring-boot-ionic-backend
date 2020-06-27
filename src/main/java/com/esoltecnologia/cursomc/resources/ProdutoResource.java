package com.esoltecnologia.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.esoltecnologia.cursomc.domain.Produto;
import com.esoltecnologia.cursomc.dto.ProdutoDTO;
import com.esoltecnologia.cursomc.resources.utils.URL;
import com.esoltecnologia.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService servico;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		
		Produto obj = servico.buscar(id); /*impôe ao controlador Rest acesso ao Service passando id*/
		
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> buscaPagina(
			/*parametros passados na url*/
			@RequestParam(value="nome", defaultValue = "") String nome, 
			@RequestParam(value="categorias", defaultValue = "") String categorias, 
			@RequestParam(value="pagina", defaultValue = "0") Integer page, 
			@RequestParam(value="linhasPorPagina", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value="ordem", defaultValue = "nome") String orderBy, 
			@RequestParam(value="direcao", defaultValue = "ASC") String direction) {
		String nomeDecodificado = URL.decodeParametro(nome);
		List<Integer> ids = URL.criaListaDeIds(categorias);
		Page<Produto> lista = servico.pesquisaProd(nomeDecodificado, ids, page, linesPerPage, orderBy, direction); 
		/*convert a lista para listaDTO para mostrar apenas os dados da tabela Categoria */
		Page<ProdutoDTO> listaDTO = lista.map(obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok().body(listaDTO);
	}
	
}
