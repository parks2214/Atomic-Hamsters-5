package gdx.menu.images;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class AniSprite {
    public static Sprite sprMouse;
    private Texture txSheet;
    private Animation araniMouse[];
    private int fW, fH, fSx, fSy;

    public AniSprite(Texture txSheet) {
        this.txSheet = txSheet;
        araniMouse = new Animation[4];
    }
    public Animation[] animate () {
        fW = txSheet.getWidth() / 4;
        fH = txSheet.getHeight() / 4;
        for (int i = 0; i < 4; i++) {
            Sprite[] arSprMouse = new Sprite[4];
            for (int j = 0; j < 4; j++) {
                fSx = j * fW;
                fSy = i * fH;
                sprMouse = new Sprite(txSheet, fSx, fSy, fW, fH);
                sprMouse.setFlip(false, true);
                arSprMouse[j] = new Sprite(sprMouse);
            }
            araniMouse[i] = new Animation(0.8f, arSprMouse);
        }
        return araniMouse;
    }
}
