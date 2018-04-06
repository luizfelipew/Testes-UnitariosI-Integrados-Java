package br.ce.wendt.servicos;

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
    }


    private static Filme filme1 = new Filme("Filme 1", 2 , 4.0);
    private static Filme filme2 = new Filme("Filme 2", 2 , 4.0);
    private static Filme filme3 = new Filme("Filme 3", 2 , 4.0);
    private static Filme filme4 = new Filme("Filme 4", 2 , 4.0);
    private static Filme filme5 = new Filme("Filme 5", 2 , 4.0);
    private static Filme filme6 = new Filme("Filme 6", 2 , 4.0);
    private static Filme filme7 = new Filme("Filme 6", 2 , 4.0);


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
        Usuario usuario = new Usuario("Usuario 1");

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
