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
int nHeight=0,nWidth=0;
Texture txtMouse;
    public Animate (int nX, int nY,String sFileName) {
             txtMouse=(new Texture(Gdx.files.internal(sFileName)));

    }
    
}
