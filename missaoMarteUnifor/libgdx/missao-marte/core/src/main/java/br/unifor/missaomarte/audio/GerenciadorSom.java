package br.unifor.missaomarte.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Gerencia efeitos sonoros do jogo gerados proceduralmente via PCM/WAV.
 *
 * Conceito de áudio em jogos:
 *  - Sound  → sons curtos (tiro, explosão). Carregado inteiro na memória.
 *  - Music  → músicas longas. Lidas em streaming do disco para economizar RAM.
 *
 * Em um projeto real, use arquivos .wav ou .ogg em assets/ e carregue com:
 *   Sound tiro = Gdx.audio.newSound(Gdx.files.internal("sounds/tiro.wav"));
 *   tiro.play(volume);  // reproduz o som
 *   tiro.dispose();     // libera memória no final
 *
 * Aqui geramos os sons proceduralmente (via síntese PCM) para não depender
 * de arquivos externos — demonstrando ao mesmo tempo o princípio de PCM.
 */
public class GerenciadorSom {

    private static final int SAMPLE_RATE = 22050; // amostras por segundo

    private Sound somTiro;
    private Sound somExplosao;
    private Sound somGameOver;
    private boolean ativo = false;

    public GerenciadorSom() {
        try {
            // Gera cada som como onda PCM e carrega no libGDX como Sound
            somTiro     = wavParaSound(gerarSeno(880f, 0.08f));      // bip agudo curto
            somExplosao = wavParaSound(gerarRuido(0.20f));            // ruído de explosão
            somGameOver = wavParaSound(gerarSequencia(             // melodia descendente
                new float[]{440f, 330f, 220f}, 0.20f));
            ativo = true;
        } catch (Exception e) {
            Gdx.app.log("GerenciadorSom", "Audio indisponivel: " + e.getMessage());
        }
    }

    // ── API pública ──────────────────────────────────────────────────────────

    public void tocarTiro()     { if (ativo && somTiro     != null) somTiro.play(0.5f); }
    public void tocarExplosao() { if (ativo && somExplosao != null) somExplosao.play(0.8f); }
    public void tocarGameOver() { if (ativo && somGameOver != null) somGameOver.play(0.7f); }

    public void dispose() {
        if (somTiro     != null) somTiro.dispose();
        if (somExplosao != null) somExplosao.dispose();
        if (somGameOver != null) somGameOver.dispose();
    }

    // ── Síntese PCM ──────────────────────────────────────────────────────────

    /** Gera uma onda senoidal (tom puro) de frequência e duração dadas. */
    private short[] gerarSeno(float freq, float duracaoSeg) {
        int n = (int) (SAMPLE_RATE * duracaoSeg);
        short[] s = new short[n];
        for (int i = 0; i < n; i++) {
            float t        = (float) i / SAMPLE_RATE;
            float envelope = Math.min(1f, (n - i) / (SAMPLE_RATE * 0.03f)); // fade-out rápido
            s[i] = (short) (Math.sin(2 * Math.PI * freq * t) * Short.MAX_VALUE * 0.65f * envelope);
        }
        return s;
    }

    /** Gera ruído branco com decaimento (simulação de explosão). */
    private short[] gerarRuido(float duracaoSeg) {
        int n = (int) (SAMPLE_RATE * duracaoSeg);
        short[] s = new short[n];
        for (int i = 0; i < n; i++) {
            float decay = (float) (n - i) / n;
            s[i] = (short) ((Math.random() * 2 - 1) * Short.MAX_VALUE * 0.55f * decay * decay);
        }
        return s;
    }

    /** Concatena vários tons senoidais em sequência. */
    private short[] gerarSequencia(float[] freqs, float durCada) {
        int totalAmostras = (int) (SAMPLE_RATE * durCada) * freqs.length;
        short[] total = new short[totalAmostras];
        int pos = 0;
        for (float f : freqs) {
            short[] parte = gerarSeno(f, durCada);
            System.arraycopy(parte, 0, total, pos, parte.length);
            pos += parte.length;
        }
        return total;
    }

    // ── Conversão PCM → WAV → Sound ──────────────────────────────────────────

    /** Encapsula amostras PCM num arquivo WAV temporário e carrega como Sound. */
    private Sound wavParaSound(short[] samples) throws Exception {
        byte[] wavBytes = montarWav(samples);
        File tmp = File.createTempFile("mmarte_", ".wav");
        tmp.deleteOnExit();
        try (FileOutputStream fos = new FileOutputStream(tmp)) {
            fos.write(wavBytes);
        }
        return Gdx.audio.newSound(new FileHandle(tmp));
    }

    /**
     * Monta um arquivo WAV PCM mínimo válido.
     * Estrutura: RIFF header (44 bytes) + amostras PCM 16-bit mono.
     */
    private byte[] montarWav(short[] samples) {
        int dataSize   = samples.length * 2; // 2 bytes por amostra (16-bit)
        ByteBuffer buf = ByteBuffer.allocate(44 + dataSize).order(ByteOrder.LITTLE_ENDIAN);

        buf.put(new byte[]{'R','I','F','F'});
        buf.putInt(36 + dataSize);          // tamanho total - 8
        buf.put(new byte[]{'W','A','V','E'});

        buf.put(new byte[]{'f','m','t',' '});
        buf.putInt(16);                     // tamanho do chunk fmt para PCM
        buf.putShort((short) 1);            // formato: PCM
        buf.putShort((short) 1);            // canais: mono
        buf.putInt(SAMPLE_RATE);            // taxa de amostragem
        buf.putInt(SAMPLE_RATE * 2);        // byteRate = sampleRate * blockAlign
        buf.putShort((short) 2);            // blockAlign = canais * bitsPerSample/8
        buf.putShort((short) 16);           // bits por amostra

        buf.put(new byte[]{'d','a','t','a'});
        buf.putInt(dataSize);
        for (short s : samples) buf.putShort(s);

        return buf.array();
    }
}
