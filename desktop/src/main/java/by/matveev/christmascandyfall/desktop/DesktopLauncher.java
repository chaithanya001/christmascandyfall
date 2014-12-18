package by.matveev.christmascandyfall.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import by.matveev.christmascandyfall.ChristmasCandyFall;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class DesktopLauncher {
	public static void main (String[] arg) {
        TexturePacker.process(new TexturePacker.Settings(), "../../art/png/game", "gfx/", "game");

		final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.x = 0;
        config.y = 0;
		config.width = 480;
		config.height = 800;

		new LwjglApplication(new ChristmasCandyFall(), config);
	}
}
