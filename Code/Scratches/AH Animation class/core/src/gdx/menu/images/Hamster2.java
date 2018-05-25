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
    public Sprite spTemp;
    int nFrame = 0, nPos = 0, nX = 100, nY = 100, nDx, nDy, nDir = 0, nOrigX, nOrigY;
    Animation araniMouse[];
    Texture txSheet;
    Rectangle rectMouse, rectMouseNew;

    public Hamster2(int nX, int nY) {
        super(new Texture(Gdx.files.internal("sprmouse.png")));
        txSheet = new Texture("sprmouse.png");
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
        nDx = 0;
        nDy = 0;
        nPos = 0;
        setPosition(nOrigX, nOrigY);
    }

    public void animation(int nFrame) {
        spTemp = (Sprite) araniMouse[nPos].getKeyFrame(nFrame, false);

        if (spTemp == null) {
            spTemp = new Sprite(spTemp);
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
            nDy = 0;
            nDx = 1 + (int) fSpeed;
            nPos = 2;
        } else if (nDir == 1) {
            nDx = 0;
            nDy = -1 - (int) fSpeed;
            nPos = 3;
        } else if (nDir == 2) {
            nDy = 0;
            nDx = -1 - (int) fSpeed;
            nPos = 1;
        } else if (nDir == 3) {
            nDx = 0;
            nDy = 1 + (int) fSpeed;
            nPos = 0;
        }
        setX(getX() + nDx);
        setY(getY() + nDy);
        setSize(getWidth() + nSizeX, getHeight() + nSizeY);
    }

    public boolean isHitS(Sprite spr) {
        return spTemp.getBoundingRectangle().overlaps(spr.getBoundingRectangle());
    }
}
