/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gdx.menu.images;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 *
 * @author parks2214
 */
public class Animation extends Sprite {
    int nHeight = 0, nWidth = 0;
    Texture txtMouse;

    public Animate(int nX, int nY, String sFileName) {
        txtMouse = (new Texture(Gdx.files.internal(sFileName)));


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
