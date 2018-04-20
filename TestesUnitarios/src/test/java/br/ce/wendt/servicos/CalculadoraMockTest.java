package br.ce.wendt.servicos;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import static org.junit.Assert.assertEquals;

public class CalculadoraMockTest {


    @Mock
    private Calculadora calcMock;

    @Spy
    private Calculadora calcSpy;

    @Mock
    private EmailService email;


    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void devoMostrarDiferencaEntreMockSpy(){
       // Mockito.when(calcMock.somar(1,2)).thenReturn(8);
       // Mockito.when(calcMock.somar(1,2)).thenCallRealMethod();
        Mockito.when(calcMock.somar(1,2)).thenReturn(5);
      //  Mockito.when(calcSpy.somar(1,2)).thenReturn(5); //da um problema pq executa oq ta dentro do parentes agora vamos arrumar
        Mockito.doReturn(5).when(calcSpy).somar(1,2);
        //para nao executar o spy
        Mockito.doNothing().when(calcSpy).imprime();

        System.out.println("Mock " + calcMock.somar(1,2));
        System.out.println("Spy " + calcSpy.somar(1,2));


        System.out.println("Mock !!");
        calcMock.imprime();
        System.out.println("Spy !!");  //comportamento padrao eh executar o metodo
        calcSpy.imprime();
    }

    @Test
    public void teste(){
        Calculadora calc = Mockito.mock(Calculadora.class);

        ArgumentCaptor<Integer> argCapt = ArgumentCaptor.forClass(Integer.class);
        Mockito.when(calc.somar(argCapt.capture(), argCapt.capture())).thenReturn(5);

        assertEquals(5, calc.somar(1, 100000));
     //   System.out.println(argCapt.getAllValues());
    }
}
