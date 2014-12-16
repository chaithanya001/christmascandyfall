package by.matveev.christmascandyfall.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import by.matveev.christmascandyfall.ChristmasCandyFall;

public class DesktopLauncher {
	public static void main (String[] arg) {
		final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 480;
		config.height = 800;

		new LwjglApplication(new ChristmasCandyFall(), config);
	}
}
