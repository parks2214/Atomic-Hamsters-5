package gdx.menu.images;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class AniSprite extends Sprite {
    public Sprite spTemp;
    int nFrame = 0, nPos = 0, nDir = 0, nOrigX, nOrigY;
    Animation araniSprite[];
    Texture txSheet;
    float fDx, fDy;
    Rectangle rMouse, rMouseNew;
    Sprite sprMouse;

    public AniSprite(int nX, int nY, Texture texture) {
        txSheet = texture;
        araniSprite = Animate(txSheet);
        setSize(50, 50);
        setPosition(nX, nY);
        nOrigX = nX;
        nOrigY = nY;
        if (nFrame > 7) {
            nFrame = 0;
        }
    }

    public void reset() {
        nDir = 0;
        fDx = 0;
        fDy = 0;
        nPos = 0;
        setPosition(nOrigX, nOrigY);
    }

    public void animation(int nFrame) {
        spTemp = (Sprite) araniSprite[nPos].getKeyFrame(nFrame, false);
        spTemp.setPosition(getX(), getY());
        spTemp.setSize(getWidth(), getHeight());
        spTemp.setFlip(false, true);
    }

    public void move(int nDir, float fSpeed, int nSizeX, int nSizeY) {
        //Direction instruction for mouse 1
        if (nDir == 0) {
            fDy = 0;
            fDx = 1 + fSpeed;
            nPos = 2;
        } else if (nDir == 1) {
            fDx = 0;
            fDy = -1 - fSpeed;
            nPos = 3;
        } else if (nDir == 2) {
            fDy = 0;
            fDx = -1 - fSpeed;
            nPos = 1;
        } else if (nDir == 3) {
            fDx = 0;
            fDy = 1 + fSpeed;
            nPos = 0;
        }
        setX(getX() + fDx);
        setY(getY() + fDy);
        setSize(nSizeX, nSizeY);
    }

    public boolean isHitS(Sprite spr, int nSizeY) {
        getThisRect(nSizeY);
        return rMouseNew.overlaps(spr.getBoundingRectangle());
    }

    public void outOfBounds() {
        setX(getX() - fDx);
        setY(getY() - fDy);
    }

    public Rectangle getThisRect(int nSizeY) {
        rMouse = spTemp.getBoundingRectangle();
        rMouseNew = rMouse;
        rMouseNew.setY(rMouse.getY() + 20 + ((nSizeY - 50) / 3));
        rMouseNew.setHeight(rMouse.getHeight() - 20 - ((nSizeY - 50) / 3));
        return rMouseNew;
    }

    public Animation[] Animate(Texture txSheet2 ){
        Animation araniMouse[];
        int nW, nH;
        int nSx, nSy;
        araniMouse = new Animation[4];
        nW = txSheet2.getWidth() / 4;
        nH = txSheet2.getHeight() / 4;
        for (int i = 0; i < 4; i++) {
            Sprite[] arSprMouse = new Sprite[4];
            for (int j = 0; j < 4; j++) {
                nSx = j * nW;
                nSy = i * nH;
                sprMouse = new Sprite(txSheet2, nSx, nSy, nW, nH);
                sprMouse.setFlip(false, true);
                arSprMouse[j] = new Sprite(sprMouse);
            }
            araniMouse[i] = new Animation(0.8f, arSprMouse);
        }
        sprMouse.setPosition(200, 200);
        return araniMouse;
    }
}

