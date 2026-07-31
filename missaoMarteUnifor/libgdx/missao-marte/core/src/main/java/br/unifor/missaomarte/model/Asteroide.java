package br.unifor.missaomarte.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import br.unifor.missaomarte.MissaoMarteGame;

/**
 * Obstáculo que cai de cima para baixo com leve drift lateral.
 * Velocidade e tamanho são aleatórios; ambos escalam com nivelDificuldade.
 */
public class Asteroide {

    private float x;
    private float y;
    private final float raio;
    private final float velocidadeY;
    private final float velocidadeX; // leve deriva lateral
    private final Circle bounds;
    private final Color  corPrincipal;
    private final Color  corCratera;

    public Asteroide(float nivelDificuldade) {
        this.raio        = MathUtils.random(14f, 34f);
        this.x           = MathUtils.random(raio, MissaoMarteGame.WIDTH - raio);
        this.y           = MissaoMarteGame.HEIGHT + raio;
        this.velocidadeY = MathUtils.random(100f, 220f) * nivelDificuldade;
        this.velocidadeX = MathUtils.random(-28f, 28f);
        // Hitbox 80% do raio visual — colisão ligeiramente generosa
        this.bounds = new Circle(x, y, raio * 0.80f);

        // Tons de laranja, marrom e vermelho
        float r = MathUtils.random(0.55f, 0.90f);
        float g = MathUtils.random(0.20f, 0.45f);
        float b = MathUtils.random(0.00f, 0.15f);
        this.corPrincipal = new Color(r, g, b, 1f);
        this.corCratera   = new Color(r * 0.55f, g * 0.55f, b * 0.55f, 1f);
    }

    public void atualizar(float delta) {
        y -= velocidadeY * delta;
        x += velocidadeX * delta;
        x  = MathUtils.clamp(x, raio, MissaoMarteGame.WIDTH - raio);
        bounds.setPosition(x, y);
    }

    public void desenhar(ShapeRenderer shapes) {
        // Corpo com 9 segmentos — aspecto irregular de rocha
        shapes.setColor(corPrincipal);
        shapes.circle(x, y, raio, 9);

        // Duas crateras para dar volume
        shapes.setColor(corCratera);
        shapes.circle(x - raio * 0.30f, y + raio * 0.25f, raio * 0.22f, 7);
        shapes.circle(x + raio * 0.35f, y - raio * 0.20f, raio * 0.17f, 6);
    }

    /** Retorna true quando o asteroide sai completamente pela parte de baixo da tela. */
    public boolean saiuDaTela() {
        return y + raio < 0;
    }

    public Circle getBounds() { return bounds; }
}
