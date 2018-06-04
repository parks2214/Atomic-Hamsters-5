package gdx.menu.images;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class AniSprite {
    private Sprite sprMouse;
    private Texture txSheet;
    private Animation araniMouse[];
    private int nW, nH;
    public int nSx, nSy;

    public AniSprite(Texture txSheet) {
        this.txSheet = txSheet;
        araniMouse = new Animation[4];
    }
    public Animation[] animate () {
        nW = txSheet.getWidth() / 4;
        nH = txSheet.getHeight() / 4;
        for (int i = 0; i < 4; i++) {
            Sprite[] arSprMouse = new Sprite[4];
            for (int j = 0; j < 4; j++) {
                nSx = j * nW;
                nSy = i * nH;
                sprMouse = new Sprite(txSheet, nSx, nSy, nW, nH);
                sprMouse.setFlip(false, true);
                arSprMouse[j] = new Sprite(sprMouse);
            }
            araniMouse[i] = new Animation(0.8f, arSprMouse);
        }
        sprMouse.setPosition(200, 200);
        return araniMouse;
    }
}
