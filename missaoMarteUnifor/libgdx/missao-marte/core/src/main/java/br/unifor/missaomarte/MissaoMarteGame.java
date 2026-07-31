package br.unifor.missaomarte;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import br.unifor.missaomarte.audio.GerenciadorSom;
import br.unifor.missaomarte.model.Projetil;
import br.unifor.missaomarte.screen.MenuScreen;

/**
 * Ponto de entrada da lógica do jogo.
 * Compartilha batch, shapes, font e som entre todas as telas para economizar memória.
 */
public class MissaoMarteGame extends Game {

    public static final int    WIDTH  = 800;
    public static final int    HEIGHT = 600;
    public static final String TITULO = "Missão Marte Unifor";

    public SpriteBatch   batch;
    public ShapeRenderer shapes;
    public BitmapFont    font;
    public GerenciadorSom som;

    // Persiste entre partidas dentro da mesma execução
    public int highScore = 0;

    @Override
    public void create() {
        batch  = new SpriteBatch();
        shapes = new ShapeRenderer();
        font   = new BitmapFont(); // fonte padrão libGDX (ASCII)
        som    = new GerenciadorSom();
        setScreen(new MenuScreen(this));
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapes.dispose();
        font.dispose();
        som.dispose();
        Projetil.disposeTextura(); // libera textura estática compartilhada
    }
}

