package br.unifor.missaomarte.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

/**
 * Nave controlada pelo jogador.
 * Movimenta-se com WASD ou setas; hitbox circular menor que o visual
 * para uma experiência mais justa.
 */
public class Nave {

    public static final float RAIO       = 22f;
    public static final float VELOCIDADE = 300f;

    // Hitbox 70% do raio visual — colisão perdoa as bordas do sprite
    private static final float RAIO_HITBOX = RAIO * 0.70f;

    private float x;
    private float y;
    private final Circle bounds;

    public Nave(float x, float y) {
        this.x      = x;
        this.y      = y;
        this.bounds = new Circle(x, y, RAIO_HITBOX);
    }

    public void processarInput(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)  || Gdx.input.isKeyPressed(Input.Keys.A)) x -= VELOCIDADE * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) x += VELOCIDADE * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)    || Gdx.input.isKeyPressed(Input.Keys.W)) y += VELOCIDADE * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)  || Gdx.input.isKeyPressed(Input.Keys.S)) y -= VELOCIDADE * delta;
    }

    public void limitarNaTela(float largura, float altura) {
        x = Math.max(RAIO, Math.min(x, largura - RAIO));
        y = Math.max(RAIO, Math.min(y, altura  - RAIO));
    }

    /** Sincroniza a hitbox com a posição atual. Chamar após mover. */
    public void atualizar() {
        bounds.setPosition(x, y);
    }

    public void desenhar(ShapeRenderer shapes) {
        // Chama do motor (triângulo azul abaixo da nave)
        shapes.setColor(new Color(0.3f, 0.5f, 1.0f, 1f));
        shapes.triangle(
            x - 7, y - RAIO,
            x + 7, y - RAIO,
            x,     y - RAIO - 14
        );

        // Corpo principal — triângulo ciano apontando para cima
        shapes.setColor(Color.CYAN);
        shapes.triangle(
            x,        y + RAIO, // topo
            x - RAIO, y - RAIO, // base esquerda
            x + RAIO, y - RAIO  // base direita
        );

        // Cockpit central
        shapes.setColor(Color.WHITE);
        shapes.circle(x, y, 6, 8);
    }

    public Circle getBounds() { return bounds; }
    public float  getX()      { return x; }
    public float  getY()      { return y; }
}
