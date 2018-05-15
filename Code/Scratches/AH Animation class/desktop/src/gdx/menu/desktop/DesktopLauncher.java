package gdx.menu.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;
import gdx.menu.GamMenu;

import javax.swing.*;
import java.awt.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height= 480;
		config.width = 640;
		config.vSyncEnabled = true;

		new LwjglApplication(new GamMenu(), config);
	}
}
