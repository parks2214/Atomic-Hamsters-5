/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gdx.menu.images;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 *
 * @author parks2214
 */
public class Randomly {
    int nRandom;
    public Randomly(int nW, int nH, int nX, int nY, String sFileName) {

        
        setPosition(nX, nY);
        setFlip(false, true);
        setSize(nW, nH);
    }
}
