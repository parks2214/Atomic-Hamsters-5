package gdx.menu.images;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Hamster extends Sprite {
    public Sprite spTemp;
    int nFrame = 0, nPos = 0, nDir = 0, nOrigX, nOrigY;
    Animation araniMouse[];
    Texture txSheet;
    float fDx, fDy;
    Rectangle rMouse, rMouseNew;

    public Hamster(int nX, int nY, Texture texture) {
        txSheet = texture;
        AniSprite aniSprite = new AniSprite(txSheet);
        araniMouse = aniSprite.animate();
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
        spTemp = (Sprite) araniMouse[nPos].getKeyFrame(nFrame, false);

        if (spTemp == null) {
            spTemp.setFlip(false, true);
        } else {
            spTemp.setTexture(spTemp.getTexture());
        }
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
}
