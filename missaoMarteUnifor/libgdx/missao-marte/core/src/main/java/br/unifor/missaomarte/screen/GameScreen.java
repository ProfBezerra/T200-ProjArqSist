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
import br.unifor.missaomarte.model.Explosao;
import br.unifor.missaomarte.model.Nave;
import br.unifor.missaomarte.model.Projetil;

/**
 * Tela principal â€” contÃ©m o game loop completo:
 *   input â†’ lÃ³gica â†’ colisÃ£o â†’ renderizaÃ§Ã£o
 *
 * Conceitos demonstrados:
 *  - SPRITE       : projÃ©teis usam Texture + Sprite (ver Projetil.java)
 *  - COLISÃƒO      : naveÃ—asteroide e projÃ©tilÃ—asteroide com Intersector
 *  - SOM          : tiro, explosÃ£o e game over via GerenciadorSom
 *  - HUD          : painel superior com pontos, nÃ­vel, escudo e combo
 *  - EFEITO VISUAL: Explosao.java â€” objetos temporÃ¡rios com ciclo de vida
 */
public class GameScreen implements Screen {

    // â”€â”€ Constantes de jogo â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    private static final float TEMPO_POR_NIVEL      = 15f;
    private static final float SPAWN_INTERVALO_INI  = 1.2f;
    private static final float SPAWN_INTERVALO_MIN  = 0.28f;
    private static final float FATOR_POR_NIVEL      = 0.40f;
    private static final float TEMPO_INVENCIVEL     = 2.0f;
    private static final int   VIDAS_INICIAIS       = 3;
    private static final int   PONTOS_POR_SEGUNDO   = 10;
    private static final int   PONTOS_POR_ASTEROIDE = 50;
    private static final float COOLDOWN_TIRO        = 0.22f; // segundos entre tiros

    // DimensÃµes do HUD fixo no topo da tela
    private static final float HUD_ALTURA = 52f;
    private static final float HUD_Y      = MissaoMarteGame.HEIGHT - HUD_ALTURA;

    // â”€â”€ Estado da tela â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    private final MissaoMarteGame   jogo;
    private final OrthographicCamera camera;

    private Nave             nave;
    private Array<Asteroide> asteroides;
    private Array<Estrela>   estrelas;
    private Array<Projetil>  projeteis;   // SPRITE: lista de balas em voo
    private Array<Explosao>  explosoes;   // EFEITO: explosÃµes ativas

    private int   pontuacao;
    private int   vidas;
    private int   nivel;
    private int   asteroidsDestruidos;   // contador de acertos (para HUD)
    private float tempoJogo;
    private float tempoSpawn;
    private float spawnIntervalo;
    private float tempoInvencivel;       // >0 â†’ nave invencÃ­vel (pisca)
    private float cooldownTiro;          // tempo restante atÃ© prÃ³ximo tiro
    private boolean pausado;

    public GameScreen(MissaoMarteGame jogo) {
        this.jogo   = jogo;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, MissaoMarteGame.WIDTH, MissaoMarteGame.HEIGHT);
    }

    // â”€â”€ Ciclo de vida da Screen â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€

    @Override
    public void show() {
        nave      = new Nave(MissaoMarteGame.WIDTH / 2f, 80f);
        asteroides = new Array<>();
        estrelas   = gerarEstrelas(85);
        projeteis  = new Array<>();
        explosoes  = new Array<>();

        pontuacao          = 0;
        vidas              = VIDAS_INICIAIS;
        nivel              = 1;
        asteroidsDestruidos = 0;
        tempoJogo          = 0f;
        tempoSpawn         = 0f;
        spawnIntervalo     = SPAWN_INTERVALO_INI;
        tempoInvencivel    = 0f;
        cooldownTiro       = 0f;
        pausado            = false;
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
    @Override public void dispose() { projeteis.clear(); asteroides.clear(); }

    // â”€â”€ LÃ³gica de jogo â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€

    private void processarInputGlobal() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) jogo.setScreen(new MenuScreen(jogo));
        if (Gdx.input.isKeyJustPressed(Input.Keys.P))      pausado = !pausado;
    }

    private void atualizar(float delta) {
        tempoJogo += delta;
        pontuacao  = (int) (tempoJogo * PONTOS_POR_SEGUNDO) + asteroidsDestruidos * PONTOS_POR_ASTEROIDE;

        nivel = 1 + (int) (tempoJogo / TEMPO_POR_NIVEL);
        float fatorDif = 1f + (nivel - 1) * FATOR_POR_NIVEL;
        spawnIntervalo = Math.max(SPAWN_INTERVALO_MIN, SPAWN_INTERVALO_INI / fatorDif);

        // Nave
        nave.processarInput(delta);
        nave.limitarNaTela(MissaoMarteGame.WIDTH, MissaoMarteGame.HEIGHT);
        nave.atualizar(delta);

        // Tiro (SPACE ou seta-cima com cooldown)
        if (cooldownTiro > 0) cooldownTiro -= delta;
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && cooldownTiro <= 0) {
            projeteis.add(new Projetil(nave.getX(), nave.getY() + Nave.RAIO));
            jogo.som.tocarTiro();    // SOM: efeito sonoro do disparo
            cooldownTiro = COOLDOWN_TIRO;
        }

        // Invencibilidade apÃ³s colisÃ£o
        if (tempoInvencivel > 0) tempoInvencivel -= delta;

        // Spawn de asteroides
        tempoSpawn += delta;
        if (tempoSpawn >= spawnIntervalo) {
            asteroides.add(new Asteroide(fatorDif));
            tempoSpawn = 0f;
        }

        atualizarProjeteis(delta);
        atualizarAsteroides(delta);
        atualizarExplosoes(delta);
        verificarColisoes();
    }

    private void atualizarProjeteis(float delta) {
        Array<Projetil> remover = new Array<>();
        for (Projetil p : projeteis) {
            p.atualizar(delta);
            if (p.saiuDaTela(MissaoMarteGame.HEIGHT)) remover.add(p);
        }
        projeteis.removeAll(remover, true);
    }

    private void atualizarAsteroides(float delta) {
        Array<Asteroide> remover = new Array<>();
        for (Asteroide a : asteroides) {
            a.atualizar(delta);
            if (a.saiuDaTela()) remover.add(a);
        }
        asteroides.removeAll(remover, true);
    }

    private void atualizarExplosoes(float delta) {
        Array<Explosao> remover = new Array<>();
        for (Explosao e : explosoes) {
            e.atualizar(delta);
            if (e.terminou()) remover.add(e);
        }
        explosoes.removeAll(remover, true);
    }

    /**
     * COLISÃƒO: verifica dois tipos de sobreposiÃ§Ã£o com Intersector.overlaps():
     *  1. ProjÃ©til (Circle) Ã— Asteroide (Circle) â†’ destrÃ³i asteroide + pontos + explosÃ£o + som
     *  2. Nave     (Circle) Ã— Asteroide (Circle) â†’ perde vida + invencibilidade + som
     */
    private void verificarColisoes() {
        // 1. ProjÃ©teis acertando asteroides
        Array<Projetil>  projAcertaram    = new Array<>();
        Array<Asteroide> asterAcertados   = new Array<>();

        for (Projetil p : projeteis) {
            for (Asteroide a : asteroides) {
                if (Intersector.overlaps(p.getBounds(), a.getBounds())) {
                    projAcertaram.add(p);
                    asterAcertados.add(a);
                    // Cria efeito visual de explosÃ£o no local do asteroide
                    explosoes.add(new Explosao(a.getX(), a.getY(), a.getRaio() * 1.8f));
                    jogo.som.tocarExplosao(); // SOM: explosÃ£o do asteroide
                    asteroidsDestruidos++;
                    break; // um tiro destrÃ³i um asteroide
                }
            }
        }
        projeteis.removeAll(projAcertaram, true);
        asteroides.removeAll(asterAcertados, true);

        // 2. Nave batendo em asteroide (ignorada durante invencibilidade)
        if (tempoInvencivel > 0) return;
        for (Asteroide a : asteroides) {
            if (Intersector.overlaps(nave.getBounds(), a.getBounds())) {
                vidas--;
                tempoInvencivel = TEMPO_INVENCIVEL;
                explosoes.add(new Explosao(nave.getX(), nave.getY(), Nave.RAIO * 2f));
                if (vidas <= 0) {
                    if (pontuacao > jogo.highScore) jogo.highScore = pontuacao;
                    jogo.som.tocarGameOver(); // SOM: fim de jogo
                    jogo.setScreen(new GameOverScreen(jogo, pontuacao));
                }
                return;
            }
        }
    }

    // â”€â”€ RenderizaÃ§Ã£o â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€

    private void desenhar() {
        ScreenUtils.clear(0.02f, 0.02f, 0.10f, 1);
        camera.update();

        // â”€â”€ ShapeRenderer: formas geomÃ©tricas â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
        jogo.shapes.setProjectionMatrix(camera.combined);
        jogo.shapes.begin(ShapeRenderer.ShapeType.Filled);

        // Fundo: estrelas
        for (Estrela e : estrelas) e.desenhar(jogo.shapes);

        // Asteroides
        for (Asteroide a : asteroides) a.desenhar(jogo.shapes);

        // ExplosÃµes (EFEITO VISUAL temporÃ¡rio)
        for (Explosao ex : explosoes) ex.desenhar(jogo.shapes);

        // Nave (pisca durante invencibilidade a ~8 Hz)
        boolean mostrarNave = tempoInvencivel <= 0 || (int) (tempoInvencivel * 8) % 2 == 0;
        if (mostrarNave) nave.desenhar(jogo.shapes);

        // HUD â€“ barra de escudo (vidas representadas visualmente)
        desenharEscudo();

        jogo.shapes.end();

        // â”€â”€ SpriteBatch: sprites (projÃ©teis) e texto (HUD) â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
        jogo.batch.setProjectionMatrix(camera.combined);
        jogo.batch.begin();

        // SPRITE: projÃ©teis desenhados com SpriteBatch (Texture + Sprite)
        for (Projetil p : projeteis) p.desenhar(jogo.batch);

        // HUD: texto
        desenharHUD();

        jogo.batch.end();
    }

    /**
     * HUD: painel superior com pontuaÃ§Ã£o, nÃ­vel, escudo e destroÃ§ados.
     * HUD (Head-Up Display) Ã© a camada de UI sobreposta Ã  cena do jogo.
     */
    private void desenharHUD() {
        // Linha 1: pontuaÃ§Ã£o e nÃ­vel
        jogo.font.getData().setScale(1.15f);
        jogo.font.setColor(Color.WHITE);
        jogo.font.draw(jogo.batch, "Pontos: " + pontuacao, 10, MissaoMarteGame.HEIGHT - 8);

        jogo.font.setColor(Color.LIGHT_GRAY);
        jogo.font.draw(jogo.batch, "Nivel " + nivel, MissaoMarteGame.WIDTH - 90, MissaoMarteGame.HEIGHT - 8);

        // Linha 2: escudo textual, destroÃ§ados e controles
        jogo.font.getData().setScale(0.80f);
        jogo.font.setColor(Color.CYAN);
        jogo.font.draw(jogo.batch, "Escudo:", 10, MissaoMarteGame.HEIGHT - 30);

        jogo.font.setColor(Color.ORANGE);
        jogo.font.draw(jogo.batch, "Destruidos: " + asteroidsDestruidos,
            MissaoMarteGame.WIDTH / 2f - 60, MissaoMarteGame.HEIGHT - 30);

        jogo.font.setColor(Color.DARK_GRAY);
        jogo.font.getData().setScale(0.72f);
        jogo.font.draw(jogo.batch, "[WASD] Mover  [SPACE] Tiro  [P] Pausa  [ESC] Menu",
            10, 18);

        if (pausado) {
            jogo.font.getData().setScale(2.5f);
            jogo.font.setColor(Color.YELLOW);
            jogo.font.draw(jogo.batch, "PAUSADO", 278, 335);
            jogo.font.getData().setScale(1f);
            jogo.font.setColor(Color.WHITE);
            jogo.font.draw(jogo.batch, "Pressione P para continuar", 238, 290);
        }
    }

    /**
     * Barra de escudo (shield bar) â€” representa as vidas restantes visualmente.
     * Cada "cÃ©lula" Ã© um retÃ¢ngulo colorido: verde = intacto, vazio = perdido.
     */
    private void desenharEscudo() {
        float cellW = 22f;
        float cellH = 12f;
        float gapX  = 4f;
        float startX = 68f;
        float startY = MissaoMarteGame.HEIGHT - 46f;

        for (int i = 0; i < VIDAS_INICIAIS; i++) {
            float cx = startX + i * (cellW + gapX);
            if (i < vidas) {
                // Vida restante: gradiente verde â†’ amarelo conforme vida diminui
                float ratio = (float) vidas / VIDAS_INICIAIS;
                jogo.shapes.setColor(1f - ratio, ratio, 0f, 1f);
            } else {
                // Vida perdida: cÃ©lula escura
                jogo.shapes.setColor(0.15f, 0.15f, 0.15f, 1f);
            }
            jogo.shapes.rect(cx, startY, cellW, cellH);

            // Borda branca ao redor de cada cÃ©lula
            jogo.shapes.setColor(Color.DARK_GRAY);
            jogo.shapes.rect(cx - 1, startY - 1, cellW + 2, 1);    // base
            jogo.shapes.rect(cx - 1, startY + cellH, cellW + 2, 1); // topo
            jogo.shapes.rect(cx - 1, startY, 1, cellH);             // esquerda
            jogo.shapes.rect(cx + cellW, startY, 1, cellH);         // direita
        }
    }

    // â”€â”€ Helpers â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€

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
