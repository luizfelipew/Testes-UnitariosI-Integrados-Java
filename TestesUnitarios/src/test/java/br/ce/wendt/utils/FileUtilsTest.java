package br.ce.wendt.utils;

import br.ce.wendt.Scheduled.DeleteArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileUtilsTest {

    private File diretorio;
    private DeleteArchive deleteArchive;

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
    public void deletaArquivoPorUmDia() throws IOException {
        //cenario
//        final File diretorio = new File("/Users/LuizFelipe/Documents/Java_udemy/testes/imagensTeste");
        final String tmpdir = System.getProperty("java.io.tmpdir");
        diretorio = new File(tmpdir, "test-data");
        diretorio.mkdirs();
//
//        final File origem = new File(diretorio, "lftest.png");
//        origem.createNewFile();

        new File(diretorio, "arquivo-1.png").createNewFile();
        new File(diretorio, "arquivo-2.png").createNewFile();

        List<File> arquivos = FileUtils.lista(diretorio);

//        File file = new File("arquivo-1.png");
        File file = new File("test-data");

        System.out.println(new Date(file.lastModified()));

        deletarArquivos(1, diretorio.getPath());

//        DataUtils qtd = new DataUtils();
//        if (arquivos.get(0).lastModified() > qtd.){
//
//        }


       // System.out.println(arquivos.get(0).lastModified());


        // acao
       // this.deleteArchive.deletarArquivos(0,tmpdir);

        // validao
//        assertEquals();


    }

    public static void deletarArquivos(int qtdDays, String path) {
        Date data = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        c.add(Calendar.DATE, -qtdDays);
        data = c.getTime();


        File archives = new File(path);
        String[] names = archives.list();
        for (String name : names) {
            File temp = new File(archives.getPath(), name);
            Date archive = new Date(temp.lastModified());
            if (archive.before(data)) {
                temp.delete();
            }
        }
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
