package aed2.trabalho;

import java.io.*;
import java.util.*;

public class MangaManager {
    private Map<String, Long> indicePrimario = new HashMap<>();
    private Map<String, List<String>> indiceSecundario = new HashMap<>();
    private final String dadosPath = "dados/base_mangas.txt";

    public void carregarDados() throws IOException {
        File file = new File(dadosPath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        long posicao = 0;
        String linha;
        while ((linha = br.readLine()) != null) {
            Manga manga = new Manga(linha);
    
            // Índice primário: ISBN → posição do registro
            indicePrimario.put(manga.isbn, posicao);
    
            // Índice secundário: Título → lista de ISBNs
            indiceSecundario.putIfAbsent(manga.titulo, new ArrayList<>());
            indiceSecundario.get(manga.titulo).add(manga.isbn);
    
            posicao += linha.getBytes().length + 1; // conta corretamente o tamanho do caractere
        }
        br.close();
    }
    

    public void salvarIndices() throws IOException {
        BufferedWriter bwPrimario = new BufferedWriter(new FileWriter("indices/indicePrimario.idx"));
        for (Map.Entry<String, Long> entry : indicePrimario.entrySet()) {
            bwPrimario.write(entry.getKey() + "|" + entry.getValue() + "\n");
        }
        bwPrimario.close();

        BufferedWriter bwSecundario = new BufferedWriter(new FileWriter("indices/indiceSecundario.idx"));
        for (Map.Entry<String, List<String>> entry : indiceSecundario.entrySet()) {
            bwSecundario.write(entry.getKey() + "|" + String.join(";", entry.getValue()) + "\n");
        }
        bwSecundario.close();
    }
}
