package gdx.menu.images;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class AniSprite extends Sprite {
    public Sprite spTemp;
    private int nFrame = 0, nPos = 0, nOrigX, nOrigY, nSizeX = 50, nSizeY = 50;
    private Animation araniSprite[];
    private Texture txSheet;
    private float fDx, fDy, fSpeed = 0;
    private Rectangle rMouse, rMouseNew;
    private Sprite sprite;

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
        fDx = 0;
        fDy = 0;
        nPos = 0;
        fSpeed = 0;
        nSizeX = 50;
        nSizeY = 50;
        setPosition(nOrigX, nOrigY);
    }

    public void animation(int nFrame) {
        spTemp = (Sprite) araniSprite[nPos].getKeyFrame(nFrame, false);
        spTemp.setPosition(getX(), getY());//We thought this was unnecessary, but the mice did not move once this was removed
        spTemp.setSize(getWidth(), getHeight());
        spTemp.setFlip(false, true);
    }

    public void move(int nDir) {
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

    public boolean isHitS(Sprite spr) {
        return getThisRect().overlaps(spr.getBoundingRectangle());
    }

    public void outOfBounds() {
        setX(getX() - fDx);
        setY(getY() - fDy);
    }

    public Rectangle getThisRect() {
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
                sprite = new Sprite(txSheet2, nSx, nSy, nW, nH);
                sprite.setFlip(false, true);
                arSprMouse[j] = new Sprite(sprite);
            }
            araniMouse[i] = new Animation(0.8f, arSprMouse);
        }
        sprite.setPosition(200, 200);
        return araniMouse;
    }

    public void increaseSpeed() {
        fSpeed += 0.5f;
    }

    public void decreaseSpeed() {
        fSpeed -= 0.5f;
    }

    public void increaseSize() {
        if (nSizeX < 100 && nSizeY < 100) {
            nSizeX += 3;
            nSizeY += 3;
        }
    }

    public void decreaseSize() {
        if (nSizeX > 1 && nSizeY > 1) {
            nSizeX -= 3;
            nSizeY -= 3;
        }
    }

    public float getSpeed() {
        return fSpeed;
    }
}

