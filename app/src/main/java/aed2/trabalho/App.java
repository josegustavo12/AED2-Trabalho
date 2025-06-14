package aed2.trabalho;

public class App {
    public static void main(String[] args) {
        MangaManager manager = new MangaManager();
        try {
            manager.carregarDados();
            manager.salvarIndices();
            System.out.println("Índices criados com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao processar dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
