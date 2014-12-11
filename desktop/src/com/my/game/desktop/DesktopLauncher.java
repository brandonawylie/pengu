package com.my.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.my.game.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = Game.TITLE;
		cfg.width = Game.V_WIDTH * Game.SCALE;
		cfg.height = Game.V_HEIGHT * Game.SCALE;
		//cfg.resizable = false;
		new LwjglApplication(new Game(), cfg);
	}
}
