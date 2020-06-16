package com.esoltecnologia.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.esoltecnologia.cursomc.domain.Categoria;
import com.esoltecnologia.cursomc.domain.Cidade;
import com.esoltecnologia.cursomc.domain.Cliente;
import com.esoltecnologia.cursomc.domain.Endereco;
import com.esoltecnologia.cursomc.domain.Estado;
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
import com.esoltecnologia.cursomc.repositories.PagamentoRepository;
import com.esoltecnologia.cursomc.repositories.PedidoRepository;
import com.esoltecnologia.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

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

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/* salvando dados automaticamente na tabela Categoria */
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		Produto prod1 = new Produto(null, "Computador", 2000.00);
		Produto prod2 = new Produto(null, "Impressora", 800.00);
		Produto prod3 = new Produto(null, "Mouse", 80.00);

		cat1.getProdutos().addAll(Arrays.asList(prod1, prod2, prod3));
		cat2.getProdutos().addAll(Arrays.asList(prod2));

		prod1.getCategorias().addAll(Arrays.asList(cat1));
		prod2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		prod3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRep.saveAll(Arrays.asList(cat1, cat2));
		produtoRep.saveAll(Arrays.asList(prod1, prod2, prod3));

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
		
		
		
		
	}

}
