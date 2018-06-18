package gdx.menu.images;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Pellet extends Sprite {
    private float fW, fH;

    public Pellet(float fX, float fY, Texture texture) {
        super(texture);
        fW = 30;
        fH = 30;
        setPosition(fX, fY); //set
        System.out.println(fX + "   " + fY);
        setFlip(false, true);
        setSize(fW, fH); //set this in Pellet maker
    }

}
