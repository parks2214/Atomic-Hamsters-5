package gdx.menu.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import gdx.menu.GamMenu;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height= 480;
		config.width = 640;
		config.vSyncEnabled = true;

		new LwjglApplication(new GamMenu(), config);
	}
}
