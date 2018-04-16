package br.ce.wendt.servicos;

import br.ce.wendt.daos.LocacaoDAO;
import br.ce.wendt.entidades.Filme;
import br.ce.wendt.entidades.Locacao;
import br.ce.wendt.entidades.Usuario;
import br.ce.wendt.exceptions.FilmesSemEstoqueException;
import br.ce.wendt.exceptions.LocadoraException;
import br.ce.wendt.utils.DataUtils;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static br.ce.wendt.builders.FilmeBuilder.umFilme;
import static br.ce.wendt.builders.FilmeBuilder.umFilmeSemEstoque;
import static br.ce.wendt.builders.LocacaoBuilder.umLocacao;
import static br.ce.wendt.builders.UsuarioBuilder.umUsuario;
import static br.ce.wendt.matchers.MatchersProprios.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;


public class LocacaoServiceTest {


    @InjectMocks
	private LocacaoService service;

	@Mock
	private SPCService spc;
	@Mock
	private LocacaoDAO dao;
	@Mock
	private EmailService email;


    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setup(){
	    MockitoAnnotations.initMocks(this);
		/*service = new LocacaoService();
		dao = mock(LocacaoDAO.class);
		service.setLocacaoDAO(dao);
		spc = mock(SPCService.class);
		service.setSPCService(spc);
		email = mock(EmailService.class);
		service.setEmailService(email);*/
    }


	@Test
	public void deveAlugarFilme() throws Exception {
        Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

	    //cenario
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilme().comValor(5.0).agora());


		//acao
		Locacao locacao = service.alugarFilme(usuario, filmes);

		//verificação
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		//error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(locacao.getDataLocacao(), ehHoje());
		//error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
		error.checkThat(locacao.getDataRetorno(), ehHojeComDiferenciaDias(1));


	}

	//Forma elegante de esperar uma exceção no teste
	@Test(expected = FilmesSemEstoqueException.class)
	public void naoDeveAlugarFilmeSemEstoque() throws Exception {

		Usuario usuario = umUsuario().agora();
        List<Filme> filmes = Arrays.asList(umFilmeSemEstoque().agora());

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
        List<Filme> filmes = Arrays.asList(umFilme().agora());
        Usuario usuario = umUsuario().agora();

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
        Usuario usuario = umUsuario().agora();

        exception.expect(LocadoraException.class);
        exception.expectMessage("Filme vazio");

        service.alugarFilme(usuario, null);

        System.out.println("Forma nova");

    }

    @Test
    public void deveDevolverNaSegundaAoAlugarNoSabado() throws FilmesSemEstoqueException, LocadoraException {
	    Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

	    //cenario
	    Usuario usuario = umUsuario().agora();
	    List<Filme> filmes = Arrays.asList(umFilme().agora());

	    //acao
        Locacao retorno = service.alugarFilme(usuario, filmes);

        //verificacao
        //boolean ehSegunda = DataUtils.verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY);
        //assertTrue(ehSegunda);
        assertThat(retorno.getDataRetorno(), caiNumaSegunda());

    }

	/*public static void main(String[] args) {
		new BuilderMaster().gerarCodigoClasse(Locacao.class);
	}*/

	@Test
    public void naoDeveAlugarFilmeParaNegativadoSPC() throws FilmesSemEstoqueException {
	    //cenario
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = Arrays.asList(umFilme().agora());

        when(spc.possuiNegativacao(Mockito.any(Usuario.class))).thenReturn(true);


        //acao
        try {
            service.alugarFilme(usuario, filmes);
            //verificacao
            Assert.fail();
        } catch (LocadoraException e) {
            Assert.assertThat(e.getMessage(), is("Usuario Negativado"));
        }

        verify(spc).possuiNegativacao(usuario);
    }

    @Test
    public void deveEnviarEmailParaLocacoesAtrasadas(){
	    //cenario
        Usuario usuario = umUsuario().agora();
        Usuario usuario2 = umUsuario().comNome("Usuario em dia").agora();
        Usuario usuario3 = umUsuario().comNome("Outro atrasado").agora();

        List <Locacao> locacoes = Arrays.asList(
                umLocacao().comUsuario(usuario).atrasado().agora(),
				umLocacao().comUsuario(usuario2).agora(),
                umLocacao().comUsuario(usuario3).atrasado().agora(),
                umLocacao().comUsuario(usuario3).atrasado().agora());

        when(dao.obterLocacoesPendentes()).thenReturn(locacoes);

        //acao
        service.notificarAtrasos();

        //verificacao
        verify(email, times(3)).notificarAtraso(any(Usuario.class));
        verify(email).notificarAtraso(usuario);
        verify(email, atLeastOnce()).notificarAtraso(usuario3);
        verify(email, never()).notificarAtraso(usuario2);
        verifyNoMoreInteractions(email);
    }
}