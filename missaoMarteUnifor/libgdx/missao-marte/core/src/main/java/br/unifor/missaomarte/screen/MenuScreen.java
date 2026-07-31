package br.unifor.missaomarte.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import br.unifor.missaomarte.MissaoMarteGame;
import br.unifor.missaomarte.model.Estrela;

/** Tela inicial: exibe título, controles e aguarda ENTER para começar. */
public class MenuScreen implements Screen {

    private final MissaoMarteGame  jogo;
    private final OrthographicCamera camera;
    private final Array<Estrela>   estrelas;

    public MenuScreen(MissaoMarteGame jogo) {
        this.jogo   = jogo;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, MissaoMarteGame.WIDTH, MissaoMarteGame.HEIGHT);

        // Gera campo de estrelas estático para o fundo
        estrelas = new Array<>();
        for (int i = 0; i < 90; i++) {
            estrelas.add(new Estrela(
                MathUtils.random(0, MissaoMarteGame.WIDTH),
                MathUtils.random(0, MissaoMarteGame.HEIGHT),
                MathUtils.random(0.5f, 2.2f)
            ));
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.03f, 0.03f, 0.12f, 1);
        camera.update();

        // Fundo: estrelas + nave decorativa
        jogo.shapes.setProjectionMatrix(camera.combined);
        jogo.shapes.begin(ShapeRenderer.ShapeType.Filled);
        for (Estrela e : estrelas) e.desenhar(jogo.shapes);
        jogo.shapes.setColor(Color.CYAN);
        jogo.shapes.triangle(400, 490, 375, 455, 425, 455); // mini-nave de enfeite
        jogo.shapes.end();

        // Texto (BitmapFont padrão suporta apenas ASCII)
        jogo.batch.setProjectionMatrix(camera.combined);
        jogo.batch.begin();

        jogo.font.getData().setScale(3.5f);
        jogo.font.setColor(Color.CYAN);
        jogo.font.draw(jogo.batch, "MISSAO MARTE", 160, 435);

        jogo.font.getData().setScale(1.3f);
        jogo.font.setColor(new Color(0.7f, 0.7f, 1f, 1f));
        jogo.font.draw(jogo.batch, "Unifor  -  2026.2", 275, 390);

        jogo.font.getData().setScale(1f);
        jogo.font.setColor(Color.YELLOW);
        jogo.font.draw(jogo.batch, "Controles:", 100, 320);
        jogo.font.setColor(Color.WHITE);
        jogo.font.draw(jogo.batch, "W A S D  ou  Setas  -  Mover a nave", 100, 295);
        jogo.font.draw(jogo.batch, "P               -  Pausar / Retomar", 100, 270);
        jogo.font.draw(jogo.batch, "ESC             -  Voltar / Sair", 100, 245);

        jogo.font.getData().setScale(1.3f);
        jogo.font.setColor(Color.GREEN);
        jogo.font.draw(jogo.batch, "Pressione ENTER para jogar!", 185, 175);

        if (jogo.highScore > 0) {
            jogo.font.getData().setScale(1f);
            jogo.font.setColor(Color.ORANGE);
            jogo.font.draw(jogo.batch, "Melhor pontuacao: " + jogo.highScore, 285, 120);
        }

        jogo.font.getData().setScale(0.8f);
        jogo.font.setColor(Color.DARK_GRAY);
        jogo.font.draw(jogo.batch, "T200 - Projeto e Arquitetura de Sistemas", 190, 40);

        jogo.batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            jogo.setScreen(new GameScreen(jogo));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    @Override public void show() {}
    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}
