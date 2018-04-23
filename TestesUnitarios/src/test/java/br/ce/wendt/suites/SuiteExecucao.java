package br.ce.wendt.suites;

import br.ce.wendt.servicos.CalculadoraTest;
import br.ce.wendt.servicos.CalculoValorLocacaoTest;
import br.ce.wendt.servicos.LocacaoServiceTest;
import org.junit.runners.Suite.SuiteClasses;

//@RunWith(Suite.class)
@SuiteClasses({
        CalculadoraTest.class,
        CalculoValorLocacaoTest.class,
        LocacaoServiceTest.class
})
public class SuiteExecucao {
    //Remova se puder

}
