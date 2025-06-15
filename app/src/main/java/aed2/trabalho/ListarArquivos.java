package aed2.trabalho;

import java.io.File;

public class ListarArquivos {
    public static void listarDiretorioAtual() {
        // Obtém o diretório onde o programa está sendo executado
        File diretorioAtual = new File(System.getProperty("user.dir"));

        System.out.println("Listando arquivos no diretório: " + diretorioAtual.getAbsolutePath());

        File[] arquivos = diretorioAtual.listFiles();
        if (arquivos != null) {
            for (File arquivo : arquivos) {
                if (arquivo.isDirectory()) {
                    System.out.println("[DIR]  " + arquivo.getName());
                } else {
                    System.out.println("       " + arquivo.getName());
                }
            }
        } else {
            System.out.println("Nenhum arquivo encontrado ou diretório inacessível.");
        }
    }
}
