package br.unifor.missaomarte;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

/** Launcher desktop — inicializa a janela e entrega o controle à libGDX. */
public class Lwjgl3Launcher {

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle(MissaoMarteGame.TITULO);
        config.setWindowedMode(MissaoMarteGame.WIDTH, MissaoMarteGame.HEIGHT);
        config.setForegroundFPS(60);
        config.setResizable(false);
        new Lwjgl3Application(new MissaoMarteGame(), config);
    }
}
