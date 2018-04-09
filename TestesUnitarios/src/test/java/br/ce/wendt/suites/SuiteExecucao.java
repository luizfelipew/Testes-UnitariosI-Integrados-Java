package br.ce.wendt.suites;

import br.ce.wendt.servicos.CalculadoraTest;
import br.ce.wendt.servicos.CalculoValorLocacaoTest;
import br.ce.wendt.servicos.LocacaoServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

//@RunWith(Suite.class)
@Suite.SuiteClasses({
        CalculadoraTest.class,
        CalculoValorLocacaoTest.class,
        LocacaoServiceTest.class
})
public class SuiteExecucao {
    //Remova se puder

}
