package br.unifor.missaomarte.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;

/**
 * Projétil disparado pela nave com a tecla SPACE.
 *
 * Demonstra o conceito de SPRITE na libGDX:
 *   Pixmap  → imagem criada na RAM (CPU)
 *   Texture → imagem enviada para a GPU (VRAM)
 *   Sprite  → envolve uma Texture com posição, rotação e escala
 *
 * A textura é estática — criada uma única vez e compartilhada
 * por todos os projéteis, economizando memória de GPU.
 */
public class Projetil {

    private static final float VELOCIDADE = 560f;
    public  static final float RAIO       = 5f;

    private float x;
    private float y;
    private final Circle bounds;
    private final Sprite sprite;

    // Textura compartilhada entre todos os projéteis
    private static Texture textura;

    public Projetil(float x, float y) {
        this.x      = x;
        this.y      = y;
        this.bounds = new Circle(x, y, RAIO);

        if (textura == null) textura = criarTextura();

        // Sprite: encapsula a textura com transformações (posição, escala)
        sprite = new Sprite(textura);
        sprite.setSize(RAIO * 2, RAIO * 2);
        sprite.setOriginCenter();
        sincronizarSprite();
    }

    /**
     * Cria a textura do projétil usando Pixmap — sem arquivo externo.
     * Pixmap é um bitmap na RAM; a Texture envia esses pixels para a GPU.
     */
    private static Texture criarTextura() {
        int tam = 16; // 16×16 pixels
        Pixmap px = new Pixmap(tam, tam, Pixmap.Format.RGBA8888);
        px.setColor(0, 0, 0, 0);
        px.fill(); // fundo transparente

        // Halo amarelo
        px.setColor(Color.YELLOW);
        px.fillCircle(tam / 2, tam / 2, 7);

        // Núcleo branco brilhante
        px.setColor(Color.WHITE);
        px.fillCircle(tam / 2, tam / 2, 3);

        Texture t = new Texture(px);
        px.dispose(); // Pixmap não é mais necessário — pixels já estão na GPU
        return t;
    }

    public void atualizar(float delta) {
        y += VELOCIDADE * delta;
        bounds.setPosition(x, y);
        sincronizarSprite();
    }

    private void sincronizarSprite() {
        // setPosition posiciona o canto inferior-esquerdo do sprite
        sprite.setPosition(x - RAIO, y - RAIO);
    }

    /** Desenha o sprite usando SpriteBatch. Deve ser chamado entre batch.begin() e batch.end(). */
    public void desenhar(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public boolean saiuDaTela(float altura) {
        return y - RAIO > altura;
    }

    /** Libera a textura estática. Chamar em MissaoMarteGame.dispose(). */
    public static void disposeTextura() {
        if (textura != null) {
            textura.dispose();
            textura = null;
        }
    }

    public Circle getBounds() { return bounds; }
    public float  getX()      { return x; }
    public float  getY()      { return y; }
}
