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
    public PelletMaker(String sFileName) {
        texture = (new Texture(Gdx.files.internal(sFileName)));  //For example "SignB.jpg"
    }

    public void draw(SpriteBatch batch, float fX, float fY) {
        for (Pellet p : alPellets) {
            p.draw(batch);
        }
        if (nActual<nCounter) {
            makePellet(fX, fY);
            nActual++;
        }
    }

    public void makePellet(float fX, float fY) {
        alPellets.add(new Pellet(fX, fY, texture));
    }

    public void removePellet(Pellet pellet) {
        alPellets.remove(pellet);
        nActual--;
    }
}

