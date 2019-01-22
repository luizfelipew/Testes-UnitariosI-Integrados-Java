package br.ce.wendt.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileUtilsTest {

    private File diretorio;

    @Before
    public void prepara(){
//        diretorio = new File("/Users/LuizFelipe/Documents/Java_udemy/testes/imagensTeste");
        // para gerar um diretorio temporario
        final String tmpdir = System.getProperty("java.io.tmpdir");
        diretorio = new File(tmpdir, "test-data");
        diretorio.mkdirs();
    }

    @After
    public void limpaSujeira(){
        // deleta arquivos do diretorio
        for (File f : diretorio.listFiles()){
            f.delete();
        }
        diretorio.delete(); // deleta diretorio
    }

    @Test
    public void deveListarArquivosDoDiretorio() throws IOException {

        //cenario
//        File diretorio = new File("/Users/LuizFelipe/Documents/Java_udemy/testes/imagensTeste");
        String tmpdir = System.getProperty("java.io.tmpdir");
        new File(diretorio, "arquivo-1.png").createNewFile();
        new File(diretorio, "arquivo-2.png").createNewFile();

        // acao
        List<File> arquivos = FileUtils.lista(diretorio);

        // validao
        assertEquals("arquivo encontrados", 2, arquivos.size());

    }

    @Test
    public void deveCopiarArquivo() throws IOException {

        //cenario
//        final File diretorio = new File("/Users/LuizFelipe/Documents/Java_udemy/testes/imagensTeste");
        String tmpdir = System.getProperty("java.io.tmpdir");
        final File origem = new File(diretorio, "lftest.png");
        origem.createNewFile();

        // acao
        final File destino = new File(diretorio, "lftest-destino.png");
        FileUtils.copia(origem, destino);

        // validacao
        assertTrue("destino criado", destino.exists());
        assertEquals("mesmo tamanho", origem.length(), destino.length());
    }
}
