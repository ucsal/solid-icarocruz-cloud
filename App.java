package br.com.ucsal.olimpiadas;

import java.util.Scanner;

import br.com.ucsal.olimpiadas.repository.ParticipanteRepository;
import br.com.ucsal.olimpiadas.repository.ProvaRepository;
import br.com.ucsal.olimpiadas.repository.QuestaoRepository;
import br.com.ucsal.olimpiadas.repository.TentativaRepository;
import br.com.ucsal.olimpiadas.service.ParticipanteService;
import br.com.ucsal.olimpiadas.service.ProvaService;
import br.com.ucsal.olimpiadas.service.QuestaoService;
import br.com.ucsal.olimpiadas.service.TentativaService;
import br.com.ucsal.olimpiadas.util.TabuleiroUtil;

public class App {

    private static final Scanner in = new Scanner(System.in);

    private static final ParticipanteRepository participanteRepository = new ParticipanteRepository();
    private static final ProvaRepository provaRepository = new ProvaRepository();
    private static final QuestaoRepository questaoRepository = new QuestaoRepository();
    private static final TentativaRepository tentativaRepository = new TentativaRepository();

    private static final ParticipanteService participanteService =
            new ParticipanteService(participanteRepository);

    private static final ProvaService provaService =
            new ProvaService(provaRepository);

    private static final QuestaoService questaoService =
            new QuestaoService(questaoRepository, provaRepository);

    private static final TentativaService tentativaService =
            new TentativaService(tentativaRepository, participanteRepository, provaRepository, questaoRepository);

    public static void main(String[] args) {
        seed();

        while (true) {
            System.out.println("\n=== OLIMPÍADA DE QUESTÕES (V1) ===");
            System.out.println("1) Cadastrar participante");
            System.out.println("2) Cadastrar prova");
            System.out.println("3) Cadastrar questão (A–E) em uma prova");
            System.out.println("4) Aplicar prova (selecionar participante + prova)");
            System.out.println("5) Listar tentativas (resumo)");
            System.out.println("0) Sair");
            System.out.print("> ");

            switch (in.nextLine()) {
                case "1" -> cadastrarParticipante();
                case "2" -> cadastrarProva();
                case "3" -> cadastrarQuestao();
                case "4" -> aplicarProva();
                case "5" -> listarTentativas();
                case "0" -> {
                    System.out.println("tchau");
                    return;
                }
                default -> System.out.println("opção inválida");
            }
        }
    }

    private static void cadastrarParticipante() {
        System.out.print("Nome: ");
        String nome = in.nextLine();

        System.out.print("Email (opcional): ");
        String email = in.nextLine();

        try {
            Participante participante = participanteService.cadastrar(nome, email);
            System.out.println("Participante cadastrado: " + participante.getId());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void cadastrarProva() {
        System.out.print("Título da prova: ");
        String titulo = in.nextLine();

        try {
            Prova prova = provaService.cadastrar(titulo);
            System.out.println("Prova criada: " + prova.getId());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void cadastrarQuestao() {
        if (provaService.listar().isEmpty()) {
            System.out.println("não há provas cadastradas");
            return;
        }

        Long provaId = escolherProva();
        if (provaId == null) {
            return;
        }

        System.out.println("Enunciado:");
        String enunciado = in.nextLine();

        System.out.print("FEN inicial: ");
        String fenInicial = in.nextLine();

        String[] alternativas = new String[5];
        for (int i = 0; i < 5; i++) {
            char letra = (char) ('A' + i);
            System.out.print("Alternativa " + letra + ": ");
            alternativas[i] = letra + ") " + in.nextLine();
        }

        System.out.print("Alternativa correta (A–E): ");
        char correta;
        try {
            correta = Questao.normalizar(in.nextLine().trim().charAt(0));
        } catch (Exception e) {
            System.out.println("alternativa inválida");
            return;
        }

        try {
            Questao questao = questaoService.cadastrar(provaId, enunciado, fenInicial, alternativas, correta);
            System.out.println("Questão cadastrada: " + questao.getId() + " (na prova " + provaId + ")");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void aplicarProva() {
        if (participanteService.listar().isEmpty()) {
            System.out.println("cadastre participantes primeiro");
            return;
        }

        if (provaService.listar().isEmpty()) {
            System.out.println("cadastre provas primeiro");
            return;
        }

        Long participanteId = escolherParticipante();
        if (participanteId == null) {
            return;
        }

        Long provaId = escolherProva();
        if (provaId == null) {
            return;
        }

        try {
            Tentativa tentativa = tentativaService.aplicarProva(provaId, participanteId, in, TabuleiroUtil::imprimirTabuleiroFen);
            int nota = tentativaService.calcularNota(tentativa);

            System.out.println("\n--- Fim da Prova ---");
            System.out.println("Nota (acertos): " + nota + " / " + tentativa.getRespostas().size());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listarTentativas() {
        System.out.println("\n--- Tentativas ---");
        for (Tentativa tentativa : tentativaService.listar()) {
            System.out.printf(
                    "#%d | participante=%d | prova=%d | nota=%d/%d%n",
                    tentativa.getId(),
                    tentativa.getParticipanteId(),
                    tentativa.getProvaId(),
                    tentativaService.calcularNota(tentativa),
                    tentativa.getRespostas().size()
            );
        }
    }

    private static Long escolherParticipante() {
        System.out.println("\nParticipantes:");
        for (Participante participante : participanteService.listar()) {
            System.out.printf("  %d) %s%n", participante.getId(), participante.getNome());
        }
        System.out.print("Escolha o id do participante: ");

        try {
            long id = Long.parseLong(in.nextLine());
            if (!participanteService.existe(id)) {
                System.out.println("id inválido");
                return null;
            }
            return id;
        } catch (Exception e) {
            System.out.println("entrada inválida");
            return null;
        }
    }

    private static Long escolherProva() {
        System.out.println("\nProvas:");
        for (Prova prova : provaService.listar()) {
            System.out.printf("  %d) %s%n", prova.getId(), prova.getTitulo());
        }
        System.out.print("Escolha o id da prova: ");

        try {
            long id = Long.parseLong(in.nextLine());
            if (!provaService.existe(id)) {
                System.out.println("id inválido");
                return null;
            }
            return id;
        } catch (Exception e) {
            System.out.println("entrada inválida");
            return null;
        }
    }

    private static void seed() {
        Prova prova = provaService.cadastrar("Olimpíada 2026 • Nível 1 • Prova A");

        questaoService.cadastrar(
                prova.getId(),
                """
                Questão 1 — Mate em 1.
                É a vez das brancas.
                Encontre o lance que dá mate imediatamente.
                """,
                "6k1/5ppp/8/8/8/7Q/6PP/6K1 w - - 0 1",
                new String[]{
                        "A) Qh7#",
                        "B) Qf5#",
                        "C) Qc8#",
                        "D) Qh8#",
                        "E) Qe6#"
                },
                'C'
        );
    }
}
