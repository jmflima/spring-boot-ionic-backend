package com.esoltecnologia.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esoltecnologia.cursomc.domain.ItemPedido;
import com.esoltecnologia.cursomc.domain.PagamentoComBoleto;
import com.esoltecnologia.cursomc.domain.Pedido;
import com.esoltecnologia.cursomc.domain.enuns.EstadoPagamento;
import com.esoltecnologia.cursomc.repositories.ItemPedidoRepository;
import com.esoltecnologia.cursomc.repositories.PagamentoRepository;
import com.esoltecnologia.cursomc.repositories.PedidoRepository;
import com.esoltecnologia.cursomc.repositories.ProdutoRepository;
import com.esoltecnologia.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired /*instancia a dependêndia automaticamente por INJEÇÃO DE DEPÊNCIA ou INVERSÃO DE CONTROLE*/
	private PedidoRepository repo; /*chama uma operação no objeto dados (Repository) */
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepo;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepo;
	
	@Autowired
	private ProdutoRepository produtoRepo;
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			/*se o meu pagamento for do tipo pagamento com boleto, gera um boleto*/
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		
		obj = repo.save(obj);
		pagamentoRepo.save(obj.getPagamento());
		for(ItemPedido ip : obj.getItens()) {
			/*percorrendo todos os itens de pedido associados ao objeto getItens i pra cada ítem calcular*/
			ip.setDesconto(0.0);
			ip.setPreco(produtoRepo.findById(ip.getProduto().getId()).get().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepo.saveAll(obj.getItens());
		return obj;

	}
}

