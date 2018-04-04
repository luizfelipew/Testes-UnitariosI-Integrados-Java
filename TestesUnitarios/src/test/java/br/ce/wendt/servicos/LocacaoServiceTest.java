package br.ce.wendt.servicos;

import br.ce.wendt.entidades.Filme;
import br.ce.wendt.entidades.Locacao;
import br.ce.wendt.entidades.Usuario;
import br.ce.wendt.exceptions.FilmesSemEstoqueException;
import br.ce.wendt.exceptions.LocadoraException;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static br.ce.wendt.utils.DataUtils.isMesmaData;
import static br.ce.wendt.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;


public class LocacaoServiceTest {

	private LocacaoService service;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setup(){
		service = new LocacaoService();
    }


	@Test
	public void testeLocacao() throws Exception {
		//cenario

		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 5.0));


		//acao
		Locacao locacao = service.alugarFilme(usuario, filmes);

		//verificação
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));


	}

	//Forma elegante de esperar uma exceção no teste
	@Test(expected = FilmesSemEstoqueException.class)
	public void testeLocacao_filmeSemEstoque() throws Exception {

		Usuario usuario = new Usuario("Usuario 1");
        List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 0, 5.0));

		//acao
		service.alugarFilme(usuario, filmes);
	}

/*	//Forma robusta para ter um maior controle sobre a exceção esperada
	@Test
	public void testeLocacao_filmeSemEstoque2() {

		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		//acao
		try {
			service.alugarFilme(usuario, filme);
			Assert.fail("Deveria ter lançado uma exceção");
		} catch (Exception e) {
			assertThat(e.getMessage(), is("Filme sem estoque"));
		}
	}

	// Forma nova de tratar uma exceção usando uma nova rule ExpectedException
	@Test
	public void testeLocacao_filmeSemEstoque3() throws Exception {

		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 0, 5.0);



		exception.expect(Exception.class);
		exception.expectMessage("Filme sem estoque");
		//acao
		service.alugarFilme(usuario, filme);

	} */



	//Forma robusta sempre tem Assert.fail()
	@Test
	public void testeLocacao_usuarioVazio() throws FilmesSemEstoqueException {
		//cenario
        List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 5.0));
        Usuario usuario = new Usuario("Usuario 1");

		//acao
		try {
			service.alugarFilme(null, filmes);
			Assert.fail();
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(),is("Usuario vazio"));
		}

        System.out.println("Forma robusta");
	}

	//Forma nova
	@Test
    public void testeLocacao_FilmeVazio() throws FilmesSemEstoqueException, LocadoraException {
        //cenario
        Usuario usuario = new Usuario("Usuario 1");

        exception.expect(LocadoraException.class);
        exception.expectMessage("Filme vazio");

        service.alugarFilme(usuario, null);

        System.out.println("Forma nova");

    }
}