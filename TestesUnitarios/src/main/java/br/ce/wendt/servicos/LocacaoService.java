package br.ce.wendt.servicos;

import br.ce.wendt.entidades.Filme;
import br.ce.wendt.entidades.Locacao;
import br.ce.wendt.entidades.Usuario;
import br.ce.wendt.exceptions.FilmesSemEstoqueException;
import br.ce.wendt.exceptions.LocadoraException;

import java.util.Date;
import java.util.List;

import static br.ce.wendt.utils.DataUtils.adicionarDias;

public class LocacaoService {



	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmesSemEstoqueException, LocadoraException {

		if(usuario == null){
			throw new LocadoraException("Usuario vazio");
		}

		if(filmes == null || filmes.isEmpty()){
			throw new LocadoraException("Filme vazio");
		}

		for (Filme filme: filmes){
			if (filme.getEstoque() == 0){
				throw new FilmesSemEstoqueException();
			}
		}


		Locacao locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());

		Double valorTotal = 0d;

		for (Filme filme: filmes){
			valorTotal += filme.getPrecoLocacao();
		}

		locacao.setValor(valorTotal);

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}

}