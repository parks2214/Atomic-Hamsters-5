package gdx.menu.images;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;

public class Wall extends Sprite{
    Texture texture;
    public Wall(int nW, int nH, int nX, int nY, Texture texture){
        super(texture);
        setPosition(nX, nY);
        setSize(nW, nH);
        setFlip(false, true);
    }
}

