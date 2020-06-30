package com.esoltecnologia.cursomc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esoltecnologia.cursomc.domain.Categoria;
import com.esoltecnologia.cursomc.domain.Cidade;
import com.esoltecnologia.cursomc.domain.Cliente;
import com.esoltecnologia.cursomc.domain.Endereco;
import com.esoltecnologia.cursomc.domain.Estado;
import com.esoltecnologia.cursomc.domain.ItemPedido;
import com.esoltecnologia.cursomc.domain.Pagamento;
import com.esoltecnologia.cursomc.domain.PagamentoComBoleto;
import com.esoltecnologia.cursomc.domain.PagamentoComCartao;
import com.esoltecnologia.cursomc.domain.Pedido;
import com.esoltecnologia.cursomc.domain.Produto;
import com.esoltecnologia.cursomc.domain.enuns.EstadoPagamento;
import com.esoltecnologia.cursomc.domain.enuns.TipoCliente;
import com.esoltecnologia.cursomc.repositories.CategoriaRepository;
import com.esoltecnologia.cursomc.repositories.CidadeRepository;
import com.esoltecnologia.cursomc.repositories.ClienteRepository;
import com.esoltecnologia.cursomc.repositories.EnderecoRepository;
import com.esoltecnologia.cursomc.repositories.EstadoRepository;
import com.esoltecnologia.cursomc.repositories.ItemPedidoRepository;
import com.esoltecnologia.cursomc.repositories.PagamentoRepository;
import com.esoltecnologia.cursomc.repositories.PedidoRepository;
import com.esoltecnologia.cursomc.repositories.ProdutoRepository;

@Service
public class DBService {

	@Autowired
	private CategoriaRepository categoriaRep;
	@Autowired
	private ProdutoRepository produtoRep;
	@Autowired
	private EstadoRepository estadoRep;
	@Autowired
	private CidadeRepository cidadeRep;
	@Autowired
	private ClienteRepository clienteRep;
	@Autowired
	private EnderecoRepository enderecoRep;
	@Autowired
	private PedidoRepository pedidoRep;
	@Autowired
	private PagamentoRepository pagamentoRep;

	@Autowired
	private ItemPedidoRepository itempedidoRep;
	
	public void instantiateTestDatabase() throws ParseException {
		/* salvando dados automaticamente na tabela Categoria */
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama, mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");

		Produto prod1 = new Produto(null, "Computador", 2000.00);
		Produto prod2 = new Produto(null, "Impressora", 800.00);
		Produto prod3 = new Produto(null, "Mouse", 80.00);
		Produto prod4 = new Produto(null, "Mesa de escritório", 2000.00);
		Produto prod5 = new Produto(null, "Toalha", 800.00);
		Produto prod6 = new Produto(null, "Colcha", 80.00);
		Produto prod7 = new Produto(null, "Tv true color", 2000.00);
		Produto prod8 = new Produto(null, "Roçadeira", 800.00);
		Produto prod9 = new Produto(null, "Abajour", 80.00);
		Produto prod10 = new Produto(null, "Pendente", 2000.00);
		Produto prod11 = new Produto(null, "Champoo", 800.00);

		cat1.getProdutos().addAll(Arrays.asList(prod1, prod2, prod3));
		cat2.getProdutos().addAll(Arrays.asList(prod2, prod4));
		cat3.getProdutos().addAll(Arrays.asList(prod5, prod6));
		cat4.getProdutos().addAll(Arrays.asList(prod1, prod2, prod3, prod7));
		cat5.getProdutos().addAll(Arrays.asList(prod8));
		cat6.getProdutos().addAll(Arrays.asList(prod9, prod10));
		cat7.getProdutos().addAll(Arrays.asList(prod11));

		prod1.getCategorias().addAll(Arrays.asList(cat1, cat4));
		prod2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		prod3.getCategorias().addAll(Arrays.asList(cat1, cat4));
		prod4.getCategorias().addAll(Arrays.asList(cat2));
		prod5.getCategorias().addAll(Arrays.asList(cat3));
		prod6.getCategorias().addAll(Arrays.asList(cat3));
		prod7.getCategorias().addAll(Arrays.asList(cat4));
		prod8.getCategorias().addAll(Arrays.asList(cat5));
		prod9.getCategorias().addAll(Arrays.asList(cat6));
		prod10.getCategorias().addAll(Arrays.asList(cat6));
		prod11.getCategorias().addAll(Arrays.asList(cat7));

		categoriaRep.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		produtoRep.saveAll(Arrays.asList(prod1, prod2, prod3, prod4, prod5, prod6, prod7, prod8, prod9, prod10, prod11));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		estadoRep.saveAll(Arrays.asList(est1, est2));
		cidadeRep.saveAll(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "sala 800", "Centro", "38777012", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRep.saveAll(Arrays.asList(cli1));
		enderecoRep.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1); /*o pagamento do pedido1 é o pagto1*/
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2); /*o pagamento do pedido2 é o pagto2*/
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRep.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRep.saveAll(Arrays.asList(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, prod1, 0.00, 1, 2000.00); 
		ItemPedido ip2 = new ItemPedido(ped1, prod3, 0.00, 2, 80.00); 
		ItemPedido ip3 = new ItemPedido(ped2, prod2, 100.00, 1, 800.00); 
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		prod1.getItens().addAll(Arrays.asList(ip1));
		prod2.getItens().addAll(Arrays.asList(ip3));
		prod3.getItens().addAll(Arrays.asList(ip2));
		
		itempedidoRep.saveAll(Arrays.asList(ip1, ip2, ip3));
		
	}

}
