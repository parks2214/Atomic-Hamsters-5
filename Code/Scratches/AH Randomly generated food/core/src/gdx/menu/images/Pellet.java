package gdx.menu.images;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Pellet extends Sprite {
    float fW, fH;

    public Pellet(float fX, float fY, Texture texture) {
        super(texture);
        fW = 50;//this is what it is set too in scrFood, want natural size later
        fH = 50;
        // fW = texture.getWidth(); //code ready to just directly take size here
        //fH = texture.getHeight();
        setPosition(fX, fY); //set
        setFlip(false, true);
        setSize(fW, fH); //set this in Pellet maker
    }

}
