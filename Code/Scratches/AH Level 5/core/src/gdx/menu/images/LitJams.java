package gdx.menu.images;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class LitJams {
    Music music = Gdx.audio.newMusic(Gdx.files.internal("musak.wav"));

    public void IngameSound(int i) {
        if (i == 0) {
            music.setVolume(1000f);
            music.setLooping(true);
            music.play();
        } else if (i == 1) {
            music.stop();
        }
    }
}