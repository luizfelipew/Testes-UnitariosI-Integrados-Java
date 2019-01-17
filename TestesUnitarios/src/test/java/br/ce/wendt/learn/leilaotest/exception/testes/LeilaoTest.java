package br.ce.wendt.learn.leilaotest.exception.testes;

import br.ce.wendt.learn.leilaotest.exception.Lance;
import br.ce.wendt.learn.leilaotest.exception.Leilao;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class LeilaoTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void deveDarLance(){

        Lance lance = new Lance("Jao", 299.99);

        Leilao kindle = new Leilao("Kindle Paperwhite");
        kindle.darLance(lance);

        Assert.assertEquals("total de lances", 1, kindle.getLances().size());


    }

    @Test(expected = RuntimeException.class)
    public void naoDeveDarLanceQuandoValorForNegativo(){

        Lance lance = new Lance("jao", -1.99);
        Leilao kindle = new Leilao("Kindle Paperwhite");
        kindle.darLance(lance);

//        try {
//            kindle.darLance(lance);
////            fail("Cade a excecao");
//        } catch (RuntimeException e){
//            assertEquals("Lance com valor negativo", e.getMessage());
//        }
    }


//    @Test
//    public void naoDeveDarLanceQuandoValorForNegativo(){
//
//        thrown.expect(RuntimeException.class);
//        thrown.expectMessage("Lance com valor negativo");
//
//        Lance lance = new Lance("Jao", -1);
//
//        Leilao kindle = new Leilao("Kindle Paperwhite");
//        kindle.darLance(lance);
//
//
//
//    }

}
