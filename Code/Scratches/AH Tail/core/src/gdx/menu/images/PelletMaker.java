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
    private int nCounter=5,nActual=0;
    public ArrayList <Pellet> alPellets = new ArrayList<Pellet>();
    private Texture texture;
    public PelletMaker(int nW, int nH, String sFileName) {
        texture = (new Texture(Gdx.files.internal(sFileName)));  //For example "SignB.jpg"
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
        float fX = 51 + (float)(Math.random() * ((559 - 51) + 1)); //640
        float fY = 51 + (float)(Math.random() * ((399 - 51) + 1)); //480
        alPellets.add(new Pellet(fX, fY, texture));
    }

    public void removePellet(Pellet pellet) {
        alPellets.remove(pellet);
        nActual--;
    }
}

