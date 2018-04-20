/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gdx.menu.images;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 *
 * @author parks2214
 */
public class PelletMaker {
    int nCounter=6,nActual=0;
    public ArrayList <Pellet> alPellets = new ArrayList<Pellet>();
    Texture texture;
    public PelletMaker() {
        texture = new Texture(Gdx.files.internal("HamP.png"));  //For example "SignB.jpg"
//        for (int x = 0; x < 4; x++) {
//            for (int y = 0; y < 3; y++) {
//                alPellets.add(new Pellet(x * 200, y * 200, texture));
//            }
//        }
//        setPosition(nX, nY);
//        setFlip(false, true);
//        setSize(fW, fH);

    }

    public void draw(SpriteBatch batch) {
        for (Pellet p : alPellets) {
            p.draw(batch);
        }
        if (nActual<nCounter) {
            makePellet();
            nActual++;
        }
    }

    public void makePellet() {
        float fX = (float) Math.random() * Gdx.graphics.getWidth();
        float fY = (float) Math.random() * Gdx.graphics.getHeight();
        alPellets.add(new Pellet(fX, fY, texture));
    }

    public void removePellet(Pellet pellet) {
        alPellets.remove(pellet);
        nActual--;
    }
}
