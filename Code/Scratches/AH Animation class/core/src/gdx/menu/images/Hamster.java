package gdx.menu.images;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import gdx.menu.Screens.ScrAnimalChoice;
import gdx.menu.Screens.ScrAnimalChoice2;

public class Hamster extends Sprite {

    int nDx, nDy, nPos, nPos2, nGlow, nOrigX, nOrigY, nFrame = 0, nDir, nDir2;
    public Animation arAni[], arAni2[], araniMouse[], araniMouse2[];
    AniSprite aniSprite, aniSprite2;
    public Sprite sprTemp;
    int nChoice, nChoice2;
    Texture txSheet, txSheet2;
    TextureRegion trTemp, trTemp2;

    public Hamster(int nX, int nY, int nW, int nH) {
        super(new Texture(Gdx.files.internal("NormHamAni.png")));
        nChoice = ScrAnimalChoice.nChoice;
        nChoice2 = ScrAnimalChoice2.nChoice2;
        if (nChoice == 1) {
            txSheet = new Texture("sprmouse.png");
        } else if (nChoice == 2) {
            txSheet = new Texture("sprmouse2.png");
        }
        if (nChoice2 == 1) {
            txSheet2 = new Texture("sprmouse.png");
        } else if (nChoice2 == 2) {
            txSheet2 = new Texture("sprmouse2.png");
        }
        aniSprite = new AniSprite(txSheet);
        aniSprite2 = new AniSprite(txSheet2);
        arAni = aniSprite.animate();
        arAni2 = aniSprite2.animate();
        setPosition(nX, nY);
        setSize(nW, nH);
        nOrigX = nX;
        nOrigY = nY;
        setFlip(false, true);
    }

    public void reset() {
        nDir = 0;
        nDx = 0;
        nDy = 0;
        nPos = 0;
        nGlow = 0;
        nOrigX = 0;
        nOrigY = 0;
        setPosition(nOrigX, nOrigY);
    }

    public void animation() {
        if (nFrame > 7) {
            nFrame = 0;
        }
        trTemp = (TextureRegion) araniMouse[nPos].getKeyFrame(nFrame, false);
        trTemp2 = (TextureRegion) araniMouse2[nPos2].getKeyFrame(nFrame, false);
        sprTemp.setPosition(getX(), getY());
        sprTemp.setSize(getWidth(), getHeight());
        sprTemp.setFlip(false, true);
    }

    public void tryMove(int nDir, SprMap map) {
        for (int i = 0; i < arWall.length; i++) {
            if (isHitS(sprMouse, arWall[i])) {
                sprMouse.setPosition(fSx, fSy);
            }
            if (isHitS(sprMouse2, arWall[i])) {
                sprMouse2.setPosition(fSx2, fSy2);
            }
        }
    }

    public void move(int nNewDir, SprMap map) {
        if (DY[nDir2] == 0) {
            sprMouse2.setY(sprMouse2.getY() + DY[nDir2]);
            nY2 = nY2 += DY[nDir2];
        } else if (DY[nDir2] < 0) {
            sprMouse2.setY(sprMouse2.getY() + DY[nDir2] - fSpeed2);
            nY2 = nY2 += DY[nDir2] - fSpeed2;
        } else {
            sprMouse2.setY(sprMouse2.getY() + DY[nDir2] + fSpeed2);
            nY2 = nY2 += DY[nDir2] + fSpeed2;

        }
        if (DX[nDir2] == 0) {
            sprMouse2.setX(sprMouse2.getX() + DX[nDir2]);
            nX2 = nX2 += DX[nDir2];
        } else if (DX[nDir2] < 0) {
            sprMouse2.setX(sprMouse2.getX() + DX[nDir2] - fSpeed2);
            nX2 = nX2 += DX[nDir2] - fSpeed2;
        } else {
            sprMouse2.setX(sprMouse2.getX() + DX[nDir2] + fSpeed2);
            nX2 = nX2 += DX[nDir2] + fSpeed2;

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
        if (nDir2 == 0) {
            nPos2 = 2;
        } else if (nDir2 == 1) {
            nPos2 = 3;
        } else if (nDir2 == 2) {
            nPos2 = 1;
        } else if (nDir2 == 3) {
            nPos2 = 0;
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
