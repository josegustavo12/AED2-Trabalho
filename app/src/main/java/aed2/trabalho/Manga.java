package aed2.trabalho;

import java.util.*;

public class Manga {
    public String isbn;
    public String titulo;
    public List<String> autores;
    public int anoInicio;
    public Integer anoFim; // pode ser null
    public List<String> generos;
    public String revista;
    public String editora;
    public int anoEdicao;
    public int qtdVolumes;
    public int qtdVolumesAdquiridos;
    public List<Integer> volumesAdquiridos;

    public Manga(String linha) {
        String[] partes = linha.split(";");
        this.isbn = partes[0].trim();
        this.titulo = partes[1].trim();
        this.autores = Arrays.stream(partes[2].split(",")).map(String::trim).toList();
        this.anoInicio = Integer.parseInt(partes[3].trim());
        this.anoFim = partes[4].trim().equals("-") ? null : Integer.parseInt(partes[4].trim());
        this.generos = Arrays.stream(partes[5].split(",")).map(String::trim).toList();
        this.revista = partes[6].trim();
        this.editora = partes[7].trim();
        this.anoEdicao = Integer.parseInt(partes[8].trim());
        this.qtdVolumes = Integer.parseInt(partes[9].trim());
        this.qtdVolumesAdquiridos = Integer.parseInt(partes[10].trim());
        this.volumesAdquiridos = new ArrayList<>();

        String volumesStr = partes[11].replaceAll("\\[|\\]", "").trim();
        if (!volumesStr.isEmpty()) {
            for (String v : volumesStr.split(",")) {
                volumesAdquiridos.add(Integer.parseInt(v.trim()));
            }
        }
    }

    public String serialize() {
        return String.join(";",
            isbn,
            titulo,
            String.join(",", autores),
            String.valueOf(anoInicio),
            anoFim != null ? String.valueOf(anoFim) : "-",
            String.join(",", generos),
            revista,
            editora,
            String.valueOf(anoEdicao),
            String.valueOf(qtdVolumes),
            String.valueOf(qtdVolumesAdquiridos),
            "[" + volumesAdquiridos.stream().map(String::valueOf).reduce((a, b) -> a + ", " + b).orElse("") + "]"
        );
    }

    @Override
    public String toString() {
        return "ISBN: " + isbn +
                "\nTítulo: " + titulo +
                "\nAutores: " + autores +
                "\nAno de Início: " + anoInicio +
                "\nAno de Fim: " + (anoFim == null ? "-" : anoFim) +
                "\nGêneros: " + generos +
                "\nRevista: " + revista +
                "\nEditora: " + editora +
                "\nAno da Edição: " + anoEdicao +
                "\nVolumes totais: " + qtdVolumes +
                "\nVolumes adquiridos: " + volumesAdquiridos;
    }
}
