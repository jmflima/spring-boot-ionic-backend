package com.esoltecnologia.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esoltecnologia.cursomc.domain.Pedido;
import com.esoltecnologia.cursomc.repositories.PedidoRepository;
import com.esoltecnologia.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired /*instancia a dependêndia automaticamente por INJEÇÃO DE DEPÊNCIA ou INVERSÃO DE CONTROLE*/
	private PedidoRepository repo; /*chama uma operação no objeto dados (Repository) */
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
		}
}

