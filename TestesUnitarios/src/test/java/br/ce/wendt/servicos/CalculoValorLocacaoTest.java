package br.ce.wendt.servicos;

import br.ce.wendt.daos.LocacaoDAO;
import br.ce.wendt.daos.LocacaoDAOFake;
import br.ce.wendt.entidades.Filme;
import br.ce.wendt.entidades.Locacao;
import br.ce.wendt.entidades.Usuario;
import br.ce.wendt.exceptions.FilmesSemEstoqueException;
import br.ce.wendt.exceptions.LocadoraException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static br.ce.wendt.builders.FilmeBuilder.umFilme;
import static br.ce.wendt.builders.UsuarioBuilder.umUsuario;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.runners.Parameterized.*;



//Test Data Driven
@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {

    private LocacaoService service;

    @Parameter
    public List<Filme> filmes;

    @Parameter(value = 1)
    public Double valorLocacao;

    @Parameter(value = 2)
    public String cenario;

    @Before
    public void setup(){
        service = new LocacaoService();
        LocacaoDAO dao = new LocacaoDAOFake();
        service.setLocacaoDAO(dao);
    }


    private static Filme filme1 = umFilme().agora();
    private static Filme filme2 = umFilme().agora();
    private static Filme filme3 = umFilme().agora();
    private static Filme filme4 = umFilme().agora();
    private static Filme filme5 = umFilme().agora();
    private static Filme filme6 = umFilme().agora();
    private static Filme filme7 = umFilme().agora();


    //@Parameters(name = "Teste {index} = {0} - {1}")
    @Parameters(name = "{2}")
    public static Collection<Object[]> getarametros(){
        return Arrays.asList(new Object[][] {
                {Arrays.asList(filme1, filme2), 8.00, "2 Filmes: Sem desconto"},
                {Arrays.asList(filme1, filme2, filme3), 11.00, "3 Filmes: 25%"},
                {Arrays.asList(filme1, filme2, filme3, filme4), 13.00, "4 Filmes: 50%"},
                {Arrays.asList(filme1, filme2, filme3, filme4, filme5), 14.00, "5 Filmes: 75%"},
                {Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 14.00, "6 Filmes: 100%"},
                {Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6, filme7), 18.00, "7 Filmes: Sem desconto"}

        });
    }

    @Test
    public void deveCalcularValorLocacaoConsiderandoDescontos() throws FilmesSemEstoqueException, LocadoraException {
        //cenario
        Usuario usuario = umUsuario().agora();

        //acao
        Locacao resultado = service.alugarFilme(usuario, filmes);

        //verificacao
        //4+4+3+2+1
        assertThat(resultado.getValor(), is(valorLocacao));

    }

    @Test
    public void print(){
        System.out.println(valorLocacao);
    }
}
