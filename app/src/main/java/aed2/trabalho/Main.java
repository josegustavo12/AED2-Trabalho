package aed2.trabalho;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MangaManager manager = new MangaManager();

        try {
            manager.carregarDados();

            while (true) {
                System.out.println("\n== MENU MANGÁS ==");
                System.out.println("1. Inserir novo mangá");
                System.out.println("2. Buscar por ISBN");
                System.out.println("3. Buscar por título");
                System.out.println("4. Atualizar título");
                System.out.println("5. Remover mangá");
                System.out.println("0. Sair");
                System.out.print("Escolha: ");
                int opcao = sc.nextInt(); sc.nextLine();

                switch (opcao) {
                    case 1 -> {
                        System.out.print("ISBN: ");
                        String isbn = sc.nextLine();
                        System.out.print("Título: ");
                        String titulo = sc.nextLine();
                        System.out.print("Autores (separados por vírgula): ");
                        String[] autores = sc.nextLine().split(",");
                        System.out.print("Ano de início: ");
                        int anoIni = sc.nextInt(); sc.nextLine();
                        System.out.print("Ano de fim (-1 se em publicação): ");
                        int anoFim = sc.nextInt(); sc.nextLine();
                        Integer fim = (anoFim == -1) ? null : anoFim;
                        System.out.print("Gêneros (separados por vírgula): ");
                        String[] generos = sc.nextLine().split(",");
                        System.out.print("Revista: ");
                        String revista = sc.nextLine();
                        System.out.print("Editora: ");
                        String editora = sc.nextLine();
                        System.out.print("Ano da edição: ");
                        int anoEdicao = sc.nextInt(); sc.nextLine();
                        System.out.print("Qtd. volumes total: ");
                        int qtdTotal = sc.nextInt(); sc.nextLine();
                        System.out.print("Qtd. volumes adquiridos: ");
                        int qtdAdquirido = sc.nextInt(); sc.nextLine();
                        System.out.print("Lista de volumes adquiridos (ex: 1,2,3): ");
                        String[] vols = sc.nextLine().split(",");
                        List<Integer> listaVols = new ArrayList<>();
                        for (String v : vols) listaVols.add(Integer.parseInt(v.trim()));

                        Manga novo = new Manga(
                            String.join(";",
                                isbn, titulo, String.join(",", autores),
                                String.valueOf(anoIni), (fim == null ? "-" : fim.toString()),
                                String.join(",", generos), revista, editora,
                                String.valueOf(anoEdicao),
                                String.valueOf(qtdTotal),
                                String.valueOf(qtdAdquirido),
                                "[" + String.join(",", vols) + "]"
                            )
                        );
                        manager.inserirNovo(novo);
                        System.out.println("✔ Mangá inserido.");
                    }
                    case 2 -> {
                        System.out.print("Digite o ISBN: ");
                        String isbn = sc.nextLine();
                        Manga m = manager.buscarPorISBN(isbn);
                        if (m != null) System.out.println(m);
                        else System.out.println("❌ Mangá não encontrado.");
                    }
                    case 3 -> {
                        System.out.print("Digite o título: ");
                        String titulo = sc.nextLine();
                        var lista = manager.buscarPorTitulo(titulo);
                        if (lista.isEmpty()) System.out.println("❌ Nenhum encontrado.");
                        else lista.forEach(System.out::println);
                    }
                    case 4 -> {
                        System.out.print("ISBN a alterar: ");
                        String isbn = sc.nextLine();
                        System.out.print("Novo título: ");
                        String novoTitulo = sc.nextLine();
                        if (manager.atualizarTitulo(isbn, novoTitulo)) System.out.println("✔ Título atualizado.");
                        else System.out.println("❌ Mangá não encontrado.");
                    }
                    case 5 -> {
                        System.out.print("ISBN a remover: ");
                        String isbn = sc.nextLine();
                        System.out.print("Confirmar remoção? (s/n): ");
                        String resp = sc.nextLine();
                        if (resp.equalsIgnoreCase("s")) {
                            if (manager.removerPorISBN(isbn)) System.out.println("✔ Removido.");
                            else System.out.println("❌ Mangá não encontrado.");
                        } else {
                            System.out.println("Remoção cancelada.");
                        }
                    }
                    case 0 -> {
                        System.out.println("Saindo...");
                        return;
                    }
                    default -> System.out.println("Opção inválida.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
