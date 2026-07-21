package solidexercicio10.repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import solidexercicio10.model.Dificuldade;

public class RankingService implements RankingRepository {
    private final Path arquivo;

    public RankingService(String nomeArquivo) {
        this.arquivo = Paths.get(nomeArquivo);
    }

    @Override
    public void salvar(String nome, int pontuacao) {
        salvar(nome, pontuacao, Dificuldade.MEDIO, 0, 0);
    }

    @Override
    public void salvar(String nome, int pontuacao, Dificuldade dificuldade, int passageirosColetados, long tempoJogo) {
        List<String> linhas = new ArrayList<>();
        if (Files.exists(arquivo)) {
            try {
                linhas = Files.readAllLines(arquivo, StandardCharsets.UTF_8);
            } catch (IOException ignored) {
            }
        }
        String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        linhas.add(nome + "|" + pontuacao + "|" + dificuldade + "|" + passageirosColetados + "|" + dataHora + "|" + tempoJogo);
        try {
            Files.createDirectories(arquivo.getParent());
            Files.write(arquivo, linhas, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível salvar o ranking", e);
        }
    }

    @Override
    public List<RankingEntry> listar() {
        if (!Files.exists(arquivo)) {
            return new ArrayList<>();
        }
        try {
            List<String> linhas = Files.readAllLines(arquivo, StandardCharsets.UTF_8);
            List<RankingEntry> ranking = new ArrayList<>();
            for (String linha : linhas) {
                String[] partes = linha.split("\\|");
                if (partes.length >= 6) {
                    ranking.add(new RankingEntry(
                            partes[0],
                            Integer.parseInt(partes[1]),
                            Dificuldade.deString(partes[2]),
                            Integer.parseInt(partes[3]),
                            partes[4],
                            Long.parseLong(partes[5])
                    ));
                }
            }
            ranking.sort(Comparator.comparingInt((RankingEntry entry) -> entry.score).reversed());
            return ranking;
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void limpar() {
        try {
            Files.deleteIfExists(arquivo);
        } catch (IOException ignored) {
        }
    }
}
