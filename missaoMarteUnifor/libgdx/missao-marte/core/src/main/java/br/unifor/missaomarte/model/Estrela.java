package br.unifor.missaomarte.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

/** Ponto luminoso fixo no fundo — puro efeito visual, sem lógica de jogo. */
public class Estrela {

    private final float x;
    private final float y;
    private final float raio;
    private final Color cor;

    public Estrela(float x, float y, float raio) {
        this.x    = x;
        this.y    = y;
        this.raio = raio;
        float brilho = MathUtils.random(0.45f, 1.0f);
        this.cor = new Color(brilho, brilho, brilho * 0.85f, 1f);
    }

    public void desenhar(ShapeRenderer shapes) {
        shapes.setColor(cor);
        shapes.circle(x, y, raio, 4); // 4 segmentos = aspecto de losango/estrela pixelada
    }
}
