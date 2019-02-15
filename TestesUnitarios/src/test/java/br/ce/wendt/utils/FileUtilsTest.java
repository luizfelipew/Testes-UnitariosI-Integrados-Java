package br.ce.wendt.utils;

import br.ce.wendt.Scheduled.DeleteArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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

    @Rule
    public ExpectedException exception = ExpectedException.none();


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


//           final File destination = new File(System.getProperty("java.io.tmpdir"));
//
//        final String command = String.format("touch -t 021404kk11 teste.txt %s ", destination.getAbsolutePath());

//           final File destination = new File(System.getProperty("java.io.tmpdir"));
//
//        final String command = String.format("touch -t 021404kk11 teste.txt %s ", destination.getAbsolutePath());
//
//        try {
//            final Process process = Runtime.getRuntime().exec(command);
//            process.waitFor();
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//
//
//        this.cleanTempFilesService.cleanTmpFiles();
//
//        final File expected = new File(System.getProperty("java.io.tmpdir"));
//        Assert.assertEquals(expected, destination);
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


    protected void removeArchives(final int numDays, final String path) {
        final File files = new File(path);
        final String[] names = files.list();
        for (final String name : names) {
            final File temp = new File(files.getPath(), name);
            final Date archive = new Date(temp.lastModified());
            if (shouldDelete(archive, numDays)) {
                try {
                    temp.delete();
                } catch (final SecurityException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    protected boolean shouldDelete(final Date date, final int numDays) {
        final Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -numDays);

        return date.before(c.getTime());
    }

    @Test
    public void shouldLauchExceptionWhenDoNotPermitted(){
        File destination = new File(System.getProperty("java.io.tmpdir"));
        destination.setExecutable(false);

        exception.expect(SecurityException.class);
        exception.expectMessage("Usuário sem permissão");

        //this.cleanTempFilesService.removeArchives(1, destination.toString());
    }


}
