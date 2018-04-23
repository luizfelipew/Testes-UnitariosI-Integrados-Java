package br.ce.wendt.servicos;

import br.ce.wendt.daos.LocacaoDAO;
import br.ce.wendt.entidades.Filme;
import br.ce.wendt.entidades.Locacao;
import br.ce.wendt.entidades.Usuario;
import br.ce.wendt.exceptions.FilmesSemEstoqueException;
import br.ce.wendt.exceptions.LocadoraException;
import br.ce.wendt.utils.DataUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.Calendar;
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


@RunWith(PowerMockRunner.class)
@PrepareForTest({LocacaoService.class})
public class LocacaoServiceTest_PowerMock {


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
	    service = PowerMockito.spy(service);


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
        //Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

	    //cenario
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilme().comValor(5.0).agora());

        //PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(DataUtils.obterData(28, 4, 2017));
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 28);
        calendar.set(Calendar.MONTH, Calendar.APRIL);
        calendar.set(Calendar.YEAR, 2017);
        PowerMockito.mockStatic(Calendar.class);
        PowerMockito.when(Calendar.getInstance()).thenReturn(calendar);

		//acao
		Locacao locacao = service.alugarFilme(usuario, filmes);

		//verificação
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));

		//error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		//error.checkThat(locacao.getDataLocacao(), ehHoje());

        //error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
		//error.checkThat(locacao.getDataRetorno(), ehHojeComDiferenciaDias(1));

		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), DataUtils.obterData(28,4,2017)), is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterData(29,4,2017)), is(true));


	}


    @Test
    public void deveDevolverNaSegundaAoAlugarNoSabado() throws Exception {
	    //Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

	    //cenario
	    Usuario usuario = umUsuario().agora();
	    List<Filme> filmes = Arrays.asList(umFilme().agora());

        //PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(DataUtils.obterData(29, 4, 2017));
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 29);
        calendar.set(Calendar.MONTH, Calendar.APRIL);
        calendar.set(Calendar.YEAR, 2017);
        PowerMockito.mockStatic(Calendar.class);
        PowerMockito.when(Calendar.getInstance()).thenReturn(calendar);

	    //acao
        Locacao retorno = service.alugarFilme(usuario, filmes);

        //verificacao
        //boolean ehSegunda = DataUtils.verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY);
        //assertTrue(ehSegunda);
        assertThat(retorno.getDataRetorno(), caiNumaSegunda());
        //PowerMockito.verifyNew(Date.class, Mockito.times(2)).withNoArguments();

        PowerMockito.verifyStatic(Mockito.times(2));
        Calendar.getInstance();


    }


    @Test
    public void deveAlugarFilme_SemCalcularValor() throws Exception {
        //cenario
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = Arrays.asList(umFilme().agora());


        //calcularValorLocacao foi passado dessa forma pois o metodo dele na classe service pq é um método privado
        PowerMockito.doReturn(1.0).when(service, "calcularValorLocacao", filmes);

        //acao
        Locacao locacao =  service.alugarFilme(usuario, filmes);

        //verificacao
        Assert.assertThat(locacao.getValor(), is(1.0));
        PowerMockito.verifyPrivate(service).invoke("calcularValorLocacao", filmes);
    }

    @Test
    public void deveCalcularValorLocacao() throws Exception {
	    //cenario
        List<Filme> filmes = Arrays.asList(umFilme().agora());

        //acao
        Double valor = (Double) Whitebox.invokeMethod(service, "calcularValorLocacao", filmes);

        //verificacao
        Assert.assertThat(valor, is(4.0));
    }
}