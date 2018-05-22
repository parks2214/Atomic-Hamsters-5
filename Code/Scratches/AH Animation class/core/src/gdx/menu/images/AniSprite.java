package gdx.menu.images;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class AniSprite {
    static Sprite sprMouse;
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
        sprMouse.setPosition(200, 200);
        return araniMouse;
    }
}

//  nFrame = 0;
//nPos = 0;
//nPos2 = 0;
//araniMouse = new Animation[4];
//araniMouse2 = new Animation[4];
//fW = txSheet.getWidth() / 4;
//fH = txSheet.getHeight() / 4;
//fW2 = txSheet2.getWidth() /4;
//fH2 = txSheet2.getHeight() /4;
//for (int i = 0; i < 4; i++) {
//Sprite[] arSprMouse = new Sprite[4];
//Sprite[] arSprMouse2 = new Sprite[4];
//fSx = j * fW;
//fSy = i * fH;
//fSy2 = i * fH2;
//fSx2 = j * fW2;
//sprMouse = new Sprite(txSheet, fSx, fSy, fW, fH);
//sprMouse.setFlip(false, true);
//arSprMouse[j] = new Sprite(sprMouse);
//sprMouse2 = new Sprite(txSheet2, fSx2, fSy2, fW2, fH2);
//sprMouse2.setFlip(false, true);
//arSprMouse2[j] = new Sprite(sprMouse2);
//araniMouse[i] = new Animation(0.8f, arSprMouse);
//araniMouse2[i] = new Animation(0.8f, arSprMouse2);
//sprMouse.setPosition(200, 200);
//sprMouse2.setPosition(300, 200);
//float fSx = sprMouse.getX();
//    float fSy = sprMouse.getY();
//    float fSx2 = sprMouse2.getX();
//    float fSy2 = sprMouse2.getY();
// if (nFrame > 7) {
//            nFrame = 0;
//        }
//        trTemp = (TextureRegion) araniMouse[nPos].getKeyFrame(nFrame, false);
//        trTemp2 = (TextureRegion) araniMouse2[nPos2].getKeyFrame(nFrame, false);
// spTemp.setPosition(fSx, fSy);
//        spTemp2.setPosition(fSx2, fSy2);
//        batch.draw(trTemp, fSx, fSy, nSizeX, nSizeY);
//        batch.draw(trTemp2, fSx2, fSy2, nSizeX2, nSizeY2);
