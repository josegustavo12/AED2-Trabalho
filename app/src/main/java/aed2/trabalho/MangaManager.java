package aed2.trabalho;
import java.io.*;
import java.util.*;

public class MangaManager {
    private final String dadosPath = "mangas.txt";
    private final String primarioPath = "indices/indicePrimario.idx";
    private final String secundarioPath = "indices/indiceSecundario.idx";

    private Map<String, Long> indicePrimario = new HashMap<>();
    private Map<String, List<String>> indiceSecundario = new HashMap<>();

    public void carregarDados() throws IOException {
        indicePrimario.clear();
        indiceSecundario.clear();
        File file = new File(dadosPath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        long posicao = 0;
        String linha;
        while ((linha = br.readLine()) != null) {
            Manga manga = new Manga(linha);
            indicePrimario.put(manga.isbn, posicao);

            indiceSecundario.putIfAbsent(manga.titulo, new ArrayList<>());
            indiceSecundario.get(manga.titulo).add(manga.isbn);

            posicao += linha.getBytes().length + 1;
        }
        br.close();
    }

    public void salvarIndices() throws IOException {
        BufferedWriter bwPrimario = new BufferedWriter(new FileWriter(primarioPath));
        for (Map.Entry<String, Long> entry : indicePrimario.entrySet()) {
            bwPrimario.write(entry.getKey() + "|" + entry.getValue() + "\n");
        }
        bwPrimario.close();

        BufferedWriter bwSecundario = new BufferedWriter(new FileWriter(secundarioPath));
        for (Map.Entry<String, List<String>> entry : indiceSecundario.entrySet()) {
            bwSecundario.write(entry.getKey() + "|" + String.join(";", entry.getValue()) + "\n");
        }
        bwSecundario.close();
    }

    public void inserirNovo(Manga novo) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(dadosPath, "rw");
        long pos = raf.length();
        raf.seek(pos);
        raf.writeBytes(novo.serialize() + "\n");
        raf.close();

        indicePrimario.put(novo.isbn, pos);
        indiceSecundario.putIfAbsent(novo.titulo, new ArrayList<>());
        indiceSecundario.get(novo.titulo).add(novo.isbn);

        salvarIndices();
    }

    public Manga buscarPorISBN(String isbn) throws IOException {
        Long pos = indicePrimario.get(isbn);
        if (pos == null) return null;

        RandomAccessFile raf = new RandomAccessFile(dadosPath, "r");
        raf.seek(pos);
        String linha = raf.readLine();
        raf.close();
        return new Manga(linha);
    }

    public List<Manga> buscarPorTitulo(String titulo) throws IOException {
        List<String> isbns = indiceSecundario.get(titulo);
        if (isbns == null) return List.of();

        List<Manga> mangas = new ArrayList<>();
        for (String isbn : isbns) {
            Manga m = buscarPorISBN(isbn);
            if (m != null) mangas.add(m);
        }
        return mangas;
    }

    public boolean removerPorISBN(String isbn) throws IOException {
        Manga m = buscarPorISBN(isbn);
        if (m == null) return false;

        // Marcar como removido (linha lógica com ***REMOVIDO***)
        File tempFile = new File("dados/temp.txt");
        File original = new File(dadosPath);
        BufferedReader reader = new BufferedReader(new FileReader(original));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String linha;
        while ((linha = reader.readLine()) != null) {
            if (linha.startsWith(isbn)) {
                writer.write("");
            } else {
                writer.write(linha + "\n");
            }
        }

        reader.close();
        writer.close();

        original.delete();
        tempFile.renameTo(original);

        carregarDados(); // recarrega índices com posições atualizadas
        salvarIndices();
        return true;
    }

    public boolean atualizarTitulo(String isbn, String novoTitulo) throws IOException {
        Manga m = buscarPorISBN(isbn);
        if (m == null) return false;

        removerPorISBN(isbn); // remove linha antiga

        // atualiza título
        m.titulo = novoTitulo;
        inserirNovo(m); // insere com novo título

        return true;
    }
}
