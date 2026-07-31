package br.unifor.missaomarte.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import br.unifor.missaomarte.MissaoMarteGame;
import br.unifor.missaomarte.model.Asteroide;
import br.unifor.missaomarte.model.Estrela;
import br.unifor.missaomarte.model.Nave;

/**
 * Tela principal — contém o game loop completo:
 * input → lógica → colisão → renderização.
 *
 * Mecânicas:
 *  - 3 vidas; colisão concede 2 s de invencibilidade (nave pisca)
 *  - Nível sobe a cada 15 s → spawn mais frequente e asteroides mais rápidos
 *  - P pausa/retoma; ESC volta ao menu
 */
public class GameScreen implements Screen {

    // ── Constantes de dificuldade ────────────────────────────────────────────
    private static final float TEMPO_POR_NIVEL     = 15f;  // segundos por nível
    private static final float SPAWN_INTERVALO_INI = 1.2f; // intervalo inicial entre spawns
    private static final float SPAWN_INTERVALO_MIN = 0.30f; // intervalo mínimo
    private static final float FATOR_POR_NIVEL     = 0.40f; // quanto a velocidade cresce/nível
    private static final float TEMPO_INVENCIVEL    = 2.0f;  // segundos após colisão
    private static final int   VIDAS_INICIAIS      = 3;
    private static final int   PONTOS_POR_SEGUNDO  = 10;

    // ── Estado da tela ───────────────────────────────────────────────────────
    private final MissaoMarteGame  jogo;
    private final OrthographicCamera camera;

    private Nave             nave;
    private Array<Asteroide> asteroides;
    private Array<Estrela>   estrelas;

    private int   pontuacao;
    private int   vidas;
    private int   nivel;
    private float tempoJogo;
    private float tempoSpawn;
    private float spawnIntervalo;
    private float tempoInvencivel;  // contador decrescente; > 0 = nave invencível
    private boolean pausado;

    public GameScreen(MissaoMarteGame jogo) {
        this.jogo   = jogo;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, MissaoMarteGame.WIDTH, MissaoMarteGame.HEIGHT);
    }

    // ── Ciclo de vida da Screen ──────────────────────────────────────────────

    @Override
    public void show() {
        nave      = new Nave(MissaoMarteGame.WIDTH / 2f, 80f);
        asteroides = new Array<>();
        estrelas   = gerarEstrelas(85);

        pontuacao      = 0;
        vidas          = VIDAS_INICIAIS;
        nivel          = 1;
        tempoJogo      = 0f;
        tempoSpawn     = 0f;
        spawnIntervalo = SPAWN_INTERVALO_INI;
        tempoInvencivel = 0f;
        pausado        = false;
    }

    @Override
    public void render(float delta) {
        processarInputGlobal();
        if (!pausado) atualizar(delta);
        desenhar();
    }

    @Override public void resize(int w, int h) {}
    @Override public void pause()  { pausado = true; }
    @Override public void resume() {}
    @Override public void hide()   {}
    @Override public void dispose() { asteroides.clear(); }

    // ── Lógica de jogo ───────────────────────────────────────────────────────

    private void processarInputGlobal() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            jogo.setScreen(new MenuScreen(jogo));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            pausado = !pausado;
        }
    }

    private void atualizar(float delta) {
        tempoJogo += delta;
        pontuacao  = (int) (tempoJogo * PONTOS_POR_SEGUNDO);

        // Recalcular nível e fator de dificuldade
        nivel = 1 + (int) (tempoJogo / TEMPO_POR_NIVEL);
        float fatorDif = 1f + (nivel - 1) * FATOR_POR_NIVEL;
        spawnIntervalo = Math.max(SPAWN_INTERVALO_MIN, SPAWN_INTERVALO_INI / fatorDif);

        // Nave
        nave.processarInput(delta);
        nave.limitarNaTela(MissaoMarteGame.WIDTH, MissaoMarteGame.HEIGHT);
        nave.atualizar();

        // Conta regressiva de invencibilidade
        if (tempoInvencivel > 0) tempoInvencivel -= delta;

        // Spawn de asteroides
        tempoSpawn += delta;
        if (tempoSpawn >= spawnIntervalo) {
            asteroides.add(new Asteroide(fatorDif));
            tempoSpawn = 0f;
        }

        // Atualiza e remove asteroides que saíram da tela
        Array<Asteroide> remover = new Array<>();
        for (Asteroide a : asteroides) {
            a.atualizar(delta);
            if (a.saiuDaTela()) remover.add(a);
        }
        asteroides.removeAll(remover, true);

        // Detecta colisões (ignoradas durante invencibilidade)
        if (tempoInvencivel <= 0) verificarColisoes();
    }

    private void verificarColisoes() {
        for (Asteroide a : asteroides) {
            if (Intersector.overlaps(nave.getBounds(), a.getBounds())) {
                vidas--;
                tempoInvencivel = TEMPO_INVENCIVEL;
                if (vidas <= 0) {
                    if (pontuacao > jogo.highScore) jogo.highScore = pontuacao;
                    jogo.setScreen(new GameOverScreen(jogo, pontuacao));
                }
                return; // uma colisão por frame é suficiente
            }
        }
    }

    // ── Renderização ─────────────────────────────────────────────────────────

    private void desenhar() {
        ScreenUtils.clear(0.02f, 0.02f, 0.10f, 1);
        camera.update();

        // ── ShapeRenderer: formas geométricas ────────────────────────────────
        jogo.shapes.setProjectionMatrix(camera.combined);
        jogo.shapes.begin(ShapeRenderer.ShapeType.Filled);

        // Fundo: estrelas
        for (Estrela e : estrelas) e.desenhar(jogo.shapes);

        // Asteroides
        for (Asteroide a : asteroides) a.desenhar(jogo.shapes);

        // Nave (pisca a cada ~120 ms durante invencibilidade)
        boolean mostrarNave = tempoInvencivel <= 0 || (int) (tempoInvencivel * 8) % 2 == 0;
        if (mostrarNave) nave.desenhar(jogo.shapes);

        // Vidas: mini-naves no canto superior esquerdo
        for (int i = 0; i < vidas; i++) {
            float lx = 22 + i * 28;
            float ly = MissaoMarteGame.HEIGHT - 58;
            jogo.shapes.setColor(Color.CYAN);
            jogo.shapes.triangle(lx, ly + 16, lx - 10, ly, lx + 10, ly);
        }

        jogo.shapes.end();

        // ── SpriteBatch: texto (HUD) ──────────────────────────────────────────
        jogo.batch.setProjectionMatrix(camera.combined);
        jogo.batch.begin();

        jogo.font.getData().setScale(1.2f);
        jogo.font.setColor(Color.WHITE);
        jogo.font.draw(jogo.batch, "Pontos: " + pontuacao, 10, MissaoMarteGame.HEIGHT - 10);

        jogo.font.getData().setScale(0.85f);
        jogo.font.setColor(Color.CYAN);
        jogo.font.draw(jogo.batch, "Vidas:", 10, MissaoMarteGame.HEIGHT - 35);

        jogo.font.setColor(Color.LIGHT_GRAY);
        jogo.font.draw(jogo.batch, "Nivel " + nivel,       MissaoMarteGame.WIDTH - 92,  MissaoMarteGame.HEIGHT - 10);
        jogo.font.draw(jogo.batch, "[P]Pausa  [ESC]Menu",  MissaoMarteGame.WIDTH - 178, 20);

        if (pausado) {
            jogo.font.getData().setScale(2.5f);
            jogo.font.setColor(Color.YELLOW);
            jogo.font.draw(jogo.batch, "PAUSADO", 278, 335);
            jogo.font.getData().setScale(1f);
            jogo.font.setColor(Color.WHITE);
            jogo.font.draw(jogo.batch, "Pressione P para continuar", 238, 290);
        }

        jogo.batch.end();
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private Array<Estrela> gerarEstrelas(int n) {
        Array<Estrela> lista = new Array<>();
        for (int i = 0; i < n; i++) {
            lista.add(new Estrela(
                MathUtils.random(0, MissaoMarteGame.WIDTH),
                MathUtils.random(0, MissaoMarteGame.HEIGHT),
                MathUtils.random(0.5f, 2.2f)
            ));
        }
        return lista;
    }
}
