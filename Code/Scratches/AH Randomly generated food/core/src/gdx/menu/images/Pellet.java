package gdx.menu.images;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Pellet extends Sprite {
    int nW, nH;
    public Pellet(int nX, int nY, Texture texture) {
        super(texture);
        nW=75;//this is what it is set too in scrFood, want natural size later
        nH=75;
       // nW = texture.getWidth(); //code ready to just directly take size here
        //nH = texture.getHeight();
        setPosition(nX, nY); //set
        setFlip(false, true);
        setSize(nW, nH); //set this in Pellet maker
    }

}
