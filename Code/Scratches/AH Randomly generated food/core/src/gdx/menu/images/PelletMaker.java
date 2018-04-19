/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gdx.menu.images;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

/**
 *
 * @author parks2214
 */
public class PelletMaker {
    int nRandom;
    ArrayList <Pellet> alPellets = new ArrayList<Pellet>();
    Texture texture;
    public PelletMaker() {
        texture = new Texture(Gdx.files.internal("HamP.png"));  //For example "SignB.jpg"
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 3; y++) {
                alPellets.add(new Pellet(x * 200, y * 200, texture));
            }
        }
//        setPosition(nX, nY);
//        setFlip(false, true);
//        setSize(nW, nH);
    }

    public void draw(SpriteBatch batch) {
        for (Pellet p : alPellets) {
            p.draw(batch);
        }
    }
}
