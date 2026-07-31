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

/** Tela exibida quando o jogador perde todas as vidas. */
public class GameOverScreen implements Screen {

    private final MissaoMarteGame  jogo;
    private final OrthographicCamera camera;
    private final int              pontuacaoFinal;
    private final boolean          novoRecorde;
    private final Array<Estrela>   estrelas;

    public GameOverScreen(MissaoMarteGame jogo, int pontuacaoFinal) {
        this.jogo           = jogo;
        this.pontuacaoFinal = pontuacaoFinal;
        // highScore já foi atualizado em GameScreen antes de chamar esta tela
        this.novoRecorde    = pontuacaoFinal > 0 && pontuacaoFinal >= jogo.highScore;

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, MissaoMarteGame.WIDTH, MissaoMarteGame.HEIGHT);

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
        ScreenUtils.clear(0.08f, 0.01f, 0.01f, 1);
        camera.update();

        // Estrelas de fundo
        jogo.shapes.setProjectionMatrix(camera.combined);
        jogo.shapes.begin(ShapeRenderer.ShapeType.Filled);
        for (Estrela e : estrelas) e.desenhar(jogo.shapes);
        jogo.shapes.end();

        // Texto
        jogo.batch.setProjectionMatrix(camera.combined);
        jogo.batch.begin();

        jogo.font.getData().setScale(3.5f);
        jogo.font.setColor(Color.RED);
        jogo.font.draw(jogo.batch, "GAME OVER", 205, 460);

        jogo.font.getData().setScale(1.5f);
        jogo.font.setColor(Color.WHITE);
        jogo.font.draw(jogo.batch, "Pontuacao final: " + pontuacaoFinal, 225, 375);

        jogo.font.setColor(novoRecorde ? Color.GOLD : Color.YELLOW);
        jogo.font.draw(jogo.batch, "Recorde:         " + jogo.highScore,  225, 340);

        if (novoRecorde) {
            jogo.font.getData().setScale(1.1f);
            jogo.font.setColor(Color.GOLD);
            jogo.font.draw(jogo.batch, "*** NOVO RECORDE! ***", 265, 305);
        }

        jogo.font.getData().setScale(1.2f);
        jogo.font.setColor(Color.GREEN);
        jogo.font.draw(jogo.batch, "ENTER  -  Jogar novamente", 218, 235);
        jogo.font.setColor(Color.CYAN);
        jogo.font.draw(jogo.batch, "M      -  Menu principal",  218, 205);
        jogo.font.setColor(Color.GRAY);
        jogo.font.draw(jogo.batch, "ESC    -  Sair do jogo",    218, 175);

        jogo.batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            jogo.setScreen(new GameScreen(jogo));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            jogo.setScreen(new MenuScreen(jogo));
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
