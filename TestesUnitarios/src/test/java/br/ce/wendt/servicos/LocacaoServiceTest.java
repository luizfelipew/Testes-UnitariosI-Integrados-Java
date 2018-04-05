package br.ce.wendt.servicos;

import br.ce.wendt.entidades.Filme;
import br.ce.wendt.entidades.Locacao;
import br.ce.wendt.entidades.Usuario;
import br.ce.wendt.exceptions.FilmesSemEstoqueException;
import br.ce.wendt.exceptions.LocadoraException;
import br.ce.wendt.utils.DataUtils;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static br.ce.wendt.utils.DataUtils.isMesmaData;
import static br.ce.wendt.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


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
	public void deveAlugarFilme() throws Exception {
        Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

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
	public void naoDeveAlugarFilmeSemEstoque() throws Exception {

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
	public void nãoDeveAlugarFilmeSemUsuario() throws FilmesSemEstoqueException {
		//cenario
        List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 5.0));
        Usuario usuario = new Usuario("Usuario 1");

		//acao
		try {
			service.alugarFilme(null, filmes);
			Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(),is("Usuario vazio"));
		}

        System.out.println("Forma robusta");
	}

	//Forma nova
	@Test
    public void naoDeveAlugarFilmeSemFilme() throws FilmesSemEstoqueException, LocadoraException {
        //cenario
        Usuario usuario = new Usuario("Usuario 1");

        exception.expect(LocadoraException.class);
        exception.expectMessage("Filme vazio");

        service.alugarFilme(usuario, null);

        System.out.println("Forma nova");

    }

    @Test
	public void devePagar75PctNoFilme3() throws FilmesSemEstoqueException, LocadoraException {
		//cenario
        Usuario usuario = new Usuario("Usuario 1");
        List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2 , 4.0),
                                           new Filme("Filme 2", 2 , 4.0),
                                            new Filme("Filme 3", 2 , 4.0));
        //acao
        Locacao resultado = service.alugarFilme(usuario, filmes);

        //verificacao
        assertThat(resultado.getValor(), is(11.00));
	}

    @Test
    public void devePagar50PctNoFilme4() throws FilmesSemEstoqueException, LocadoraException {
        //cenario
        Usuario usuario = new Usuario("Usuario 1");
        List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2 , 4.0),
                new Filme("Filme 2", 2 , 4.0),
                new Filme("Filme 3", 2 , 4.0),
                new Filme("Filme 4", 2 , 4.0));
        //acao
        Locacao resultado = service.alugarFilme(usuario, filmes);

        //verificacao
        assertThat(resultado.getValor(), is(13.00));
    }

    @Test
    public void devePagar25PctNoFilme5() throws FilmesSemEstoqueException, LocadoraException {
        //cenario
        Usuario usuario = new Usuario("Usuario 1");
        List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2 , 4.0),
                new Filme("Filme 2", 2 , 4.0),
                new Filme("Filme 3", 2 , 4.0),
                new Filme("Filme 4", 2 , 4.0),
                new Filme("Filme 5", 2 , 4.0));
        //acao
        Locacao resultado = service.alugarFilme(usuario, filmes);

        //verificacao
        //4+4+3+2+1
        assertThat(resultado.getValor(), is(14.00));
    }

    @Test
    public void devePagar0PctNoFilme6() throws FilmesSemEstoqueException, LocadoraException {
        //cenario
        Usuario usuario = new Usuario("Usuario 1");
        List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2 , 4.0),
                new Filme("Filme 2", 2 , 4.0),
                new Filme("Filme 3", 2 , 4.0),
                new Filme("Filme 4", 2 , 4.0),
                new Filme("Filme 5", 2 , 4.0),
                new Filme("Filme 6", 2 , 4.0));
        //acao
        Locacao resultado = service.alugarFilme(usuario, filmes);

        //verificacao
        assertThat(resultado.getValor(), is(14.00));
    }

    @Test
    public void deveDevolverNaSegundaAoAlugarNoSabado() throws FilmesSemEstoqueException, LocadoraException {
	    Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

	    //cenario
	    Usuario usuario = new Usuario("Usuario 1");
	    List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 1, 5.0));

	    //acao
        Locacao retorno = service.alugarFilme(usuario, filmes);

        //verificacao
        boolean ehSegunda = DataUtils.verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY);
        assertTrue(ehSegunda);

    }
}