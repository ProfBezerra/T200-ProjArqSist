package br.unifor.missaomarte.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;

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
    private boolean boosting;    // true quando W/↑ pressionado
    private float   tempoBoost;  // acumula tempo para oscilar a chama

    public Nave(float x, float y) {
        this.x      = x;
        this.y      = y;
        this.bounds = new Circle(x, y, RAIO_HITBOX);
    }

    public void processarInput(float delta) {
        boosting = Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)  || Gdx.input.isKeyPressed(Input.Keys.A)) x -= VELOCIDADE * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) x += VELOCIDADE * delta;
        if (boosting)                                                                          y += VELOCIDADE * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)  || Gdx.input.isKeyPressed(Input.Keys.S)) y -= VELOCIDADE * delta;
    }

    public void limitarNaTela(float largura, float altura) {
        x = Math.max(RAIO, Math.min(x, largura - RAIO));
        y = Math.max(RAIO, Math.min(y, altura  - RAIO));
    }

    /** Sincroniza a hitbox e avança o acumulador de animação da chama. */
    public void atualizar(float delta) {
        bounds.setPosition(x, y);
        if (boosting) tempoBoost += delta;
        else          tempoBoost  = 0f;
    }

    public void desenhar(ShapeRenderer shapes) {
        if (boosting) {
            // Chama oscila entre 0.6 e 1.0 a ~35 Hz — efeito de combustão
            float flicker = 0.6f + 0.4f * MathUtils.sin(tempoBoost * 35f);
            float flameH  = 18f + 14f * flicker;

            // Camada externa: laranja/vermelho
            shapes.setColor(1f, 0.25f + 0.25f * flicker, 0f, 1f);
            shapes.triangle(
                x - 10, y - RAIO,
                x + 10, y - RAIO,
                x,      y - RAIO - flameH
            );
            // Camada média: amarelo
            shapes.setColor(1f, 0.85f, 0.1f, 1f);
            shapes.triangle(
                x - 5.5f, y - RAIO,
                x + 5.5f, y - RAIO,
                x,        y - RAIO - flameH * 0.65f
            );
            // Núcleo: branco quente
            shapes.setColor(Color.WHITE);
            shapes.triangle(
                x - 2.5f, y - RAIO,
                x + 2.5f, y - RAIO,
                x,        y - RAIO - flameH * 0.30f
            );
        } else {
            // Idle: pequena chama azul estática
            shapes.setColor(new Color(0.3f, 0.5f, 1.0f, 1f));
            shapes.triangle(
                x - 7, y - RAIO,
                x + 7, y - RAIO,
                x,     y - RAIO - 14
            );
        }

        // Corpo principal — triângulo ciano apontando para cima
        shapes.setColor(Color.CYAN);
        shapes.triangle(
            x,        y + RAIO,
            x - RAIO, y - RAIO,
            x + RAIO, y - RAIO
        );

        // Cockpit central
        shapes.setColor(Color.WHITE);
        shapes.circle(x, y, 6, 8);
    }

    public Circle getBounds() { return bounds; }
    public float  getX()      { return x; }
    public float  getY()      { return y; }
}
