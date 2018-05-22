package gdx.menu.images;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import gdx.menu.Screens.ScrAnimalChoice;
import gdx.menu.Screens.ScrAnimalChoice2;
import gdx.menu.Screens.ScrGame2;

public class Hamster2 extends Sprite {
    float fSx, fSy, fSpeed = 0;
    Sprite sprMouse, spTemp;
    int nFrame = 0, nPos = 0, nX = 100, nY = 100;
    TextureRegion trTemp;
    Animation[] araniMouse;
    int DX[] = {1, 0, -1, 0};
    int DY[] = {0, -1, 0, 1};

    public Hamster2 (int nX, int nY) {
        sprMouse = AniSprite.sprMouse;
        fSx = sprMouse.getX();
        fSy = sprMouse.getY();
        if (nFrame > 7) {
            nFrame = 0;
        }
    }

    public Sprite sprite() {
        trTemp = (TextureRegion) araniMouse[nPos].getKeyFrame(nFrame, false);

        if (spTemp == null) {
            spTemp = new Sprite(trTemp);
            spTemp.setFlip(false,true);
        } else {
            spTemp.setTexture(trTemp.getTexture());
        }
        return spTemp;
    }

    public void move(int nDir) {
        //Direction instruction for mouse 1
        if (DY[nDir] == 0) {
            sprMouse.setY(sprMouse.getY() + DY[nDir]);
            nY = nY += DY[nDir];
        } else if (DY[nDir] < 0){
            sprMouse.setY(sprMouse.getY() + DY[nDir] - fSpeed);
            nY = nY += DY[nDir] - fSpeed;
        }
        else {
            sprMouse.setY(sprMouse.getY() + DY[nDir] + fSpeed);
            nY = nY += DY[nDir] + fSpeed;

        }
        if (DX[nDir] == 0) {
            sprMouse.setX(sprMouse.getX() + DX[nDir]);
            nX = nX += DX[nDir];
        } else if (DX[nDir] < 0){
            sprMouse.setX(sprMouse.getX() + DX[nDir] - fSpeed);
            nX = nX += DX[nDir] - fSpeed;
        }
        else {
            sprMouse.setX(sprMouse.getX() + DX[nDir] + fSpeed);
            nX = nX += DX[nDir] + fSpeed;

        }
        nFrame++;
        if (nDir == 0) {
            nPos = 2;
        } else if (nDir == 1) {
            nPos = 3;
        } else if (nDir == 2) {
            nPos = 1;
        } else if (nDir == 3) {
            nPos = 0;
        }
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
