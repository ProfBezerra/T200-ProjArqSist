package graspexercicio10.service;

import graspexercicio10.model.*;
import graspexercicio10.repository.IRankingRepository;
import graspexercicio10.repository.RankingEntry;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * GRASP High Cohesion: gerencia exclusivamente o estado e as regras de uma partida.
 *
 * GRASP Low Coupling: GameController depende apenas desta classe; não precisa
 * conhecer Missao, Nave, Dificuldade ou IRankingRepository diretamente.
 *
 * GRASP Information Expert: consolida as regras de jogo (pontuação, vitória, derrota)
 * que antes estavam espalhadas em Main.
 */
public class JogoService {

    private final IRankingRepository rankingRepo;
    private final FabricaMissao      fabrica;
    private final Random             random;

    // Estado da partida em andamento
    private Missao      missao;
    private String      pilotoNome;
    private Dificuldade dificuldade;
    private int         minX, maxX, minY, maxY;
    private int         score;
    private int         movimentos;
    private long        tempoInicio;
    private boolean     partidaAtiva;

    public JogoService(IRankingRepository rankingRepo) {
        this.rankingRepo = rankingRepo;
        this.fabrica     = new FabricaMissao();
        this.random      = new Random();
    }

    public void iniciarPartida(String pilotoNome, Dificuldade dificuldade, int tamanhoMapa) {
        this.pilotoNome   = pilotoNome;
        this.dificuldade  = dificuldade;
        this.minX = -tamanhoMapa;  this.maxX = tamanhoMapa;
        this.minY = -tamanhoMapa;  this.maxY = tamanhoMapa;
        this.score        = dificuldade.getPontuacaoInicial(); // Information Expert em Dificuldade
        this.movimentos   = 0;
        this.tempoInicio  = System.currentTimeMillis();
        this.partidaAtiva = true;
        this.missao       = fabrica.criar(dificuldade, minX, maxX, minY, maxY, random);
    }

    /** Move a nave e retorna mensagem de feedback (pode estar vazia). */
    public String mover(char direcao) {
        missao.getNave().moverComLimites(direcao, minX, maxX, minY, maxY);
        score--;
        movimentos++;
        missao.moverInimigos(random, minX, maxX, minY, maxY);
        return verificarEstado();
    }

    /** Tenta embarcar passageiro na posição atual; retorna mensagem de feedback. */
    public String embarcar() {
        Passageiro p = missao.passagemNaPosicao();
        if (p == null) return "Nenhum passageiro nesta posição.";
        if (!missao.embarcarPassageiroNaPosicao()) return "Nave cheia!";
        score += p.getPontuacao();
        return String.format("Passageiro %s embarcado! +%d pontos!", p.getNome(), p.getPontuacao());
    }

    // ── Estado interno ────────────────────────────────────────────────────────

    private String verificarEstado() {
        if (missao.verificaColisao()) {
            missao.getNave().perderVida();
            if (missao.getNave().getVidas() <= 0) {
                partidaAtiva = false;
                return "GAME OVER! A nave foi destruída.";
            }
            return String.format("Colisão! Vidas restantes: %d", missao.getNave().getVidas());
        }
        if (score <= 0) {
            partidaAtiva = false;
            return "Combustível esgotado! Missão perdida.";
        }
        if (missao.todosEmbarcados() && naveNaBase()) {
            finalizarComSucesso();
            return "MISSÃO CUMPRIDA! Retorno à órbita marciana concluído.";
        }
        if (missao.todosEmbarcados()) {
            return "Todos embarcados! Retorne à base (0,0).";
        }
        return "";
    }

    private boolean naveNaBase() {
        return missao.getNave().getX() == 0 && missao.getNave().getY() == 0;
    }

    private void finalizarComSucesso() {
        partidaAtiva = false;
        List<RankingEntry> ranking = rankingRepo.carregar();
        if (score > 0 && rankingRepo.ehTopScore(ranking, score)) {
            ranking.add(new RankingEntry(
                    pilotoNome, score, dificuldade,
                    missao.getNave().getPassageiros().size(),
                    LocalDateTime.now().toString().substring(0, 19).replace('T', ' '),
                    getTempoJogo()
            ));
            rankingRepo.salvar(ranking);
        }
    }

    // ── Getters de estado (lidos pelo Controller) ─────────────────────────────

    public boolean     isPartidaAtiva() { return partidaAtiva; }
    public Missao      getMissao()      { return missao; }
    public int         getScore()       { return score; }
    public int         getMovimentos()  { return movimentos; }
    public long        getTempoJogo()   { return (System.currentTimeMillis() - tempoInicio) / 1000; }
    public String      getPilotoNome()  { return pilotoNome; }
    public int         getMinX()        { return minX; }
    public int         getMaxX()        { return maxX; }
    public int         getMinY()        { return minY; }
    public int         getMaxY()        { return maxY; }
}
