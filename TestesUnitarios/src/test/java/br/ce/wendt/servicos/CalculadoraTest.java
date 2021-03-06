package br.ce.wendt.servicos;

import br.ce.wendt.exceptions.NaoPodeDividirPorZeroException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculadoraTest {

    private Calculadora calc;

    @Before
    public void setup(){
        calc = new Calculadora();
    }

    @Test
    public void deveSomarDoisValores(){
        //cenario
        int a = 5;
        int b = 3;

        //acao
        int resultado = calc.somar(a, b);


        //verificacao
        assertEquals(8, resultado);
    }

    @Test
    public void deveSubtrairDoisValores(){
        //cenario
        int a = 8;
        int b = 5;

        //acao
        int resultado = calc.subtrair(a, b);

        //verificacao
        assertEquals(3, resultado);

    }

    @Test
    public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException {
        //cenario
        int a = 6;
        int b = 3;

        //acao
        int resultado = calc.divide(a, b);

        //verificacao
        assertEquals(2, resultado);

    }

    @Test(expected = NaoPodeDividirPorZeroException.class)
    public void deveLancarExcecaoAoDividirPorZero() throws NaoPodeDividirPorZeroException {
        int a = 10;
        int b = 0;

        calc.divide(a, b);

    }

    @Test
    public void deveDividir(){
        String a = "6";
        String b = "3";

        int resutado = calc.divide(a, b);

        assertEquals(2, resutado);
    }

}
