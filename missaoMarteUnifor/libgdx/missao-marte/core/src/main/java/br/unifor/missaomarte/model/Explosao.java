package br.unifor.missaomarte.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Efeito visual de explosão — círculo que expande e muda de cor rapidamente.
 *
 * Demonstra o conceito de EFEITO VISUAL TEMPORÁRIO em jogos:
 * um objeto com tempo de vida limitado que altera seu visual ao longo do tempo.
 * Em engines modernas, isso é feito com sistemas de partículas (Particle System).
 */
public class Explosao {

    private static final float DURACAO = 0.45f; // segundos de duração total

    public final float x;
    public final float y;
    public final float raioMax;

    private float tempoRestante;

    public Explosao(float x, float y, float raioMax) {
        this.x             = x;
        this.y             = y;
        this.raioMax       = raioMax;
        this.tempoRestante = DURACAO;
    }

    public void atualizar(float delta) {
        tempoRestante -= delta;
    }

    /** Intensidade: 1.0 = início (brilhante), 0.0 = fim (extinto). */
    public float intensidade() {
        return Math.max(0f, tempoRestante / DURACAO);
    }

    /** Raio crescente: pequeno no início, grande no fim. */
    public float raioAtual() {
        return raioMax * (1f - intensidade());
    }

    public void desenhar(ShapeRenderer shapes) {
        float t = intensidade(); // 1→0

        // Anel externo: laranja brilhante → vermelho escuro
        shapes.setColor(t, t * 0.4f, 0f, 1f);
        shapes.circle(x, y, raioAtual() + raioMax * 0.15f, 14);

        // Núcleo: branco → amarelo à medida que esfria
        shapes.setColor(1f, t * 0.8f + 0.2f, t * 0.5f, 1f);
        shapes.circle(x, y, raioAtual() * 0.5f + 2f, 10);
    }

    public boolean terminou() {
        return tempoRestante <= 0;
    }
}
