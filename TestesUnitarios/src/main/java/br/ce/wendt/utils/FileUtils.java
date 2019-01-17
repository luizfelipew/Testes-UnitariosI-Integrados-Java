package br.ce.wendt.utils;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class FileUtils {

    /**
     * Lista todos os arquivos de um diretorio
     */

    public static List<File> lista(File diretorio){
        File[] arquivos = diretorio.listFiles();
        return Arrays.asList(arquivos);
    }

    /**
     * Copia arquivo origem para arquivo destino
     */

    public static void copia(File origem, File destino){

        destino.getParentFile().mkdirs(); // cria diretorio pai

        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(origem);
            output = new FileOutputStream(destino);

            byte[] buffer = new byte[1024];
            int length;

            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        } finally {
            try { input.close(); } catch (Exception e) {}
            try { output.close(); } catch (Exception e) {}
        }

    }
}
